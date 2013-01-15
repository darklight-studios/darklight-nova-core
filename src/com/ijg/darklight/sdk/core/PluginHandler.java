package com.ijg.darklight.sdk.core;

import java.util.ArrayList;

public class PluginHandler {
	private ArrayList<Plugin> plugins;
	
	public PluginHandler(ArrayList<Plugin> loadedPlugins) {
		plugins = loadedPlugins;
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
