package plugins;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import com.ijg.darklight.core.Issue;
import com.ijg.darklight.core.ScoreModule;

public class PathModule extends ScoreModule {

	private Issue fakePath = new Issue("Malicious PATH",
			"Path variable no longer contains the invalid 'C:\\Program Files (x86)\\Windows NT' path.");

	public PathModule() {
		issues.add(fakePath);
	}

	private boolean isPathClean() {
		try {
			Process p;
			p = Runtime.getRuntime().exec("cmd.exe /c echo %PATH%");
			BufferedReader br = new BufferedReader(new InputStreamReader(
					p.getInputStream()));
			String path = "";
			String line = "";
			while ((line = br.readLine()) != null) {
				path += line;
			}
			if (path.contains("C:\\Program Files (x86)\\Windows NT")) {
				return false;
			}
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public ArrayList<Issue> check() {
		if (isPathClean()) {
			add(fakePath);
		} else {
			remove(fakePath);
		}
		return issues;
	}

}
