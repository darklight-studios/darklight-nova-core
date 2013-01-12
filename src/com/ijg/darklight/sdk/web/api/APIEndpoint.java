package com.ijg.darklight.sdk.web.api;

public enum APIEndpoint {
	AUTH		("/api/auth"),
	UPDATE		("/api/score"),
	INDIVIDUAL 	("/session/individual/"),
	TEAM		("/session/team/");
	
	private String endpoint;
	
	private APIEndpoint(String endpoint) {
		this.endpoint = endpoint;
	}
	
	public String endpoint() {
		return endpoint;
	}
}
