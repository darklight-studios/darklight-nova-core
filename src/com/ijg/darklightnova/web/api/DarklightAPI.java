package com.ijg.darklightnova.web.api;

import java.util.HashMap;
import java.util.Map.Entry;

public class DarklightAPI {
	public static String AUTH_ENDPOINT = "/api/auth";
	public static String UPDATE_ENDPOINT = "/api/score";
	public static String BASE_URL = "/session/";
	
	String PROTOCOL;
	String SERVER;
	
	public DarklightAPI(String protocol, String server) {
		this.PROTOCOL = protocol;
		this.SERVER = server;
	}
	
	/*
	 * ==============================
	 * = Individual Session Request =
	 * ==============================
	 * Requires:
	 * 	session ID
	 * 		ID of session to work with
	 * 	API endpoint
	 * 		endpoint to query
	 * 	query parameters (HashMap<String, String>)
	 * 		parameters of HTTP GET query
	 * 
	 * ~~~~~~~~~~~~~~~~
	 * Request Schemes~
	 * ~~~~~~~~~~~~~~~~
	 * Auth
	 * 	Required parameters:
	 * 		name
	 * 	Returns:
	 * 		Session key
	 * 		initial vulnerabilities if individual already exists
	 * 
	 * Update
	 * 	Required parameters:
	 * 		sessionkey
	 * 		score
	 * 		desc
	 * 			All found issues as JSON
	 * 	Returns:
	 * 		status_code
	 * 
	 * =================
	 * = Example Usage =
	 * =================
	 * |Auth|
	 * HashMap<String, String> parameters = new HashMap<String, String>();
	 * parameters.put("name", "Test Dummy");
	 * APIRequest authRequest = individualSessionRequest(sessionID, DarklightAPI.AUTH_ENDPOINT, parameters);
	 * authRequest.send();
	 * String sessionKey = authRequest.get("sessionkey");
	 * 
	 * |Update|
	 * HashMap<String, String> updateParameters = new HashMap<String, String>();
	 * updateParameters.put("sessionkey", sessionkey);
	 * updateParameters.put("score", "" + engine.assessModule.issues.size());
	 * updateParameters.put("desc", JSONObject.toJSONString(engine.assessModule.generateIssuesHashMap()));
	 * APIRequest updateRequest = individualSessionRequest(sessionID, DarklightAPI.UPDATE_ENDPOINT, updateParameters);
	 * updateRequest.send();
	 * int status = (int) updateRequest.get("status_code");
	 * boolean success = true ? status == 200 || status == 201 : false;
	 */
	
	public APIRequest individualSessionRequest(int sessionID, String endpoint, HashMap<String, String> parameters) {
		String query = makeQueryString(parameters);
		APIRequest request = new APIRequest(PROTOCOL, SERVER, BASE_URL + sessionID + endpoint, query);
		return request;
	}
	
	private String makeQueryString(HashMap<String, String> parameters) {
		StringBuilder query = new StringBuilder();
		for (Entry<String, String> pair : parameters.entrySet()) {
			query.append(pair.getKey() + "=" + pair.getValue() + "&");
		}
		return query.toString();
	}
}
