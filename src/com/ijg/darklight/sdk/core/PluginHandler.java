package com.ijg.darklight.sdk.core;

import java.util.ArrayList;

public class PluginHandler {
	private ArrayList<Plugin> plugins;
	public AccessHandler accessHandler;
	
	public PluginHandler(CoreEngine engine) {
		accessHandler = new AccessHandler(engine);
	}
	
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
