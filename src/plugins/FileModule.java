package plugins;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.ijg.darklight.core.Issue;
import com.ijg.darklight.core.ScoreModule;
import com.ijg.darklight.core.settings.Settings;

/**
 * Proof of concept for dynamically loading module settings from the config.json file
 * @author Isaac Grant
 * @version 1.0.0
 *
 */

public class FileModule extends ScoreModule {

	private HashMap<Issue, File[]> issueMap = new HashMap<Issue, File[]>();
	
	public FileModule() {
		loadSettings();
		
		Iterator<Issue> iter = issueMap.keySet().iterator();
		while (iter.hasNext()) {
			issues.add(iter.next());
		}
	}
	
	@Override
	public ArrayList<Issue> check() {
		Iterator<Issue> iter = issueMap.keySet().iterator();
		while (iter.hasNext()) {
			Issue curIssue = iter.next();
			File[] assocFiles = issueMap.get(curIssue);
			int existingFiles = 0;
			for (File assocFile : assocFiles) {
				if (assocFile.exists()) {
					existingFiles++;
				}
			}
			if (existingFiles == 0) {
				add(curIssue);
			} else {
				remove(curIssue);
			}
		}
		
		return issues;
	}
	
	/**
	 * Load issues from the config file
	 */
	@Override
	protected void loadSettings() {
		JsonObject fileSettings = Settings.getSubObject("FileModule");
		
		Iterator<Entry<String, JsonElement>> iter = fileSettings.entrySet().iterator();
		System.out.println("FileModule has loaded the following issues:");
		while (iter.hasNext()) {
			String issueName = iter.next().getKey();
			JsonArray rawIssueFiles = (JsonArray) ((JsonObject) fileSettings.get(issueName)).get("files");
			String issueDescription = ((JsonObject) fileSettings.get(issueName)).get("description").getAsString();
			File[] issueFiles = new File[rawIssueFiles.size()];
			
			System.out.println(issueName + ": " + issueDescription + ", with the following associated files:");
			for (int i = 0; i < issueFiles.length; ++i) {
				issueFiles[i] = new File(rawIssueFiles.get(i).getAsString());
				System.out.println(issueFiles[i]);
			}
			
			issueMap.put(new Issue(issueName, issueDescription), issueFiles);
		}
	}

}
