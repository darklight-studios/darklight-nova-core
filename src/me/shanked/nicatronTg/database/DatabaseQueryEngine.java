package me.shanked.nicatronTg.database;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseQueryEngine {
	private SQLDatabase database;
	public DatabaseQueryEngine(SQLDatabase database) {
		this.database = database;
	}
	
	public boolean userExists(String name, int sessionid) {
		ResultSet r = database.executeQuery("SELECT name FROM `cyberpatriot` WHERE name=? AND session_id=?", name, sessionid);
		
		try {
			r.next();
			if (r.getString("name").equals(name)) {
				return true;
			}
		} catch (SQLException e) {
			return false;
		}
		return false;
	}
	
	public boolean insertNewUser(String name, int sessionid) {
		return database.executeSQL("INSERT INTO `cyberpatriot` VALUES(null, ?, ?, ?)", sessionid, name, 0);
	}
	
	public boolean updateUserScore(String name, int sessionid, int score) {
		return database.executeSQL("UPDATE `cyberpatriot` SET score=? WHERE session_id=? AND name=?", score, sessionid, name);
	}
}
