package me.shanked.nicatronTg.database;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseQueryEngine {
	private SQLDatabase database;
	private int sessionid;

	public int getSessionid() {
		return sessionid;
	}

	public void setSessionid(int sessionid) {
		this.sessionid = sessionid;
	}

	public DatabaseQueryEngine(SQLDatabase database, int sessionid) {
		this.database = database;
	}

	public boolean userExists(String name, int sessionid) {
		ResultSet r = database
				.executeQuery(
						"SELECT name FROM `individual_session_individual` WHERE name=? AND session_id=?",
						name, sessionid);

		try {
			r.next();
			if (r.getString("name").equals(name)) {
				System.out.println("User already exists in database. Sweet.");
				return true;
			}
		} catch (SQLException e) {
			System.out.println("User doesn't exist. That works.");
			return false;
		}
		return false;
	}

	public boolean insertNewUser(String name, int sessionid) {
		System.out.println("Sending creation packet with data: Name: " + name + ", Session ID: " + sessionid);
		return database.executeSQL(
				"INSERT INTO `individual_session_individual` VALUES(null, ?, ?, ?)", sessionid,
				name, 0);
	}

	public boolean updateUserScore(String name, int sessionid, int score) {
		System.out.println("Sending update packet with data: Name: " + name + ", Session ID: " + sessionid + ", Score: " + score);
		return database
				.executeSQL(
						"UPDATE `individual_session_individual` SET score=? WHERE session_id=? AND name=?",
						score, sessionid, name);
	}
}
