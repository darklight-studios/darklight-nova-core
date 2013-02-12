package com.ijg.darklight.build;

import java.awt.Point;
import java.io.File;

import javax.swing.JFrame;

import com.google.gson.JsonObject;

public class InstallerFrame extends JFrame {
	private static final long serialVersionUID = 8468796890232401893L;
	
	private DarklightInstaller3 installer;
	private Point center;
	
	public InstallerFrame(String title, DarklightInstaller3 installer, Point center) {
		super(title);
		this.installer = installer;
		this.center = center;
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		setVisible(true);
		changePanel(EPanels.WELCOME);
	}
	
	protected void changePanel(EPanels panel) {
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
				break;
			default:
				break;
		}
		pack();
		setLocation((int) (center.getX() - (getWidth() / 2)), (int) (center.getY() - (getHeight() / 2)));
	}
	
	protected void kill() {
		dispose();
		System.exit(0);
	}
	
	protected void setInstallPath(String installPath) {
		installer.setInstallPath(installPath);
	}
	
	protected void setJarToCopy(String jarToCopy) {
		installer.setJarToCopy(jarToCopy);
	}
	
	protected void setConfigFileToCopy(String configFileToCopy) {
		installer.setConfigFileToCopy(configFileToCopy);
	}
	
	protected void setCopyInfo(JsonObject copyInfo) {
		installer.setCopyInfo(copyInfo);
	}
	
	protected void useConfig(File configFile) {
		installer.useConfig(configFile);
	}
}
