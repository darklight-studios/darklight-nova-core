package plugins;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import org.ini4j.Wini;
import org.json.simple.JSONObject;

import com.ijg.darklight.core.Issue;
import com.ijg.darklight.core.ScoreModule;
import com.ijg.darklight.core.settings.ConfigParser;

public class SecEditModule extends ScoreModule {
	
	private Issue guestEnabled = KnownIssues.GUEST_ENABLED.constructIssue();
	private Issue passwordAge = KnownIssues.PASSWORD_AGE.constructIssue();
	private Issue passwordRequirements = KnownIssues.PASSWORD_REQUIREMENTS.constructIssue();
	private Issue auditing = KnownIssues.AUDITING.constructIssue();
	
	private HashMap<Issue, String[]> issueMap = new HashMap<Issue, String[]>();
	
	public SecEditModule() {
		loadSettings();
		if (KnownIssues.GUEST_ENABLED.getUsed()) issues.add(guestEnabled);
		if (KnownIssues.PASSWORD_AGE.getUsed()) issues.add(passwordAge);
		if (KnownIssues.PASSWORD_REQUIREMENTS.getUsed()) issues.add(passwordRequirements);
		if (KnownIssues.AUDITING.getUsed()) issues.add(auditing);
		
		Iterator<Issue> iter = issueMap.keySet().iterator();
		while (iter.hasNext()) {
			issues.add(iter.next());
		}
	}
	
	private boolean checkIssue(String[] data, Wini ini) {
		if (data[3].equals("==")) {
			if (ini.get(data[0], data[1]) == data[2]) {
				return true;
			}
		} else if (data[3].equals("!=")) {
			if (ini.get(data[0], data[1]) != data[2]) {
				return true;
			}
		} else if (data[3].equals("<=")) {
			if (Integer.parseInt(ini.get(data[0], data[1])) <= Integer.parseInt(data[3])) {
				return true;
			}
		} else if (data[3].equals(">=")) {
			if (Integer.parseInt(ini.get(data[0], data[1])) >= Integer.parseInt(data[3])) {
				return true;
			}
		}
		return false;
	}
	
	private boolean isGuestDisabled(Wini ini) {
		return (Integer.parseInt(ini.get("System Access", "EnableGuestAccount")) == 0);
	}
	
	private boolean isPasswordAgeFixed(Wini ini) {
		int minAge = Integer.parseInt(ini.get("System Access", "MinimumPasswordAge"));
		int maxAge = Integer.parseInt(ini.get("System Access", "MaximumPasswordAge"));
		if ((minAge > 4 && minAge < maxAge) && (maxAge >= 25)) {
			return true;
		}
		return false;
	}
	
	private boolean isPasswordRequirementsFixed(Wini ini) {
		if (Integer.parseInt(ini.get("System Access", "MinimumPasswordLength")) >= 8 &&
				Integer.parseInt(ini.get("System Access", "PasswordComplexity")) == 1) {
			return true;
		}
		return false;
	}
	
	private boolean isAuditingFixed(Wini ini) {
		if (Integer.parseInt(ini.get("Event Audit", "AuditSystemEvents")) > 0 && 
				Integer.parseInt(ini.get("Event Audit", "AuditLogonEvents")) > 0 && 
				Integer.parseInt(ini.get("Event Audit", "AuditObjectAccess")) > 0 && 
				Integer.parseInt(ini.get("Event Audit", "AuditPrivilegeUse")) > 0 && 
				Integer.parseInt(ini.get("Event Audit", "AuditPolicyChange")) > 0 && 
				Integer.parseInt(ini.get("Event Audit", "AuditAccountManage")) > 0 && 
				Integer.parseInt(ini.get("Event Audit", "AuditProcessTracking")) > 0 && 
				Integer.parseInt(ini.get("Event Audit", "AuditDSAccess")) > 0 && 
				Integer.parseInt(ini.get("Event Audit", "AuditAccountLogon")) > 0) {
			return true;
		}
		return false;
	}
	
	@Override
	protected void loadSettings() {
		JSONObject moduleSettings = (JSONObject) ConfigParser.getConfig().get("SecEditModule");
		
		@SuppressWarnings("unchecked")
		Iterator<String> iter = moduleSettings.keySet().iterator();
		while (iter.hasNext()) {
			String issueName = iter.next();
			JSONObject issueData = (JSONObject) moduleSettings.get(issueName);
			String custom = (String) issueData.get("custom");
			
			if (custom.equals("true")) {
				String issueDescription = (String) issueData.get("description");
				String category = (String) issueData.get("category");
				String key = (String) issueData.get("key");
				String value = (String) issueData.get("value");
				String specifier = (String) issueData.get("specifier");
				issueMap.put(new Issue(issueName, issueDescription), new String[] { category, key, value, specifier });
			} else {
				if (KnownIssues.contains(issueName)) {
					switch (issueName) {
						case "Guest Disabled":
							KnownIssues.GUEST_ENABLED.setUsed(true);
							break;
						case "Password Age":
							KnownIssues.PASSWORD_AGE.setUsed(true);
							break;
						case "Password Requirements":
							KnownIssues.PASSWORD_REQUIREMENTS.setUsed(true);
							break;
						case "Auditing":
							KnownIssues.AUDITING.setUsed(true);
					}
				}
			}
		}
	}
	
	@Override
	public ArrayList<Issue> check() {
		try {
			Process p = Runtime.getRuntime().exec("cmd.exe /c secedit /export /cfg C:\\Darklight\\secedit.txt");
			p.waitFor();
			
			Wini ini = new Wini(new File("C:\\Darklight\\secedit.txt"));
			
			if (KnownIssues.GUEST_ENABLED.getUsed()) {
				if (isGuestDisabled(ini)) {
					add(guestEnabled);
				} else {
					remove(guestEnabled);
				}
			}
			if (KnownIssues.PASSWORD_AGE.getUsed()) {
				if (isPasswordAgeFixed(ini)) {
					add(passwordAge);
				} else {
					remove(passwordAge);
				}
			}
			if (KnownIssues.PASSWORD_REQUIREMENTS.getUsed()) {
				if (isPasswordRequirementsFixed(ini)) {
					add(passwordRequirements);
				} else {
					remove(passwordRequirements);
				}
			}
			if (KnownIssues.AUDITING.getUsed()) {
				if (isAuditingFixed(ini)) {
					add(auditing);
				} else {
					remove(auditing);
				}
			}
			
			Iterator<Issue> iter = issueMap.keySet().iterator();
			while (iter.hasNext()) {
				Issue curIssue = iter.next();
				String[] data = issueMap.get(curIssue);
				if (checkIssue(data, ini)) {
					add(curIssue);
				} else {
					remove(curIssue);
				}
			}
			
		} catch (Exception e) {}
		return issues;
	}

	
	private enum KnownIssues {
		GUEST_ENABLED 			("Guest Disabled", "The guest account has been disabled."),
		PASSWORD_AGE			("Password Age", "Min and max password ages have been set appropriately"),
		PASSWORD_REQUIREMENTS	("Password Requirements", "Password length and complexity have been set appropriately"),
		AUDITING				("Auditing", "Auditing has been enabled");
		
		private String name, description;
		private boolean used = false;
		
		private KnownIssues(String name, String description) {
			this.name = name;
			this.description = description;
		}
		
		public static boolean contains(String name) {
			return "Guest Disabled; Password Age; Password Requirements; Auditing".contains(name);
		}
		
		public Issue constructIssue() {
			return new Issue(this.name, this.description);
		}
		
		public void setUsed(boolean value) {
			this.used = value;
		}
		
		public boolean getUsed() {
			return this.used;
		}
	}
}
