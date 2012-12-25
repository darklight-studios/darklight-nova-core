package com.ijg.darklight.core.settings;

import org.json.simple.JSONArray;

/**
 * Handles the settings parsed by {@link com.ijg.darklight.core.settings.Parser}
 * @author Isaac Grant
 * @author Lucas Nicodemus
 * @version .1
 *
 */

public enum Settings {
	PROGRESS_FILE        (Parser.getValue("general", "progress")),
	SESSION_TYPE         (Parser.getValue("general", "sessiontype")),
	API_ACTIVE			 (Parser.getValue("api", "active")),
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
	
	/**
	 * 
	 * @return A value parsed from the config file
	 */
	private Object value() {
		return value;
	}
	
	/**
	 * Get a setting stored as a string in the config file
	 * Valid strings to pass are:
	 * <ul>
	 * <li>progressfile</li>
	 * <li>sessiontype</li>
	 * <li>namefile</li>
	 * <li>api.id</li>
	 * <li>api.protocol</li>
	 * <li>api.server</li>
	 * </ul>
	 * 
	 * @param setting The setting for the value to be returned
	 * @return The value of the setting, returns empty string if the passed setting string is invalid
	 */
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
	
	/**
	 * Get a setting stored as a boolean in the config file
	 * @param setting The setting for the value to be returned
	 * @return The value of the setting, returns false if the passed setting string is invalid
	 */
	public static boolean getBool(String setting) {
		switch (setting) {
			case "api.active":
				return ((String) API_ACTIVE.value()).equals("true") ? true : false;
			case "verification.active":
				return ((String) VERIFICATION_ACTIVE.value()).equals("true") ? true : false;
			default:
				System.out.println("Error, boolean setting does not exist: "
						+ setting);
				break;
		}
		return false;
	}
	
	/**
	 * Get a setting stored as JSON in the config file
	 * @param setting The setting for the value to be returned
	 * @return The value of the setting, returns null if the passed setting string is invalid
	 */
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
