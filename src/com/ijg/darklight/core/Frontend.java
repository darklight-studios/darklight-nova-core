package com.ijg.darklight.core;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.Scanner;

import javax.swing.JOptionPane;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.ijg.darklight.core.settings.Settings;
import com.ijg.darklight.gui.GUI;

/**
 * Darklight Core frontend, handles the gui and prompting for a name to use with the Darklight API
 * 
 * @author Isaac Grant
 * @author Lucas Nicodemus
 * @version .1
 *
 */

public class Frontend {

	private JsonArray validEntry;
	final private boolean ENTERY_VERIFICATION = Settings.getPropertyAsBool("verification", "active");
	
	final public String NAME_FILE = Settings.getProperty("api", "name");
	
	private String userName = "unset";
	
	GUI gui;
	Engine engine;
	
	/**
	 * 
	 * @param engine The instance of engine to which the frontend will belong
	 */
	public Frontend(Engine engine) {
		this.engine = engine;
		gui = new GUI(engine);
	}
	
	/**
	 * Ask the user to input a name to use with the DarklightAPI
	 */
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
				if (e instanceof NullPointerException) {
					System.out
							.println("No input from user, assuming termination is desired. Terminating...");
					System.exit(0);
				}
				System.out.println("Error prompting for user: " + e.getMessage());
				promptForName();
				return;
			}
			
			if (ENTERY_VERIFICATION) {
				validEntry = (engine.teamSession()) ? Settings.getPropertyAsJsonArray("verification", "teams") : Settings.getPropertyAsJsonArray("verification", "names");
				
				Iterator<JsonElement> iterator = validEntry.iterator();
				boolean promptForName = false;
				while (iterator.hasNext()) {
					JsonElement jsonElement = iterator.next();
					
					if (jsonElement.getAsString().contains(testName)) {
						promptForName = true;
					}
				}
				
				if (promptForName) {
					System.out.println("Invalid entry: " + localName.trim());
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
	
	/**
	 * Attempt to read the name stored in the name file
	 * 
	 * @return The name parsed from the name file
	 */
	public String readName() {
		File f = new File(NAME_FILE);
		try (Scanner s = new Scanner(f)) {
			setUserName(s.nextLine().trim());
			System.out
					.println("Found name file, contains: " + userName);
			return userName;
		} catch (FileNotFoundException e) {
			System.out.println("Error: Name file not found, should be " + NAME_FILE);
		}
		return null;
	}
	
	/**
	 * Write name entered by the user
	 * 
	 * @return True if writing to the name file succeeded
	 * @throws IOException
	 */
	public boolean writeName() throws IOException {
		if (!userName.equals("unset")) {
			File f = new File(NAME_FILE);
			if (!f.createNewFile()) {
				System.out.println("! create file");
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
	
	/**
	 * Set the local userName variable which is written to the name file
	 * @param name The name that will be used to write to the name file
	 */
	private void setUserName(String name) {
		System.out.println("Setting username: " + name);
		userName = name;
	}
	
	/**
	 * Get the name that was read from the name file
	 * @return The name read from the name file
	 */
	public String getUserName() {
		return userName;
	}
}
