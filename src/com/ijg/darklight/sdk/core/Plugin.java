package com.ijg.darklight.sdk.core;

public class Plugin {
	protected PluginHandler pluginHandler;
	
	public Plugin(PluginHandler pluginHandler) {
		this.pluginHandler = pluginHandler;
	}
	
	/**
	 * Initiate the plugin
	 */
	protected void start() {};
	
	/**
	 * Safely stop the plugin
	 */
	protected void kill() {};
}
