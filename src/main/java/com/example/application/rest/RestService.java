package com.example.application.rest;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

/**
 * Třida pro Restové metody
 * @author krems
 *
 */
public class RestService {
	
	// adresa serveru
	private static String serverIPAdress = "http://ip.jsontest.com/";
	
	/**
	 * Metoda pro získání odpovědi ze serveru
	 * @return vratí ip adresu v JSON formátu
	 * @throws IOException
	 */
	public static String getIP() throws IOException{

		HttpURLConnection connection = (HttpURLConnection) new URL(serverIPAdress).openConnection();
		
		connection.setRequestMethod("GET");

		int responseCode = connection.getResponseCode();
		if(responseCode == 200){
			String response = "";
			Scanner scanner = new Scanner(connection.getInputStream());
			while(scanner.hasNextLine()){
				response += scanner.nextLine();
				response += "\n";
			}
			scanner.close();

			return response;
		}
		
		return null;
	}
}
