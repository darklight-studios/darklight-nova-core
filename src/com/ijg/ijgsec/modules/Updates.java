package com.ijg.ijgsec.modules;

import java.io.IOException;
import java.util.Scanner;

import com.ijg.ijgsec.engine.AssessmentModule;
import com.ijg.ijgsec.engine.Module;
import com.ijg.ijgsec.engine.Vulnerability;

public class Updates extends Module {
	static Vulnerability regularUpdates = new Vulnerability("Updates", "Everything is up to date");
	static Vulnerability securityUpdates = new Vulnerability("Security Updates", "All security updates have been applied");
	
	public Updates(AssessmentModule assessModule) {
		super(assessModule, numVulns = 2);
	}
	
	private static char[][] checkForUpdates() {
		Process updateChecker = null;
		try {
			/*
			 * To ensure this command is runnable (without entering the user's password)
			 * add "%sudo ALL = NOPASSWD: /usr/bin/apt-get" to the sudoers file
			 */
			Runtime.getRuntime().exec("sudo apt-get update");
			updateChecker = Runtime.getRuntime().exec("/usr/lib/update-notifier/apt-check --human-readable");
		} catch (IOException e) {
			e.printStackTrace();
		}
		Scanner stdIn = new Scanner(updateChecker.getInputStream());
		StringBuilder updateStatus = new StringBuilder();

		while (stdIn.hasNext()) {
			updateStatus.append(stdIn.next());
		}
		
		int begin = 0;
		int end = updateStatus.indexOf("packagescanbeupdated");
		char[] totalUpdates = new char[end - begin + 1];
		updateStatus.getChars(begin, end, totalUpdates, 0);
		
		begin = updateStatus.indexOf(".") + 1;
		end = updateStatus.indexOf("updatesaresecurityupdates");
		char[] securityUpdates = new char[end - begin + 1];
		updateStatus.getChars(begin, end, securityUpdates, 0);
		updateChecker.destroy();
		return new char[][] { totalUpdates, securityUpdates };
	}
	
	public void fixed() {
		char[][] updates = checkForUpdates();
		if (updates[0][0] == '0') regularUpdates.found = true;
		else regularUpdates.found = false;
		if (updates[0][0] == '0') securityUpdates.found = true;
		else securityUpdates.found = false;
		
		vulnerabilities.clear();
		vulnerabilities.add(regularUpdates);
		vulnerabilities.add(securityUpdates);
	}
}
