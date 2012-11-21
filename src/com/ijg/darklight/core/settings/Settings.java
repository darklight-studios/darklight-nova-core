package com.ijg.darklight.core.settings;

public enum Settings {
	PROGRESS_FILE        (Parser.getValue("general", "progress_file")),
	ID_VERIFICATION      (Parser.getValue("general", "idverification")),
	SESSION_TYPE         (Parser.getValue("general", "session_type")),
	API_ID               (Parser.getValue("api", "id")),
	NAME_FILE            (Parser.getValue("api", "name_file")),
	API_PROTOCOL         (Parser.getValue("api", "protocol")),
	API_SERVER           (Parser.getValue("api", "server")),
	VERIFICATION_ACTIVE  (Parser.getValue("verification", "active")),
	VERIFICATION_NAMES   (Parser.getValue("verification", "names")),
	VERIFICATION_TEAMS   (Parser.getValue("verification", "teams"));
	
	private String value;
	
	private Settings(String value) {
		this.value = value;
	}
	
	public String value() {
		return value;
	}
}
