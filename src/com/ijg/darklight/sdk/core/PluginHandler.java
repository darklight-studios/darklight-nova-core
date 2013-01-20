package com.ijg.darklight.sdk.core;

import java.util.ArrayList;

public class PluginHandler {
	private ArrayList<Plugin> plugins;
	public AccessHandler accessHandler;
	
	/**
	 * 
	 * @param engine Instance of engine the AccessHandler will use
	 */
	public PluginHandler(CoreEngine engine) {
		accessHandler = new AccessHandler(engine);
	}
	
	/**
	 * 
	 * @param plugins Loaded plugins
	 */
	void setPlugins(ArrayList<Plugin> plugins) {
		this.plugins = plugins;
	}
	
	/**
	 * Start all loaded plugins
	 */
	public void startAll() {
		for (Plugin plugin : plugins) {
			plugin.start();
		}
	}
	
	/**
	 * Kill all loaded plugins
	 */
	public void killAll() {
		for (Plugin plugin : plugins) {
			plugin.kill();
		}
	}
}
