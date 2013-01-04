package com.ijg.darklight.core.loader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.ijg.darklight.core.ScoreModule;

/**
 * Dynamically load all scoring modules placed in the "plugins" folder
 * @author Isaac Grant
 * @author Lucas Nicodemus
 * @version .1
 *
 */

public class ModuleLoader {
	
	/**
	 * 
	 * @return An array of score module instances loaded from the plugins folder
	 * @throws IOException If plugins folder is not present
	 */
	public static ScoreModule[] loadAllModules() throws IOException {
		File root = new File(new File("."), "plugins");
		if (root.exists() && root.isDirectory()) {
			ScoreModule[] modules = new ScoreModule[root.listFiles().length];
			int curI = 0;
			for (File module : root.listFiles()) {
				String name = module.getName().substring(0, module.getName().indexOf("."));
				System.out.println("Loading module " + name + "...");
				modules[curI] = (ScoreModule) DarklightLoader.loadAndInstantiateClass("plugins." + name);
				++curI;
			}
			return modules;
		}
		throw new FileNotFoundException("The plugins folder was not found. A plugins folder must be present in the same folder as Darklight to function.");
	}
}
