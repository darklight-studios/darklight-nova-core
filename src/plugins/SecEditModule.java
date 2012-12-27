package plugins;

import java.io.File;
import java.util.ArrayList;

import org.ini4j.Wini;

import com.ijg.darklight.core.Issue;
import com.ijg.darklight.core.ScoreModule;

public class SecEditModule extends ScoreModule {

	private Issue guestEnabled = new Issue("Guest Disabled", "The guest account has been disabled.");
	public SecEditModule() {
		issues.add(guestEnabled);
	}
	
	private boolean isGuestEnabled() {
		return (Integer.parseInt(getSecEditValue("System Access", "EnableGuestAccount")) == 1 ? true : false);
	}
	
	private String getSecEditValue(String category, String key) {
		try {
			Process p;
			p = Runtime.getRuntime().exec("cmd.exe /c secedit /export /cfg C:\\ScoreEngine\\secedit.txt");
			p.waitFor();

			Wini ini = new Wini(new File(("C:\\ScoreEngine\\secedit.txt")));
			return ini.get(category, key);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public ArrayList<Issue> check() {
		if (!isGuestEnabled()) {
			add(guestEnabled);
		} else {
			remove(guestEnabled);
		}
		return issues;
	}

}
