package com.ijg.darklightnova.core;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import com.ijg.darklight.web.sdk.DarklightSDK;
import com.ijg.darklight.core.settings.Settings;

public class Engine implements Runnable {
	
	boolean running, bNotFinished;
	
	public String progressFile = Settings.get("progressfile");
	
	public AssessmentModule assessModule;
	
	// Constant for our images, upon release hard coding will be removed
	private String API_PROTOCOL = "http";
	private String API_SERVER = "darklight-nova.herokuapp.com";
	public String SESSION_KEY;
	
	public int API_SESSION_ID = Integer.parseInt(Settings.get("api.id"));
	
	DarklightSDK sdk;
	
	Frontend frontend;
	
	//GUI gui;
	
	// VERY IMPORTANT!!!!
	// Change this to switch between team/individual name entry and stuff
	final private boolean team = (Settings.get("sessiontype") == "individual") ? false : true;
	
	
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
		frontend = new Frontend(this);
		Thread engine = new Thread(this, "engine");
		engine.start();
		sdk = new DarklightSDK(API_PROTOCOL, API_SERVER, API_SESSION_ID);
		frontend.promptForName();
		assessModule.assess();
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
	
	public void authUser() {
		sdk.apiAuth(frontend.getUserName());
	}
	
	public void sendUpdate(int score, HashMap<String, String> issues) {
		sdk.apiUpdate(score, issues);
	}
	
	public boolean teamSession() {
		return team;
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
	
	public boolean finished() {
		return !bNotFinished;
	}
}
