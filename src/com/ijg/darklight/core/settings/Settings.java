package com.ijg.darklight.core.settings;

import org.json.simple.JSONArray;

public enum Settings {
	PROGRESS_FILE        (Parser.getValue("general", "progress")),
	SESSION_TYPE         (Parser.getValue("general", "sessiontype")),
	API_ID               (Parser.getValue("api", "id")),
	NAME_FILE            (Parser.getValue("api", "name")),
	API_PROTOCOL         (Parser.getValue("api", "protocol")),
	API_SERVER           (Parser.getValue("api", "server")),
	VERIFICATION_ACTIVE  (Parser.getValue("verification", "active")),
	VERIFICATION_NAMES   (Parser.getValue("verification", "names")),
	VERIFICATION_TEAMS   (Parser.getValue("verification", "teams"));
	
	private Object value;
	
	private Settings(Object value) {
		this.value = value;
	}
	
	private Object value() {
		return value;
	}
	
	public static String get(String setting) {
		switch (setting) {
			case "progressfile":
				return (String) PROGRESS_FILE.value();
			case "sessiontype":
				return (String) SESSION_TYPE.value();
			case "api.id":
				return String.valueOf((Long) API_ID.value());
			case "namefile":
				return (String) NAME_FILE.value();
			case "api.protocol":
				return (String) API_PROTOCOL.value();
			case "api.server":
				return (String) API_SERVER.value();
			default:
				System.out.println("Error, regular setting does not exist: " + setting);
				break;
		}
		return "";
	}
	
	public static boolean getBool(String setting) {
		switch (setting) {
			case "verification.active":
				return (((String) VERIFICATION_ACTIVE.value()) == "true") ? true : false;
			default:
				System.out.println("Error, boolean setting does not exist: "
						+ setting);
				break;
		}
		return false;
	}
	
	public static JSONArray getJSON(String setting) {
		switch (setting) {
			case "verification.names":
				return (JSONArray) VERIFICATION_NAMES.value();
			case "verification.teams":
				return (JSONArray) VERIFICATION_TEAMS.value();
			default:
				System.out
						.println("JSON setting does not exist: " + setting);
				break;
		}
		return null;
	}
	
}
