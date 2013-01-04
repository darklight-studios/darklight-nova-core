package plugins;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.json.simple.JSONObject;

import com.ijg.darklight.core.Issue;
import com.ijg.darklight.core.ScoreModule;
import com.ijg.darklight.core.settings.ConfigParser;

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
		JSONObject moduleSettings = (JSONObject) ConfigParser.getConfig().get("ServiceModule");
		
		@SuppressWarnings("unchecked")
		Iterator<String> iter = moduleSettings.keySet().iterator();
		while (iter.hasNext()) {
			String issueName = iter.next();
			JSONObject issueData = (JSONObject) moduleSettings.get(issueName);
			String issueDescription = (String) issueData.get("description");
			String serviceName = (String) issueData.get("service");
			
			issueMap.put(new Issue(issueName, issueDescription), serviceName);
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
