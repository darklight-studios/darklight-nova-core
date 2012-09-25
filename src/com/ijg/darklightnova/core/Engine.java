package com.ijg.darklightnova.core;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import javax.swing.JOptionPane;

import me.shanked.nicatronTg.database.DatabaseQueryEngine;
import me.shanked.nicatronTg.database.SQLDatabase;

import com.ijg.darklightnova.gui.GUI;

public class Engine implements Runnable {
	
	boolean running, bNotFinished;
	
	public String progressFile = "C:\\Users\\Lucas\\AppData\\Roaming\\darklight-progress.dat"; //Should vary with OS installation.
	public String nameFile = "C:\\Users\\Lucas\\AppData\\Roaming\\darklight-name.dat";
	private String bannedWords[] = {"rocks", "amazing", "lol", "rofl", "awesome"}; //Adverb proofing
	public AssessmentModule assessModule;
	
	GUI gui;
	
	private SQLDatabase sqldb;
	public int sessionid = 1; // Change this to the current sessionid from Django
	private String userName = "unset";
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public DatabaseQueryEngine database;
	
	public static void main(String[] args) {
		new Engine();
	}

	public Engine() {
		bNotFinished = true;
		assessModule = new AssessmentModule(this);
		start();
	}
	
	public void start() {
		/*
		 * Init the gui and the thread, start
		 * the gears turning, do initial 
		 * scoring and display
		 */
		running = true;
		gui = new GUI(this);
		Thread engine = new Thread(this, "engine");
		engine.start();
		sqldb = new SQLDatabase(1, "session1", "NnQQYC74byMXrMrY", "jdbc:mysql://direct.shankshock.com/cyberpatriot");
		database = new DatabaseQueryEngine(sqldb, sessionid);
		promptForName();
		assessModule.assess();
		gui.update();
	}

	public void run() {
		while (running) {
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.exit(0);
	}
	
	public void finishSession() {
		running = false;
	}
	
	public void promptForName() {
		if (readName() != null) {
			if (!userName.equals("unset")) {
				database.updateUserScore(userName, sessionid, 0);
				return;
			}
		} else {
			String localName = "";
			String testName = "";
			try {
				localName = JOptionPane.showInputDialog(null, "Enter your full name. This must match your full name or the process will fail.", "It's all about identity.", 1);
				testName = localName.toLowerCase().trim();
			} catch (Exception e) {
				promptForName();
				return;
			}
			
			if (testName.length() < 3) {
				promptForName();
				return;
			}
			for (String s : bannedWords) {
				if (testName.contains(s)) {
					promptForName();
					return;
				}
			}
			userName = localName.trim();
			writeName();
			if (database.userExists(userName, sessionid)) {
				database.updateUserScore(userName, sessionid, 0);
			} else {
				database.insertNewUser(userName, sessionid);
			}
		}
	}
	
	public void writeFoundList() {
		/*
		 * Write all found vulnerabilities
		 * to the progress file in the format
		 * of "name: description"
		*/
		try {
			BufferedWriter out = new BufferedWriter(new FileWriter(new File(progressFile)));
			for (Issue issue : assessModule.issues) {
				out.write(issue.name + ": " + issue.description + "\n");
			}
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String readName() {
		File f = new File(nameFile);
		
		if (f.exists()) {
			try {
				Scanner s = new Scanner(f);
				userName = s.nextLine().trim();
				s.close();
				System.out.println("Read name file with contents: " + userName);
				return userName;
			} catch (Exception e) {
				return null;
			}
		}
		return null;
	}
	
	public boolean writeName() {
		if(!userName.equals("unset")) {
			File f = new File(nameFile);
			if (!f.exists()) {
				try {
					f.createNewFile();
					return true;
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				f.delete();
				try {
					f.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			FileWriter fw;
			try {
				fw = new FileWriter(f);
				System.out.println("Writing name file with contents: " + userName);
				fw.write(userName);
				fw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		return false;
	}
	
	public boolean finished() {
		return !bNotFinished;
	}
}
