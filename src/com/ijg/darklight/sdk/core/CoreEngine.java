package com.ijg.darklight.sdk.core;

import java.io.IOException;
import java.util.HashMap;

import com.ijg.darklight.sdk.loader.PluginLoader;
import com.ijg.darklight.sdk.web.DarklightWebSDK;

public class CoreEngine implements Runnable {
	
	private boolean running;
	private boolean isFinished;
	
	public final String PROGRESS_FILE = Settings.getProperty("general", "progress");
	
	public ModuleHandler moduleHandler;
	public PluginHandler pluginHandler;
	
	private final boolean API_ACTIVE = Settings.getPropertyAsBool("api", "active");
	private final String API_PROTOCOL = Settings.getProperty("api", "protocol");
	private final String API_SERVER = Settings.getProperty("api", "server");
	private final boolean TEAM_SESSION = (Settings.getProperty("general", "sessiontype").equals("individual")) ? false : true;
	
	public String sessionKey;
	
	public final int API_SESSION_ID = Settings.getPropertyAsInt("api", "id");
	
	DarklightWebSDK webSDK;
	
	Frontend frontend;
	
	public static void main(String[] args) {
		new CoreEngine();
	}
	
	public CoreEngine() {
		PluginLoader pluginLoader = new PluginLoader();
		try {
			moduleHandler = new ModuleHandler(this, pluginLoader.loadScoreModules());
			pluginHandler = new PluginHandler(pluginLoader.loadPlugins());
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(3);
		}
		start();
	}
	
	private void start() {
		printSettings();
		isFinished = false;
		running = true;
		frontend = new Frontend(this);
		pluginHandler.startAll();
		Thread engine = new Thread(this, "engine");
		engine.start();
		if (API_ACTIVE) {
			webSDK = new DarklightWebSDK(API_PROTOCOL, API_SERVER, API_SESSION_ID);
			frontend.promptForName();
		}
		moduleHandler.checkAllVulnerabilities();
	}
	
	private void printSettings() {
		System.out.println("Running with the following settings:");
		System.out
				.println("Progress file: " + Settings.getProperty("general", "progress"));
		System.out.println("Name file: " + Settings.getProperty("api", "name"));
		System.out
				.println("Session type: " + Settings.getProperty("general", "sessiontype"));
		System.out
				.println("API Active: " + Settings.getPropertyAsBool("api", "active"));
		System.out.println("API ID: " + Settings.getPropertyAsInt("api", "id"));
		System.out
				.println("API Protocol: " + Settings.getProperty("api", "protocol"));
		System.out.println("API Server: " + Settings.getProperty("api", "server"));
		System.out.println("Name entry verification: "
				+ Settings.getPropertyAsBool("verification", "active"));
		System.out.println("Verified names: "
				+ Settings.getPropertyAsJsonArray("verification", "names"));
		System.out.println("Verified team names: "
				+ Settings.getPropertyAsJsonArray("verification", "teams"));
	}
	
	public void run() {
		while (running) {
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("Exiting...");
		System.exit(0);
	}
	
	public void finishSession() {
		running = false;
	}
	
	public void authUser() {
		if (API_ACTIVE) {
			webSDK.auth(frontend.getUserName());
		}
	}
	
	public void sendUpdate(int score, HashMap<String, String> issues) {
		if (API_ACTIVE) {
			webSDK.update(score, issues);
		}
	}
	
	public boolean teamSession() {
		return TEAM_SESSION;
	}
	
	public boolean finished() {
		return isFinished;
	}
}
