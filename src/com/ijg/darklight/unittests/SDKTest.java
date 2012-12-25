package com.ijg.darklight.unittests;

import static org.junit.Assert.*;

import org.junit.Test;

import com.ijg.darklight.core.settings.Settings;
import com.ijg.darklight.web.sdk.DarklightSDK;

/**
 * @author Isaac Grant
 * @author Lucas Nicodemus
 * @version .1
 *
 */

public class SDKTest {

	@Test
	public void test() {
		DarklightSDK sdk = new DarklightSDK(Settings.get("api.protocol"), Settings.get("api.server"), Long.parseLong(Settings.get("api.id")));
		
		assertNotNull(sdk.api());
		
		
	}

}
