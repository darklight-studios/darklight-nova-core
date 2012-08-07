package com.ijg.ijgsec.modules;

import java.util.ArrayList;

import com.ijg.ijgsec.engine.AssessmentModule;
import com.ijg.ijgsec.engine.Module;
import com.ijg.ijgsec.engine.Vulnerability;

public class ExampleScoringModule extends Module {
	/*
	 * This is an example scoring module
	 */
	
	// static vulnerability declarations
	static Vulnerability exampleVulnerability = new Vulnerability("Example Vulnerability", "This is an example vulnerability for the core IJGSec engine");
	
	public ExampleScoringModule(AssessmentModule assessModule) {
		/*
		 * the numVulns is assigned in the super constructor
		 * call to allow for telling the AssessmentModule
		 * how many vulns this module has without manually
		 * doing it with a seperate call to the AssessmentModule
		 */
		super(assessModule, numVulns = 2);
	}
	
	private boolean fixedExampleVulnerability() {
		/*
		 * Private methods are used to check whether
		 * or not the vulnerability has been found, usually
		 * they are booleans, though sometimes other
		 * return types are warranted.
		 */
		return false;
	}
	
	public ArrayList<Vulnerability> fixed() {
		/*
		 * The fixed() method inherited from the superclass
		 * should be overridden in every scoring module class.
		 * This method should include a call to clear the 
		 * vulnerabilities ArrayList, add any found vulnerabilities,
		 * and finally return the vulnerabilities ArrayList
		 */
		
		vulnerabilities.clear();
		if (fixedExampleVulnerability()) vulnerabilities.add(exampleVulnerability);
		return vulnerabilities;
	}
}
