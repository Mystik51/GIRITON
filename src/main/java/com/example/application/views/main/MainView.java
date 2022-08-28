package com.example.application.views.main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.example.application.model.EventRow;
import com.example.application.model.EventState;
import com.example.application.rest.RestService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

/**
 * Třída pro zobrazení hlavní stranky
 * @author krems
 *
 */
@Route("")
public class MainView extends VerticalLayout {

	// seznam s daty pro Grid
	private ArrayList<EventRow> list = new ArrayList();
	// zpracovavá pralarně seznam eventů max 5 najednou
	private ExecutorService threadPool = Executors.newFixedThreadPool(5);
	// id pro nový event
	private int id = 0;

	/**
	 * konstruktor pro webovou stránku
	 */
	public MainView() {

		Random random = new Random();
		Button addButton = new Button("Získat IP");
		Button refreshTable = new Button("Aktualizovat");
		Grid<EventRow> grid = new Grid<>(EventRow.class, false);

		addButton.addClickListener(click -> {
			id += 1;
			EventRow eventRow = new EventRow(id, EventState.WAITING);
			list.add(eventRow);
			refreshList(grid);
			
			showNotification("Úloha " + eventRow.getId() + ": Zařazena do fronty");

			threadPool.execute(() -> {
				eventRow.setState(EventState.RUNNING);
				refreshList(grid);
				
				showNotification("Úloha " + eventRow.getId() + ": Běží");
				try {
					String json = RestService.getIP();
					ObjectMapper mapper = new ObjectMapper();
					Map<String, Object> map = mapper.readValue(json, Map.class);
					System.out.println(map.get("ip"));
					TimeUnit.SECONDS.sleep((random.nextInt(5) + 5));

					if (eventRow.getId() % 5 == 0) {
						throw new Exception("Nastala nečekaná chyba");
					}

					list.remove(eventRow);
					refreshList(grid);
					
					showNotification("Úloha " + eventRow.getId() + ": Skončila");
					
				} catch (InterruptedException ex) {
					Thread.currentThread().interrupt();
				} catch (IOException ex) {
					ex.printStackTrace();
				} catch (Exception e) {
					eventRow.setState(EventState.ERROR);
					refreshList(grid);
					
					showNotification(e.getMessage());
				}
			});

		});
		addButton.addClickShortcut(Key.ENTER);
		
		refreshTable.addClickListener(click -> {
			refreshList(grid);
			showNotification("Tabulka byla aktualizována");
		});

		grid.addColumn(EventRow::getId).setHeader("Pořadové číslo");
		grid.addColumn(EventRow::getState).setHeader("Stav");
		grid.setItems(list);

		add(new H1("Vaadin test"), new HorizontalLayout(addButton,refreshTable), grid);
	}
	
	/**
	 * Metoda pro aktualizaci všech záznamu v gridu
	 * @param grid který má být aktualizován
	 */
	private void refreshList(Grid grid) {
		getUI().get().accessSynchronously(() -> {
			grid.getDataProvider().refreshAll();
		});
	}
	
	/**
	 * Meotda pro výpis notifikaci pro uživatele
	 * @param text který má být uživateli zobrazen
	 */
	private void showNotification(String text) {
		getUI().get().accessSynchronously(() -> {
			Notification.show(text,2000,Position.TOP_CENTER);
		});
	}
}