package com.ijg.darklight.unittests;

import static org.junit.Assert.*;

import org.junit.Test;

import com.ijg.darklight.core.settings.Parser;
import com.ijg.darklight.core.settings.Settings;

public class SettingsTest {

	@Test
	public void test() {
		// General
		assertTrue(Settings.get("progressfile").contains(System.getProperty("path.separator")));
		
		assertTrue(Parser.getParsed());
		
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
		
		
		Parser.destroy();
		assertNull(Parser.getConfig());
		assertFalse(Parser.getParsed());
	}

}
