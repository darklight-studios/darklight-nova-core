package plugins;

import java.util.ArrayList;

import com.ijg.darklight.core.Issue;
import com.ijg.darklight.core.ScoreModule;

public class ExampleScoringModule extends ScoreModule {
	/*
	 * This is an example scoring module
	 */
	
	// vulnerability declarations
	private Issue exampleIssue = new Issue("Example Vulnerability", "This is an example vulnerability.");
	private Issue exampleIssue2 = new Issue("Example Vulnerability", "This is an example vulnerability.");
	private Issue exampleIssue3 = new Issue("Example Vulnerability", "This is an example vulnerability.");
	
	public ExampleScoringModule() {
		loadSettings();
		issues.add(exampleIssue);
		issues.add(exampleIssue2);
		issues.add(exampleIssue3);
	}
	
	private boolean fixedExampleVulnerability() {
		/*
		 * Private methods are used to check whether
		 * or not the vulnerability has been found, usually
		 * they are booleans, though sometimes other
		 * return types are warranted.
		 */
		return true;
	}
	
	private boolean fixed2() {
		return false;
	}
	
	private boolean fixed3() {
		return false;
	}
	
	@Override
	protected void loadSettings() {
		/*
		 * Load any settings from the config file here.
		 * The only convention is to get the settings specific to this module
		 * using the following statement:
		 * JSONObject moduleSettings = (JSONObject) ConfigParser.getConfig().get("ExampleScoringModule");
		 */
	}
	
	@Override
	public ArrayList<Issue> check() {
		if (fixedExampleVulnerability()) {
			add(exampleIssue);
		} else {
			remove(exampleIssue);
		}
		
		if (fixed2()) {
			add(exampleIssue2);
		} else {
			remove(exampleIssue3);
		}
		
		if (fixed3()) {
			add(exampleIssue3);
		} else {
			remove(exampleIssue3);
		}
		return issues;
	}
}
