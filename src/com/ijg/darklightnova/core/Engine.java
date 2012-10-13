package com.ijg.darklightnova.core;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

import javax.swing.JOptionPane;

import com.ijg.darklight.web.sdk.DarklightSDK;
import com.ijg.darklightnova.gui.GUI;

public class Engine implements Runnable {
	
	boolean running, bNotFinished;
	
	public String progressFile = "F:\\Darklight-Nova\\darklight-progress.dat"; //Should vary with OS installation.
	public String nameFile = "F:\\Darklight-Nova\\darklight-name.dat";
	private String bannedWords[] = {"rocks", "amazing", "lol", "rofl", "awesome"}; //Adverb proofing
	public AssessmentModule assessModule;
	
	private String API_PROTOCOL = "http";
	private String API_SERVER = "localhost:8000";
	public String SESSION_KEY;
	public int API_SESSION_ID = 1;
	
	//public DarklightAPI api;
	DarklightSDK sdk;
	
	GUI gui;
	
	private String userName = "unset";
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
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
		//api = new DarklightAPI(API_PROTOCOL, API_SERVER);
		sdk = new DarklightSDK(API_PROTOCOL, API_SERVER, API_SESSION_ID);
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
	
	/*public String authUser() {
		return (String) api.sessionAuthRequest(API_SESSION_ID, userName).get("sessionkey");
	}*/
	
	public void authUser() {
		sdk.apiAuth(userName);
	}
	
	public void sendUpdate(int score, HashMap<String, String> issues) {
		//api.sessionUpdateRequest(API_SESSION_ID, SESSION_KEY, score, issues);
		sdk.apiUpdate(score, issues);
	}
	
	public void promptForName() {
		if (readName() != null) {
			if (!userName.equals("unset")) {
				authUser();
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
			
			for (String word : bannedWords) {
				if (testName.contains(word)) {
					promptForName();
					return;
				}
			}
			userName = localName.trim();
			writeName();
			//SESSION_KEY = (String) api.sessionAuthRequest(API_SESSION_ID, userName).get("sessionkey");
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
