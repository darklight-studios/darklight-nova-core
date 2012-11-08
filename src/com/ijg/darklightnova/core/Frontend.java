package com.ijg.darklightnova.core;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import javax.swing.JOptionPane;

import com.ijg.darklightnova.gui.GUI;

public class Frontend {
	final private String[] validNames = { "keegan arnold", "michelle hulongbayan", "andrew williams", 
			"chris wotortsi", "do park", "jacob johnson", 
			"jeffrey rael", "luke robinson", "nathan teeter", 
			"savannah clemente", "tyler tilford-hamlin", "leeann wilson", 
			"andrew card", "isaac grant", "lucas nicodemus" };
	
	// TODO: fix other team once they come up with an actual team name
	final private String[] validTeams = { "any key", "team other" };
	
	final public String nameFile = "C:\\Darklight\\darklight-name.dat";
	
	private String userName = "unset";
	
	GUI gui;
	Engine engine;
	
	public Frontend(Engine engine) {
		this.engine = engine;
		gui = new GUI(engine);
	}
	
	public void promptForName() {
		if (readName() != null) {
			if (!userName.equals("unset")) {
				engine.authUser();
				return;
			}
		} else {
			String localName = "";
			String testName = "";
			try {
				if (engine.teamSession()) {
					localName = JOptionPane.showInputDialog(null, "Enter your team name", "It's all about identity", 1);
				} else {
					localName = JOptionPane.showInputDialog(null, "Enter your name", "It's all about identity", 1);
				}
				testName = localName.toLowerCase().trim();
			} catch (Exception e) {
				System.out.println("Error prompting for user: " + e.getMessage());
				promptForName();
				return;
			}
			
			if (engine.teamSession()) {
				if (!validTeam(testName)) {
					System.out.println("Invalid team entered: "
							+ localName.trim());
					promptForName();
					return;
				}
			} else {
				if (!validName(testName)) {
					System.out.println("Invalid name entered: "
							+ localName.trim());
					promptForName();
					return;
				}
			}
			setUserName(localName.trim());
			try {
				if (!writeName()) {
					promptForName();
					return;
				}
				engine.authUser();
			} catch (IOException e) {
				System.out
						.println("Error writing name file: " + e.getMessage());
				promptForName();
				return;
			}
		}
	}
	
	public boolean validName(String name) {
		for (String validName : validNames) {
			if (validName.contains(name)) return true;
		}
		return false;
	}
	
	public boolean validTeam(String team) {
		for (String validTeam : validTeams) {
			if (validTeam.contains(team)) return true;
		}
		return false;
	}
	
	public String readName() {
		File f = new File(nameFile);
		if (f.exists()) {
			try (Scanner s = new Scanner(f)) {
				setUserName(s.nextLine().trim());
				System.out
						.println("Found name file, contains: " + userName);
				return userName;
			} catch (FileNotFoundException e) {}
		}
		return null;
	}
	
	public boolean writeName() throws IOException {
		if (!userName.equals("unset")) {
			File f = new File(nameFile);
			if (!f.createNewFile()) {
				f.delete();
				f.createNewFile();
			}
			FileWriter fw = new FileWriter(f);
			fw.write(userName);
			fw.close();
			System.out.println("Wrote \"" + userName + "\" to name file");
			return true;
		}
		return false;
	}
	
	private void setUserName(String name) {
		System.out.println("Setting username: " + name);
		userName = name;
	}
	
	public String getUserName() {
		return userName;
	}
}
