package com.ijg.darklight.build;

import java.awt.Point;
import java.io.File;

import javax.swing.JFrame;
import com.google.gson.JsonObject;
import com.ijg.darklight.build.panels.ChooseBuildPanel;
import com.ijg.darklight.build.panels.ChooseConfigPanel;
import com.ijg.darklight.build.panels.ChooseInstallPanel;
import com.ijg.darklight.build.panels.ChooseJarPanel;
import com.ijg.darklight.build.panels.ChooseShortcutPanel;
import com.ijg.darklight.build.panels.EPanels;
import com.ijg.darklight.build.panels.InstallerPanel;
import com.ijg.darklight.build.panels.OptionBuildPanel;
import com.ijg.darklight.build.panels.OptionConfigPanel;
import com.ijg.darklight.build.panels.TermsPanel;
import com.ijg.darklight.build.panels.WelcomePanel;

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

public class InstallerFrame extends JFrame {
	private static final long serialVersionUID = 8468796890232401893L;
	
	private DarklightInstaller installer;
	private Point center;
	
	public InstallerFrame(String title, DarklightInstaller installer, Point center) {
		super(title);
		this.installer = installer;
		this.center = center;
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		setVisible(true);
		changePanel(EPanels.WELCOME);
	}
	
	public void changePanel(EPanels panel) {
		switch (panel) {
			case WELCOME:
				setContentPane(new WelcomePanel(this));
				break;
			case TERMS:
				setContentPane(new TermsPanel(this));
				break;
			case OPTION_BUILD:
				setContentPane(new OptionBuildPanel(this));
				break;
			case CHOOSE_BUILD:
				setContentPane(new ChooseBuildPanel(this));
				break;
			case CHOOSE_INSTALL:
				setContentPane(new ChooseInstallPanel(this));
				break;
			case CHOOSE_JAR:
				setContentPane(new ChooseJarPanel(this));
				break;
			case OPTION_CONFIG:
				setContentPane(new OptionConfigPanel(this));
				break;
			case CHOOSE_CONFIG:
				setContentPane(new ChooseConfigPanel(this));
				break;
			case CHOOSE_SHORTCUT:
				setContentPane(new ChooseShortcutPanel(this));
				break;
			case INSTALL:
				setContentPane(new InstallerPanel(this));
				break;
		}
		pack();
		setLocation((int) (center.getX() - (getWidth() / 2)), (int) (center.getY() - (getHeight() / 2)));
	}
	
	protected void kill() {
		dispose();
		System.exit(0);
	}
	
	public void install(InstallerPanel installerPanel) {
		installer.createFileSystem();
		installerPanel.setProgress(10);
		installer.copyJar();
		installerPanel.setProgress(30);
		installer.copyModules();
		installerPanel.setProgress(50);
		installer.copyScoreOutputDirs();
		installerPanel.setProgress(60);
		installer.installIssues();
		installerPanel.setProgress(70);
		installer.writeDefaultSettings();
		installerPanel.setProgress(80);
		installer.copyFiles();
		installerPanel.setProgress(100);
	}
	
	public void setInstallPath(String installPath) {
		installer.setInstallPath(installPath);
	}
	
	public void setJarToCopy(String jarToCopy) {
		installer.setJarToCopy(jarToCopy);
	}
	
	public void setConfigFileToCopy(String configFileToCopy) {
		installer.setConfigFileToCopy(configFileToCopy);
	}
	
	public void setCopyInfo(JsonObject copyInfo) {
		installer.setCopyInfo(copyInfo);
	}
	
	public void setCopyInfo(String shortcut, String destination) {
		installer.setCopyInfo(new File(shortcut), new File(destination));
	}
	
	public void useConfig(File configFile) {
		installer.useConfig(configFile);
	}
}
