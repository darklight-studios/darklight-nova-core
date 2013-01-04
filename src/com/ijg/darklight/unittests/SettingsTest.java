package com.ijg.darklight.unittests;

import org.junit.Test;

import com.ijg.darklight.core.settings.Settings;

/**
 * @author Isaac Grant
 * @author Lucas Nicodemus
 * @version .1
 *
 */

public class SettingsTest {

	@Test
	public void test() {
		Settings.getProperty("general", "progress");
		
	}

}
