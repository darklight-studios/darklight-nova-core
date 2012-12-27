package plugins;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import com.ijg.darklight.core.Issue;
import com.ijg.darklight.core.ScoreModule;

public class FirewallModule extends ScoreModule {

	private Issue firewallDisabledIssue = new Issue("Firewall Enabled", "The Windows Firewall has been turned on.");
	
	public FirewallModule() {
		issues.add(firewallDisabledIssue);
	}
	
	private boolean isFirewallEnabled() {
		try {
			Process p;
			p = Runtime.getRuntime().exec("cmd.exe /c netsh advfirewall show publicprofile state");
			BufferedReader br = new BufferedReader(new InputStreamReader(
					p.getInputStream()));
			String line;
			boolean isOn = false;
			while ((line = br.readLine()) != null) {
				if (line.contains("ON")) {
					isOn = true;
				}
			}
			return isOn;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	@Override
	public ArrayList<Issue> check() {
		if (isFirewallEnabled()) {
			add(firewallDisabledIssue);
		} else {
			remove(firewallDisabledIssue);
		}
		return issues;
	}

}
