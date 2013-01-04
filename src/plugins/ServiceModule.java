package plugins;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.ijg.darklight.core.Issue;
import com.ijg.darklight.core.ScoreModule;
import com.ijg.darklight.core.settings.Settings;

public class ServiceModule extends ScoreModule {

	private HashMap<Issue, String> issueMap = new HashMap<Issue, String>();
	
	public ServiceModule() {
		loadSettings();
		
		Iterator<Issue> iter = issueMap.keySet().iterator();
		while (iter.hasNext()) {
			issues.add(iter.next());
		}
	}
	
	private boolean isServiceOperational(String service) {
		try {
			Process p;
			p = Runtime.getRuntime().exec("cmd.exe /c sc query " + service);
			BufferedReader br = new BufferedReader(new InputStreamReader(
					p.getInputStream()));
			String line;
			while ((line = br.readLine()) != null) {
				if (line.contains("1060") || line.contains("1058") || line.contains("1048")) {
					return false;
				}
			}
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return true;
		}
	}
	
	@Override
	protected void loadSettings() {
		JsonObject moduleSettings = Settings.getSubObject("ServiceModule");
		
		Iterator<Entry<String, JsonElement>> iter = moduleSettings.entrySet().iterator();
		while (iter.hasNext()) {
			Entry<String, JsonElement> issue = iter.next();
			JsonObject issueData = (JsonObject) moduleSettings.get(issue.getKey());
			String issueDescription = issueData.get("description").getAsString();
			String serviceName = issueData.get("service").getAsString();
			
			issueMap.put(new Issue(issue.getKey(), issueDescription), serviceName);
		}
	}
	
	@Override
	public ArrayList<Issue> check() {
		Iterator<Issue> iter = issueMap.keySet().iterator();
		while (iter.hasNext()) {
			Issue curIssue = iter.next();
			if (!isServiceOperational(issueMap.get(curIssue))) {
				add(curIssue);
			} else {
				remove(curIssue);
			}
		}
		return issues;
	}

}
