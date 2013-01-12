package com.ijg.darklight.sdk.core;

import java.util.ArrayList;
import java.util.HashMap;

import me.shanked.nicatronTg.darklight.view.VulnerabilityOutput;

public class ModuleHandler {
	private CoreEngine engine;
	
	private double total;
	
	private ArrayList<Issue> issues = new ArrayList<Issue>();
	private ArrayList<ScoreModule> modules;
	
	private VulnerabilityOutput outputManager;
	
	public ModuleHandler(CoreEngine engine, ArrayList<ScoreModule> loadedModules) {
		this.engine = engine;
		outputManager = new VulnerabilityOutput(this);
		
		modules = loadedModules;
		for (ScoreModule module : modules) {
			total += module.getIssueCount();
		}
	}
	
	public HashMap<String, String> getFixedIssues() {
		HashMap<String, String> issuesMap = new HashMap<String, String>();
		for (Issue issue : issues) {
			issuesMap.put(issue.getName(), issue.getDescription());
		}
		return issuesMap;
	}
	
	public void checkAllVulnerabilities() {
		boolean changed = false;
		for (ScoreModule module : modules) {
			ArrayList<Issue> modifiedIssues = module.check();
			for (Issue issue : modifiedIssues) {
				if (issue.getFixed() && !issues.contains(issue)) {
					issues.add(issue);
					changed = true;
				} else if (!issue.getFixed() && issues.contains(issue)) {
					issues.remove(issue);
					changed = true;
				}
			}
		}
		
		if (changed) {
			outputManager.writeNewOutput();
			
			engine.authUser();
			engine.sendUpdate(issues.size(), getFixedIssues());
		}
	}
	
	public ArrayList<Issue> getIssues() {
		return issues;
	}
	
	public int getTotalIssueCount() {
		return (int) total;
	}
	
	public int getFixedIssueCount() {
		return issues.size();
	}
	
	public String getFixedIssuePercent() {
		return "" + ((int) (((double) issues.size()) / total) * 100) + "%";
	}
}
