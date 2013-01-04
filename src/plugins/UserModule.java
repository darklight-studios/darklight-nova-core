package plugins;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import com.ijg.darklight.core.Issue;
import com.ijg.darklight.core.ScoreModule;

public class UserModule extends ScoreModule {

	private Issue fakeAccountIsAdmin = new Issue("Fake Account Removed", "The fake account IWAM_WIN-1L7O4K56QTM no longer has administrative rights, is disabled, or is entirely gone.");
	
	public UserModule() {
		loadSettings();
		issues.add(fakeAccountIsAdmin);
	}
	
	private boolean accountExists(String name) {
		try {
			Process p;
			p = Runtime.getRuntime().exec("cmd.exe /c net user");
			BufferedReader br = new BufferedReader(new InputStreamReader(
					p.getInputStream()));
			String line;
			while ((line = br.readLine()) != null) {
				if (line.contains(name)) {
					return true;
				}
			}
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return true;
		}
	}
	
	private String getAccountValue(String name, String key) {
		Process process;
		try {
			process = Runtime.getRuntime().exec("cmd.exe /c net user " + name);
			BufferedReader bfr = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String linex;
			while ((linex = bfr.readLine()) != null) {
				if (linex.contains(key)) {
					linex = linex.replace(key, "");
					return linex.trim();
				}
			}
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private boolean accountSafe() {
		String accountName = "IWAM_WIN-1L7O4K56QTM";
		if (accountExists(accountName)) {
			if (getAccountValue(accountName, "Account active").equals("Yes")) {
				if (getAccountValue(accountName, "Local Group Memberships").contains("Administrators")) {
					return false;
				} else {
					return true;
				}
			} else {
				return true;
			}
		} else {
			return true;
		}
	}
	
	@Override
	protected void loadSettings() {
		//JSONObject moduleSettings = (JSONObject) ConfigParser.getConfig().get("UserModule");
	}
	
	@Override
	public ArrayList<Issue> check() {
		
		if (accountSafe()) {
			add(fakeAccountIsAdmin);
		} else {
			remove(fakeAccountIsAdmin);
		}
		return issues;
	}

}
