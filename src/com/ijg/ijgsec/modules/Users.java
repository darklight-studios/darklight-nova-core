package com.ijg.ijgsec.modules;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import com.ijg.ijgsec.engine.AssessmentModule;
import com.ijg.ijgsec.engine.Module;
import com.ijg.ijgsec.engine.Vulnerability;

public class Users extends Module {
	static Vulnerability passwd = new Vulnerability("bad passwd file", "Regular user's user and group IDs changed to something other than 0 (0 is root only)");
	static Vulnerability sudoGroup = new Vulnerability("sudo group", "Regular user removed from the sudo group");
	static Vulnerability noPasswdLoginGroup = new Vulnerability("no password login", "Regular user is removed from nopasswdlogin group");
	
	
	public Users(AssessmentModule assessModule) {
		super(assessModule, numVulns = 3);
	}
	
	public boolean fixedPasswdFile() {
		Scanner scanner = null;
		String line = "";
		String[] splitLine;
		
		try {
			scanner = new Scanner(new File("/etc/passwd"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		while (scanner.hasNextLine()) {
			line = scanner.nextLine();
			if (line.contains("justapooruser:x:")) {
				break;
			}
		}
		
		splitLine = line.split(":");
		if (!splitLine[2].contentEquals("0") && !splitLine[3].contentEquals("0")) {
			return true;
		}
		
		return false;
	}
	
	public boolean[] fixedGroupFile() {
		boolean[] rtrn = { true, true };
		Scanner scanner = null;
		String line = "";
		String[] splitLine;
		
		try {
			scanner = new Scanner(new File("/etc/group"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		while (scanner.hasNextLine()) {
			line = scanner.nextLine();
			if (line.contains("sudo:x:")) {
				break;
			}
		}
		splitLine = line.split(":");
		for (String s : splitLine) {
			if (s.contains("justapooruser")) {
				rtrn[0] = false;
			}
		}
		
		scanner.reset();
		
		while (scanner.hasNextLine()) {
			line = scanner.nextLine();
			if (line.contains("nopasswdlogin:x:")) {
				break;
			}
		}
		splitLine = line.split(":");
		for (String s : splitLine) {
			if (s.contains("justapooruser")) {
				rtrn[1] = false;
			}
		}
		
		return rtrn;
	}
	
	public void fixed() {		
		if (fixedPasswdFile()) passwd.found = true;
		else passwd.found = false;
		
		boolean[] groupFile = fixedGroupFile();
		if (groupFile[0]) sudoGroup.found = true;
		else sudoGroup.found = false;
		if (groupFile[1]) noPasswdLoginGroup.found = true;
		else noPasswdLoginGroup.found = false;
		
		vulnerabilities.clear();
		vulnerabilities.add(passwd);
		vulnerabilities.add(sudoGroup);
		vulnerabilities.add(noPasswdLoginGroup);
	}
}
