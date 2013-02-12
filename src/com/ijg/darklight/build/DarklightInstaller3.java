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
import java.io.PrintStream;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

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

public class DarklightInstaller3 {

		private GraphicsDevice graphicsDevice = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		private Rectangle screenSize = graphicsDevice.getDefaultConfiguration().getBounds();
		private Point center = new Point(screenSize.width / 2, screenSize.height / 2);
		
		private final String TITLE = "Darklight Installer";
		
		private InstallerFrame gui;
		
		private String installPath;
		private String jarToCopy;
		private String configFileToCopy;
		private JsonObject copyInfo;
		
		public DarklightInstaller3() {
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
		
		public static void main(String[] args) {
			new DarklightInstaller3();
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
}
