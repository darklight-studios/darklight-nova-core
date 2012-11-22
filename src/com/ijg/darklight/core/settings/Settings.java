package com.ijg.darklight.core.settings;

public enum Settings {
	PROGRESS_FILE        (Parser.getValue("general", "progress")),
	ID_VERIFICATION      (Parser.getValue("general", "idverification")),
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
	
	public Object value() {
		return value;
	}
}
