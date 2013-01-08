package com.ijg.darklight.core.loader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.ijg.darklight.core.Plugin;
import com.ijg.darklight.core.ScoreModule;

public class PluginLoader {

	public Plugin[] loadPlugins() throws IOException {
		File root = new File(new File("."), "plugins");
		if (root.exists() && root.isDirectory()) {
			File[] fileList = root.listFiles();
			Plugin[] plugins = new Plugin[fileList.length];
			int curI = 0;
			for (File plugin : fileList) {
				String name = plugin.getName().substring(0, plugin.getName().indexOf("."));
				System.out.println("Loading plugin: " + name + "...");
				plugins[curI] = (Plugin) DarklightLoader.loadAndInstantiateClass("plugins.com.ijg.darklight.core.plugins." + name);
				++curI;
			}
			return plugins;
		}
		throw new FileNotFoundException("The plugins folder was not found. A plugins folder must be present in the same folder as Darklight to function.");
	}
	
	public ScoreModule[] loadScoreModules() throws IOException {
		File root = new File(new File("."), "plugins");
		if (root.exists() && root.isDirectory()) {
			File[] fileList = root.listFiles();
			ScoreModule[] modules = new ScoreModule[fileList.length];
			int curI = 0;
			for (File module : fileList) {
				if (module.getName().endsWith(".jar")) {
					String name = module.getName().substring(0, module.getName().indexOf("."));
					System.out.println("Loading module: " + name + "...");
					modules[curI] = (ScoreModule) DarklightLoader.loadAndInstantiateJar("com.ijg.darklight.core.scoring." + name, module.getAbsolutePath());
					//modules[curI] = (ScoreModule) DarklightLoader.loadAndInstantiateClass("plugins.com.ijg.darklight.core.scoring." + name);
					++curI;
				}
			}
			return modules;
		}
		throw new FileNotFoundException("The plugins folder was not found. A plugins folder must be present in the same folder as Darklight to function.");
	}
}
