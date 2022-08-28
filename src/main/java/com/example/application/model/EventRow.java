package com.example.application.model;

import java.util.Objects;

/**
 * Modelová třida pro řádek v gridu, reprezentuje stav události
 * @author krems
 *
 */
public class EventRow {
	private int id;
	private EventState state;
	
	public EventRow(int id, EventState state) {
		super();
		this.id = id;
		this.state = state;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EventRow other = (EventRow) obj;
		return id == other.id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public EventState getState() {
		return state;
	}

	public void setState(EventState state) {
		this.state = state;
	}

	
	
}
