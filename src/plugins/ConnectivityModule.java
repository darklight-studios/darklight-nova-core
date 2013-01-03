package plugins;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;

import com.ijg.darklight.core.Issue;
import com.ijg.darklight.core.ScoreModule;

public class ConnectivityModule extends ScoreModule {

	private Issue connectivity = new Issue("Network Connectivity Restored", "Internet connectivity has been restored.");
	
	//Taken from http://stackoverflow.com/questions/1139547/detect-internet-connection-using-java because InetAddress.isReachable doesn't work.
    @SuppressWarnings("unused")
	public boolean isInternetReachable()
    {
            try {
                    //make a URL to a known source
                    URL url = new URL("http://www.google.com");

                    //open a connection to that source
                    HttpURLConnection urlConnect = (HttpURLConnection)url.openConnection();

                    //trying to retrieve data from the source. If there
                    //is no connection, this line will fail
                    Object objData = urlConnect.getContent();

            } catch (UnknownHostException e) {
                    //e.printStackTrace();
                    return false;
            }
            catch (IOException e) {
                    //e.printStackTrace();
                    return false;
            }
            return true;
    }
	
	public ConnectivityModule() {
		issues.add(connectivity);
	}
	
	@Override
	protected void loadSettings() {}
	
	@Override
	public ArrayList<Issue> check() {
		if (isInternetReachable()) {
			add(connectivity);
		} else {
			remove(connectivity);
		}
		return issues;
	}

}
