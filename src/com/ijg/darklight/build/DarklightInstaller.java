package com.ijg.darklight.build;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.Rectangle;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.ijg.darklight.sdk.core.Settings;
import com.ijg.darklight.sdk.loader.DarklightLoader;

/*
 * Copyright (C) 2013  Isaac Grant
 * 
 * This file is part of the Darklight Nova Core.
 *  
 * Darklight Nova Core is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Darklight Nova Core is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with the Darklight Nova Core.  If not, see <http://www.gnu.org/licenses/>.
 */

public class DarklightInstaller {

	private GraphicsDevice graphicsDevice = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
	private Rectangle screenSize = graphicsDevice.getDefaultConfiguration().getBounds();
	private Point center = new Point(screenSize.width / 2, screenSize.height / 2);
	
	private final String TITLE = "Darklight Installer";
	
	@SuppressWarnings("unused")
	private InstallerFrame gui;
	
	private String installPath;
	private String jarToCopy;
	private String configFileToCopy;
	
	private JsonObject copyInfo;
	
	private File shortcut;
	private File destination;
	
	public DarklightInstaller() {
		gui = new InstallerFrame(TITLE, this, center);
		
		try {
			File logFile = new File("log.txt");
			if (logFile.exists())
				logFile.delete();
			logFile.createNewFile();
			PrintStream printStream = new PrintStream(new FileOutputStream(logFile));
			System.setOut(printStream);
			System.setErr(printStream);
		} catch (IOException e) {
			System.err.println("Error redirecting stdout, stderr");
		}
	}
	
	private DarklightInstaller(String[] args) {
		for (int i = 0; i < args.length; ++i) {
			if (i + 1 < args.length) { 
				if (args[i].equals("-build")) {
					useConfig(new File(args[i+1]));
				} else if (args[i].equals("-install")) {
					setInstallPath(args[i+1]);
				} else if (args[i].equals("-jar")) {
					setJarToCopy(args[i+1]);
				} else if (args[i].equals("-config")) {
					setConfigFileToCopy(args[i+1]);
				} else if (args[i].equals("-copy") && i + 2 < args.length) {
					setCopyInfo(new File(args[i+1]), new File(args[i+2]));
				}
			}
		}
		createFileSystem();
		copyJar();
		copyModules();
		copyScoreOutputDirs();
		installIssues();
		writeDefaultSettings();
		copyFiles();
	}
	
	public static void main(String[] args) {
		if (args.length == 0) {
			new DarklightInstaller();
		} else {
			new DarklightInstaller(args);
		}
	}

	public void setInstallPath(String installPath) {
		this.installPath = installPath;
	}

	public void setJarToCopy(String jarToCopy) {
		this.jarToCopy = jarToCopy;
	}

	public void setConfigFileToCopy(String configFileToCopy) {
		this.configFileToCopy = configFileToCopy;
	}

	public void setCopyInfo(JsonObject copyInfo) {
		this.copyInfo = copyInfo;
	}
	
	public void setCopyInfo(File source, File destination) {
		shortcut = source;
		this.destination = destination;
	}
	
	public void useConfig(File configFile) {
		JsonParser parser = new JsonParser();
		try {
			JsonObject config = parser.parse(new FileReader(configFile)).getAsJsonObject();
			installPath = config.get("installpath").getAsString();
			jarToCopy = config.get("jar").getAsString();
			configFileToCopy = config.get("config").getAsString();
			copyInfo = config.get("copy").getAsJsonObject();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void createFileSystem() {
		File installFolder = new File(installPath);
		File pluginsFolder = new File(installPath, "plugins");
		
		if (pluginsFolder.exists())
			pluginsFolder.delete();
		if (installFolder.exists())
			installFolder.delete();
		
		installFolder.mkdir();
		if (pluginsFolder.mkdir()) {
			System.out.println("Successfully created file system in "
					+ installPath);
			return;
		} else {
			System.err.println("Error creating file system");
		}
	}
	
	public void copyJar() {
		File darklightJar = new File(jarToCopy);
		if (darklightJar.exists() && darklightJar.isFile()) {
			darklightJar.renameTo(new File(installPath, "Darklight.jar"));
		} else {
			System.err.println("Error, invalid Darklight Jar");
		}
	}
	
	public void copyScoreOutputDirs() {
		File staticsDest = new File(installPath, "statics");
		File templatesDest = new File(installPath, "templates");
		
		staticsDest.mkdir();
		templatesDest.mkdir();
		
		try {
			JarFile jar = new JarFile(getClass().getProtectionDomain().getCodeSource().getLocation().getPath());
			Enumeration<JarEntry> jarEntries = jar.entries();
			while (jarEntries.hasMoreElements()) {
				JarEntry entry = jarEntries.nextElement();
				if (!entry.isDirectory()) {
					if (entry.getName().contains("statics/") || entry.getName().contains("templates/")) {
						InputStream inputStream = jar.getInputStream(entry);
						OutputStream outputStream = new FileOutputStream(new File(installPath, entry.getName().replace("/", "\\")));
						byte[] buffer = new byte[4096];
						int length;
						while ((length = inputStream.read(buffer)) > 0) {
							outputStream.write(buffer, 0, length);
						}
						inputStream.close();
						outputStream.close();
					}
				} else if (entry.getName().contains("statics/") || entry.getName().contains("templates/")) {
					File dir = new File(installPath, entry.getName().replace("/", "\\"));
					dir.mkdir();
				}
			}
			jar.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void copyModules() {
		File pluginsFolder = new File("plugins");
		File[] modules = pluginsFolder.listFiles();
		for (File module : modules) {
			if (!module.renameTo(new File(new File(installPath, "plugins"), module.getName()))) {
				System.err.println("Error moving " + module.getName());
			}
		}
	}
	
	public void writeDefaultSettings() {
		if (configFileToCopy == null) {
			Settings.setSettingsFile(new File(installPath, "config.json"));
			Settings settings = new Settings();
			try {
				settings.serialize();
			} catch (IOException e) {
				System.err.println("Error serializing default settings");
			}
		} else {
			File config = new File(configFileToCopy);
			if (config.exists() && config.isFile()) {
				config.renameTo(new File(installPath, "config.json"));
			} else {
				System.err
						.println("Error, specified config file is invalid");
			}
		}
	}
	
	public void installIssues() {
		File root = new File(installPath, "plugins");
		if (root.exists() && root.isDirectory()) {
			File[] fileList = root.getAbsoluteFile().listFiles();
			for (File issue : fileList) {
				if (issue.getName().contains("Issue")) {
					if (issue.getName().endsWith(".jar")) {
						String name = issue.getName().substring(0, issue.getName().indexOf("."));
						Class<?> issueClass;
						try {
							issueClass = DarklightLoader.loadClassFromJar("com.darklight.core.scoring." + name, issue.getPath());
							Method installIssue = issueClass.getMethod("install");
							installIssue.invoke(issueClass, new Object[] {});
						} catch (MalformedURLException | ClassNotFoundException | NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
	}
	
	public void copyFiles() {
		if (copyInfo != null) {
			File source = new File(copyInfo.get("source").getAsString());
			File dest = new File(copyInfo.get("destination").getAsString());
			if (source.exists() && source.isFile()) {
				if (dest.isFile()) {
					source.renameTo(dest);
				} else {
					System.err
							.println("Error, invalid destination for shortcut: Not a file!!");
				}
			}
		} else {
			if (shortcut.exists() && shortcut.isFile()) {
				if (destination.isFile()) {
					shortcut.renameTo(destination);
				} else {
					System.err
							.println("Error, invalid destination for shortcut: Not a file!!");
				}
			}
		}
	}
}
