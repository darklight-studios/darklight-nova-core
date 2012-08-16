package com.ijg.ijgsec.modules;

import java.io.File;
import com.ijg.ijgsec.engine.AssessmentModule;
import com.ijg.ijgsec.engine.Module;
import com.ijg.ijgsec.engine.Vulnerability;

public class MaliciousFiles extends Module {
	static Vulnerability trollol = new Vulnerability("/home/imauser/Downloads/trollol", "Malicious file: '/home/imauser/Downloads/trollol' has been deleted");
	static Vulnerability iravirus = new Vulnerability("/home/imauser/Downloads/IRAVIRUS", "Malicious file: '/home/imauser/Downloads/IRAVIRUS' has been deleted");
	static Vulnerability family = new Vulnerability("/home/imauser/Pictures/family.jpg", "Malicious file: '/home/imauser/Pictures/family.jpg' has been deleted");

	public MaliciousFiles(AssessmentModule assessModule) {
		super(assessModule, numVulns = 3);
	}
	
	public void fixed() {
		vulnerabilities.clear();
		
		if (!new File(trollol.name).exists()) trollol.found = true;
		else trollol.found = false;
		
		if (!new File(iravirus.name).exists()) iravirus.found = true;
		else iravirus.found = false;
		
		if (!new File(family.name).exists()) family.found = true;
		else family.found = false;
		
		vulnerabilities.add(trollol);
		vulnerabilities.add(iravirus);
		vulnerabilities.add(family);
	}
}
