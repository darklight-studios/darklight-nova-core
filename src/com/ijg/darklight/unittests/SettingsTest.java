package com.ijg.darklight.unittests;

import static org.junit.Assert.*;

import org.json.simple.JSONArray;
import org.junit.Test;

import com.ijg.darklight.core.settings.Parser;
import com.ijg.darklight.core.settings.Settings;

public class SettingsTest {

	@Test
	public void test() {
		// General
		assertTrue(((String) Settings.PROGRESS_FILE.value()).contains(System.getProperty("path.separator")));
		
		assertTrue(Parser.getParsed());
		
		// General
		assertTrue("truefalse".contains((String) Settings.ID_VERIFICATION.value()));
		assertTrue("individualteam".contains((String) Settings.SESSION_TYPE.value()));
		
		// API
		assertTrue(Settings.API_ID.value() instanceof Long);
		assertTrue(((String) Settings.NAME_FILE.value()).contains(System.getProperty("path.separator")));
		assertEquals("http", Settings.API_PROTOCOL.value());
		assertEquals("darklight-nova.herokuapp.com", Settings.API_SERVER.value());
		
		// Verification
		assertTrue("truefalse".contains((String) Settings.VERIFICATION_ACTIVE.value()));
		assertTrue(Settings.VERIFICATION_NAMES.value() instanceof JSONArray);
		assertTrue(Settings.VERIFICATION_TEAMS.value() instanceof JSONArray);
		
		
		Parser.destroy();
		assertNull(Parser.getConfig());
		assertFalse(Parser.getParsed());
	}

}
