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

public class APIRequest {
	private String PROTOCOL;
	private String SERVER;
	
	private URI requestURI;
	private URL requestURL;
	private URLConnection connection;
	
	private JSONObject response;
	
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

	public JSONObject getResponse() {
		return response;
	}
	
	public Object get(String query) {
		return response.get(query);
	}
}
