package com.ijg.darklight.web.api;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

/**
 * Used by the DarklightAPI, acts as a container for an API request
 * @author Isaac Grant
 * @author Lucas Nicodemus
 * @version .1
 * @see com.ijg.darklight.web.api.DarklightAPI
 */

public class APIRequest {
	private String PROTOCOL;
	private String SERVER;
	
	private URI requestURI;
	private URL requestURL;
	private URLConnection connection;
	
	private JSONObject response;
	
	/**
	 * 
	 * @param protocol The protocol used by the API server, usually http
	 * @param server The http address of the API server
	 * @param requestURL The URL to send the API request to
	 * @param query The actual query to be sent
	 */
	public APIRequest(String protocol, String server, String requestURL, String query) {
		this.PROTOCOL = protocol;
		this.SERVER = server;
		try {
			requestURI = new URI(PROTOCOL, SERVER, requestURL, query, null);
			this.requestURL = requestURI.toURL();
		} catch (URISyntaxException | MalformedURLException e) {
			e.printStackTrace();
		}		
	}
	
	/**
	 * Send the request
	 */
	public void send() {
		try {
			connection = requestURL.openConnection();
			
			StringBuilder rawResponse = new StringBuilder();
			Scanner scanner = new Scanner(connection.getInputStream());
			
			while (scanner.hasNext()) {
				rawResponse.append(scanner.next());
			}
			scanner.close();
			
			response = (JSONObject) JSONValue.parse(rawResponse.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Get the API response
	 * @return The API response
	 */
	public JSONObject getResponse() {
		return response;
	}
	
	/**
	 * Get an object from the JSON response
	 * @param query The JSON key to retrieve from the response
	 * @return The JSON value for the given key (query)
	 */
	public Object get(String query) {
		return response.get(query);
	}
}
