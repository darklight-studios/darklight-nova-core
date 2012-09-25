/**
 * Copyright 2010-2012 Lucas "nicatronTg" Nicodemus
 * 
 * The following file is proprietary work developed for private use by Lucas Nicodemus.
 * No reproduction if this in whole or in part may be modified or distributed without the consent of Lucas Nicodemus.
 */

package me.shanked.nicatronTg.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SQLDatabase {
	private ArrayList<Runner> runners = new ArrayList<Runner>();

	private String username;
	private String password;
	private String JDBCUri;
	public int amount;

	public SQLDatabase(int amount, String username, String password,
			String JDBCUri) {
		try {
			Class.forName("com.mysql.jdbc.Driver");

			this.username = username;
			this.password = password;
			this.JDBCUri = JDBCUri;
			this.amount = amount;
			addRunner(1);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		System.out.println("MultiDBEngine initialized.");
		System.out.println("MultiDBEngine: " + runners.size()
				+ " DBRunners created.");
	}

	public void setupRunners() {
		SetupRunner setup = new SetupRunner(this);
		setup.start();
	}

	public void addRunner(int amount) {
		for (int i = 0; i < amount; i++) {
			Runner runner = new Runner(username, password, JDBCUri);
			runner.initialize();
			runners.add(runner);
		}
		System.out.println("MultiDBEngine: " + runners.size()
				+ " runners are now online.");
	}

	public void reset(int amount, String username, String password,
			String JDBCUri) {
		runners.clear();
		for (int i = 0; i < amount; i++) {
			runners.add(new Runner(username, password, JDBCUri));
		}
	}

	public ResultSet executeQuery(String sql, Object... args) {
		return getOpenRunner().executeQuery(sql, args);
	}

	public boolean executeSQL(String sql, Object... args) {
		return getOpenRunner().executeSQL(sql, args);
	}

	private Runner getOpenRunner() {
		if (runners.isEmpty()) {
			Runner tempRunner = new Runner(username, password, JDBCUri);
			tempRunner.initialize();
			return tempRunner;
		} else {
			return runners.remove(0);
		}
	}

	public ArrayList<Runner> getAllRunners() {
		return runners;
	}

	private class SetupRunner extends Thread {

		private SQLDatabase databaseEngine;

		public SetupRunner(SQLDatabase databaseEngine) {
			this.databaseEngine = databaseEngine;
		}

		@Override
		public void run() {
			databaseEngine.addRunner(databaseEngine.amount - 1);
		}
	}

	public class Runner {
		private String username;
		private String password;
		private String JDBCUri;
		private Connection connection;
		private PreparedStatement preparedSQLStatement;
		private boolean lock;

		public Runner(String username, String password, String JDBCUri) {
			this.username = username;
			this.password = password;
			this.JDBCUri = JDBCUri;
			initialize();
		}

		public void initialize() {
			try {
				setConnection(DriverManager.getConnection(JDBCUri, username,
						password));
			} catch (SQLException e) {
				System.out
						.println("WARNING: A database runner failed to initialize! There is probably a problem with the database/it's unreachable!");
				e.printStackTrace();
			}
		}

		public boolean getLock() {
			return lock;
		}

		boolean setLock(boolean lock) {
			return this.lock = lock;
		}

		public Connection getConnection() {
			return connection;
		}

		public void setConnection(Connection connection) {
			this.connection = connection;
		}

		public boolean executeSQL(String sql, Object... args) {
			try {
				preparedSQLStatement = connection.prepareStatement(sql);

				int order = 1;

				for (Object o : args) {
					preparedSQLStatement.setObject(order, o);
					order++;
				}

				boolean success = preparedSQLStatement.execute();
				cleanup();
				return success;
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		}

		public ResultSet executeQuery(String sql, Object... args) {
			try {
				preparedSQLStatement = connection.prepareStatement(sql);

				int order = 1;

				for (Object o : args) {
					preparedSQLStatement.setObject(order, o);
					order++;
				}
				ResultSet result = preparedSQLStatement.executeQuery();
				cleanup();
				return result;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}

		public void cleanup() {
			runners.add(this);
		}
	}
}
