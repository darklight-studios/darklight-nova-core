package com.ijg.darklight.sdk.web.api;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/*
 * Copyright (C) 2013  Isaac Grant
 * 
 * This file is part of the Darklight Nova Core.
 *  
 * Darklight Nova Core is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Darklight Nova Core is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with the Darklight Nova Core.  If not, see <http://www.gnu.org/licenses/>.
 */

public class APIRequest {

	private URI requestURI;
	private URL requestURL;
	private URLConnection connection;
	
	private JsonObject response;
	
	/**
	 * 
	 * @param protocol Protocol of the API server
	 * @param server Web address of the API server
	 * @param requestURL URL of the request
	 * @param query The API query
	 */
	public APIRequest(String protocol, String server, String requestURL, String query) {
		try {
			requestURI = new URI(protocol, server, requestURL, query, null);
			this.requestURL = requestURI.toURL();
		} catch (URISyntaxException | MalformedURLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Send this API request
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
			JsonParser jsonParser = new JsonParser();
			response = jsonParser.parse(rawResponse.toString()).getAsJsonObject();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Get the response received from the server for this request
	 * @return The response received from the server for this request
	 */
	public JsonObject getResponse() {
		return response;
	}
	
	/**
	 * Query the response
	 * @param query The data to get from the response
	 * @return The value of the query
	 */
	public JsonElement get(String query) {
		return response.get(query);
	}
}
