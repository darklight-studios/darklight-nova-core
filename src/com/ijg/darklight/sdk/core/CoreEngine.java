package com.ijg.darklight.sdk.core;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;

import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.ijg.darklight.sdk.loader.DarklightLoader;
import com.ijg.darklight.sdk.loader.PluginLoader;
import com.ijg.darklight.sdk.web.DarklightWebSDK;

public class CoreEngine implements Runnable {
	
	private boolean running;
	private boolean isFinished;
	
	public IssueHandler issueHandler;
	public PluginHandler pluginHandler;
	
	public String sessionKey;
	
	DarklightWebSDK webSDK;
	
	Frontend frontend;
	
	public Settings settings;
	
	/**
	 * Invokes the constructor
	 * @param args Command line arguments
	 */
	public static void main(String[] args) {
		if (args.length == 1) {
			if (args[0] == "-reloadmodules") {
				File root = new File(new File("."), "plugins");
				if (root.exists() && root.isDirectory()) {
					File[] fileList = root.getAbsoluteFile().listFiles();
					for (File module : fileList) {
						if (module.getName().contains("Module")) {
							if (module.getName().endsWith(".jar")) {
								String name = module.getName().substring(0, module.getName().indexOf("."));
								try {
									Class<?> moduleClass = DarklightLoader.loadClassFromJar("com.darklight.core.scoring." + name, module.getPath());
									Method installModule = moduleClass.getMethod("install");
									installModule.invoke(moduleClass, new Object[] {});
								} catch (MalformedURLException | ClassNotFoundException | NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
									e.printStackTrace();
								}
								return;
							}
						}
					}
				}
			}
		} else {
			new CoreEngine();
		}
	}
	
	/**
	 * Initiate the settings, either by deserializing them from the config.json file,
	 * or by initializing the class with the default settings and serializing it
	 */
	private void initSettings() {
		try {
			settings = Settings.createInstance();
		} catch (JsonIOException | JsonSyntaxException e1) {
			System.err.println("JSON error in config file, exiting...");
			System.exit(87); // Parameter is incorrect exit code
		} catch (FileNotFoundException e1) {
			System.err
					.println("Config file not found, creating default...");
			settings = new Settings();
			try {
				settings.serialize();
			} catch (IOException e) {
				System.err.println("Error writing default config file");
				e.printStackTrace();
				System.exit(82); // The file cannot be created exit code
			}
		}
	}
	
	public CoreEngine() {
		initSettings();
		
		PluginLoader pluginLoader = new PluginLoader();
		try {
			issueHandler = new IssueHandler(pluginLoader.loadIssues());
			pluginHandler = new PluginHandler(this);
			pluginHandler.setPlugins(pluginLoader.loadPlugins(pluginHandler));
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(3);
		}
		start();
	}
	
	/**
	 * Initiate the engine thread and the API related stuff
	 * if applicable
	 */
	private void start() {
		printSettings();
		isFinished = false;
		running = true;
		frontend = new Frontend(this);
		pluginHandler.startAll();
		Thread engine = new Thread(this, "engine");
		engine.start();
		if (settings.isApiEnabled()) {
			webSDK = new DarklightWebSDK(this, settings.getApiProtocol(), settings.getApiServer(), settings.getApiID());
			frontend.promptForName();
		}
		issueHandler.checkAllIssues();
		sendUpdate();
	}
	
	/**
	 * Reload the settings class from the config file
	 */
	public void reloadSettings() {
		try {
			settings = Settings.createInstance();
		} catch (JsonIOException | JsonSyntaxException e1) {
			System.err.println("Error reloading settings: JSON error in config file, exiting...");
		} catch (FileNotFoundException e1) {
			System.err
					.println("Error reloading settings: Config file not found");
		}
	}
	
	/**
	 * Print the basic settings loaded from the config file
	 */
	private void printSettings() {
		System.out.println("Running with the following settings:");
		System.out
				.println("Progress file: " + settings.getProgressFile());
		System.out.println("Name file: " + settings.getNameFile());
		System.out
				.println("Session type: " + settings.getSessionType());
		System.out
				.println("API Active: " + settings.isApiEnabled());
		System.out.println("API ID: " + settings.getApiID());
		System.out
				.println("API Protocol: " + settings.getApiProtocol());
		System.out.println("API Server: " + settings.getApiServer());
		System.out.println("Name entry verification: "
				+ settings.isVerificationEnabled());
		if (settings.getVerificationNames() != null) System.out.println("Verified names: "
				+ settings.getVerificationNames().toString());
		if (settings.getVerificationTeams() != null) System.out.println("Verified team names: "
				+ settings.getVerificationTeams().toString());
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
	
	public void update() {
		issueHandler.checkAllIssues();
		authUser();
		sendUpdate();
	}
	
	/**
	 * Safely kill the engine
	 */
	public void finishSession() {
		running = false;
	}
	
	/**
	 * Send an authentication request to the API
	 */
	public void authUser() {
		if (settings.isApiEnabled()) {
			webSDK.auth(frontend.getUserName());
		}
	}
	
	/**
	 * Send a score update request to the API
	 * @param score The current score (number of fixed issues)
	 * @param issues A hashmap of the fixed issues' names and descriptions
	 */
	public void sendUpdate() {
		if (settings.isApiEnabled()) {
			webSDK.update(issueHandler.getFixedIssueCount(), issueHandler.getFixedIssues());
		}
	}
	
	/**
	 * Check if this is a team or individual session
	 * @return False if "sessiontype" is set to "individual", true if it's "team"
	 */
	public boolean teamSession() {
		if (settings.getSessionType() == "team") {
			return true;
		}
		return false;
	}
	
	/**
	 * Check if the session is over
	 * @return True if isFinished has been set true
	 */
	public boolean finished() {
		return isFinished;
	}
}
