package com.ijg.ijgsec.engine;

import java.util.ArrayList;

public class Module {
	public static int numVulns;
	protected ArrayList<Vulnerability> vulnerabilities;
	
	/*
	 * All scoring modules are subclassed from
	 * this
	 */
	
	public Module(AssessmentModule assessModule, int nVulns) {
		assessModule.total += nVulns;
		vulnerabilities = new ArrayList<Vulnerability>();
	}
	
	public void fixed() { }
}
