package com.ijg.ijgsec.modules;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import com.ijg.ijgsec.engine.AssessmentModule;
import com.ijg.ijgsec.engine.Module;
import com.ijg.ijgsec.engine.Vulnerability;

public class Hosts extends Module {
	static Vulnerability hostsVuln = new Vulnerability("Hosts file", "The /etc/hosts file has been cleansed of its impurities (redirects)");
	String[] badHostsEntries = new String[] { "127.0.0.1 https://www.google.com", "127.0.0.1 http://www.google.com", "127.0.0.1 www.bing.com", "127.0.0.1 www.wikipedia.org", 
			"127.0.0.1 stackoverflow.com", "127.0.0.1 superuser.com", "127.0.0.1 https://wiki.ubuntu.com", "127.0.0.1 http://wiki.ubuntu.com", 
			"127.0.0.1 www.ubuntu.com", "127.0.0.1 www.facebook.com", "127.0.0.1 www.youtube.com", "127.0.0.1 www.bleepingcomputer.com", "127.0.0.1 www.clamav.net", 
			"127.0.0.1 https://launchpad.net", "127.0.0.1 http://launchpad.net"};
	
	File hostsFile = new File("/etc/hosts"); //Linux
	//File hostsFile = new File("C:\Windows\System32\drivers\etc\hosts") //Windows
	
	public Hosts(AssessmentModule assessModule) {
		super(assessModule, numVulns = 1);
	}
	
	public boolean hostsNotFixed() {
		ArrayList<String> hostsContent = new ArrayList<String>();
		
		Scanner hosts = null;
		try {
			hosts = new Scanner(new FileInputStream(hostsFile));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		while (hosts.hasNextLine()) {
			hostsContent.add(hosts.nextLine());
		}
		
		for (String entry : badHostsEntries) {
			if (hostsContent.indexOf(entry) != -1) {
				return true;
			}
		}
		
		return false;
	}
	
	public void fixed() {
		if (!hostsNotFixed()) hostsVuln.found = true;
		else hostsVuln.found = false;
		
		vulnerabilities.clear();
		vulnerabilities.add(hostsVuln);
	}
}
