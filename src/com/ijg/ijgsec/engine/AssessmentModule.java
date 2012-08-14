package com.ijg.ijgsec.engine;

import java.util.ArrayList;

public class AssessmentModule {
	private Engine engine;
	
	// Accessed with toString(), used in the title of the GUI window
	protected String name = "INSERT IMAGE NAME HERE";
	
	// Total vulnerabilities
	public double total;
	
	// List of all found vulnerabilities
	public ArrayList<Vulnerability> vulnerabilities = new ArrayList<Vulnerability>();
	
	// List of the scoring modules used
	public ArrayList<Module> modules = new ArrayList<Module>();
	
	public AssessmentModule(Engine engine) {
		this.engine = engine;
		/*
		 * add scoring modules
		 * to the vulnerability list
		 * here.
		 * Example:
		modules.add(new ExampleScoreingModule(this));*/
		
		engine.setTotal(total);
	}
	
	protected void assess() {
		/*
		 * changed variable used so that the engine
		 * only writes to the progress file if
		 * there have been any changes, so no time is
		 * wasted writing a new progress file when there
		 * are no changes
		 */
		int changed = 0;
		for (Module module : modules) {
			module.fixed();
			for (Vulnerability vulnerability : module.vulnerabilities) {
				if (!vulnerabilities.contains(vulnerability)) {
					if (vulnerability.found && vulnerability != null) {
						addVulnerability(vulnerability);
						changed++;
					}
				} else {
					if (!vulnerability.found) {
						removeVulnerability(vulnerability);
						changed++;
					}
				}
			}
		}
		if (changed > 0) engine.writeFoundList(); // if there are newly found vulnerabilities, rewrite the progress file
	}
	
	private void update() {
		engine.setPercent(((double) vulnerabilities.size()) / total);
	}
	
	public void report() {
		engine.update();
		assess();
		update();
	}
	
	private void addVulnerability(Vulnerability vulnerability) {
		vulnerabilities.add(vulnerability);
		engine.addVulnerability(vulnerability);
	}
	
	private void removeVulnerability(Vulnerability vulnerability) {
		vulnerabilities.remove(vulnerability);
		engine.removeVulnerability(vulnerability);
	}
	
	public String toString() {
		return name;
	}
}
