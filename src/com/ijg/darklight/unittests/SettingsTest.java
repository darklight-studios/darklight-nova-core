package com.ijg.darklight.unittests;

import static org.junit.Assert.*;

import org.junit.Test;

import com.ijg.darklight.core.settings.ConfigParser;
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
		// General
		assertTrue(Settings.get("progressfile").contains(System.getProperty("path.separator")));
		
		assertTrue(ConfigParser.getParsed());
		
		// General
		assertNotNull(Settings.getBool("idverification"));
		assertTrue("individualteam".contains(Settings.get("sessiontype")));
		
		// API
		assertNotNull(Settings.get("api.id"));
		assertNotNull(Long.parseLong(Settings.get("api.id")));
		assertTrue(Settings.get("namefile").contains(System.getProperty("path.separator")));
		assertEquals("http", Settings.get("api.protocol"));
		assertEquals("darklight-nova.herokuapp.com", Settings.get("api.server"));
		
		// Verification
		assertNotNull(Settings.getBool("verification.active"));
		assertNotNull(Settings.getJSON("verification.names"));
		assertNotNull(Settings.getJSON("verification.teams"));
		
		
		ConfigParser.destroy();
		assertNull(ConfigParser.getConfig());
		assertFalse(ConfigParser.getParsed());
	}

}
