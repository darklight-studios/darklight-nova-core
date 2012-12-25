package com.ijg.darklight.web.api;

import java.util.HashMap;
import java.util.Map.Entry;

/**
 * Used by the Darklight SDK, acts as a middle man instead of dealing directly with {@link com.ijg.darklight.web.api.APIRequest}
 * @author Isaac Grant
 * @author Lucas Nicodemus
 * @version .1
 * @see com.ijg.darklight.web.sdk.DarklightSDK
 */

public class DarklightAPI {
	public static String AUTH_ENDPOINT = "/api/auth";
	public static String UPDATE_ENDPOINT = "/api/score";
	public static String BASE_URL = "/session/";
	
	String PROTOCOL;
	String SERVER;
	
	/**
	 * 
	 * @param protocol The protocol the API server is on, usually http
	 * @param server The http address of the API server
	 */
	public DarklightAPI(String protocol, String server) {
		this.PROTOCOL = protocol;
		this.SERVER = server;
	}
	
	/**
	 * Create a request for the individual session API
	 * @param sessionID The ID of the session
	 * @param endpoint The endpoint to query
	 * @param parameters The parameters of the actual query
	 * @return The generated {@link com.ijg.darklight.web.api.APIRequest}
	 * @see com.ijg.darklight.web.api.APIRequest
	 * @see com.ijg.darklight.web.sdk.DarklightSDK
	 */
	public APIRequest individualSessionRequest(long sessionID, String endpoint, HashMap<String, String> parameters) {
		String query = makeQueryString(parameters);
		APIRequest request = new APIRequest(PROTOCOL, SERVER, BASE_URL + sessionID + endpoint, query);
		return request;
	}
	
	/**
	 * Generate a query string suitable to pass to the APIRequest constructor
	 * @param parameters The parameters from which the query string will be created
	 * @return A query string suitable to pass to the {@link com.ijg.darklight.web.api.APIRequest} constructor
	 */
	private String makeQueryString(HashMap<String, String> parameters) {
		StringBuilder query = new StringBuilder();
		for (Entry<String, String> pair : parameters.entrySet()) {
			query.append(pair.getKey() + "=" + pair.getValue() + "&");
		}
		return query.toString();
	}
}
