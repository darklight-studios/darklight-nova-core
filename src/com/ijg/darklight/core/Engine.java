package com.ijg.darklight.core;

import java.util.HashMap;
import com.ijg.darklight.web.sdk.DarklightSDK;
import com.ijg.darklight.core.settings.Settings;

/**
 * Darklight Core<br />
 * License: Undecided as of 12/24/12<br />
 * Please do not redistribute or modify this code
 * until a license has been decided on and necessary
 * protection has been implemented.
 * 
 * @author Isaac Grant
 * @author Lucas Nicodemus
 * @version .1
 *
 */

public class Engine implements Runnable {

	private boolean running;
	private boolean bNotFinished;
	
	
	public final String PROGRESS_FILE = Settings.get("progressfile");
	
	public ModuleHandler moduleHandler;
	
	private final boolean API_ACTIVE = Settings.getBool("api.active");
	private final String API_PROTOCOL = Settings.get("api.protocol");
	private final String API_SERVER = Settings.get("api.server");
	public String sessionKey;
	
	public final int API_SESSION_ID = Integer.parseInt(Settings.get("api.id"));
	
	DarklightSDK sdk;
	
	Frontend frontend;
	
	
	final private boolean TEAM_SESSION = (Settings.get("sessiontype").equals("individual")) ? false : true;
	

	/**
	 * Invokes the constructor
	 * 
	 * @param args not used
	 */
	public static void main(String[] args) {
		new Engine();
	}

	public Engine() {
		moduleHandler = new ModuleHandler(this);
		start();
	}
	
	/**
	 * Initiate Darklight Engine
	 */
	public void start() {
		printSettings();
		bNotFinished = true;
		running = true;
		frontend = new Frontend(this);
		Thread engine = new Thread(this, "engine");
		engine.start();
		if (API_ACTIVE)
				sdk = new DarklightSDK(API_PROTOCOL, API_SERVER, API_SESSION_ID);
		frontend.promptForName();
		moduleHandler.checkAllVulnerabilities();
	}

	/**
	 * Print settings parsed from config file
	 */
	private void printSettings() {
		System.out.println("Running with the following settings:");
		System.out
				.println("Progress file: " + Settings.get("progressfile"));
		System.out.println("Name file: " + Settings.get("namefile"));
		System.out
				.println("Session type: " + Settings.get("sessiontype"));
		System.out
				.println("API Active: " + Settings.getBool("api.active"));
		System.out.println("API ID: " + Settings.get("api.id"));
		System.out
				.println("API Protocol: " + Settings.get("api.protocol"));
		System.out.println("API Server: " + Settings.get("api.server"));
		System.out.println("Name entry verification: "
				+ Settings.getBool("verification.active"));
		System.out.println("Verified names: "
				+ Settings.getJSON("verification.names"));
		System.out.println("Verified team names: "
				+ Settings.getJSON("verification.teams"));
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
	
	/**
	 * Kill the program safely
	 */
	public void finishSession() {
		running = false;
	}
	
	/**
	 * Get an API session key
	 */
	public void authUser() {
		if (API_ACTIVE)
			sdk.apiAuth(frontend.getUserName());
	}
	
	/**
	 * Send a score update
	 * @param score Number of issues fixed
	 * @param issues HashMap of all fixed issues
	 */
	public void sendUpdate(int score, HashMap<String, String> issues) {
		if (API_ACTIVE)
			sdk.apiUpdate(score, issues);
	}
	
	/**
	 * Check the type of the current session
	 * @return True if the current session is defined as a team session
	 */
	public boolean teamSession() {
		return TEAM_SESSION;
	}
	
	
	/**
	 * Check if the current session is finished
	 * @return True if bNotFinished has been set to false
	 */
	public boolean finished() {
		return !bNotFinished;
	}
}
