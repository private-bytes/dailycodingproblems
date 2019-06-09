package org.privatebytes.problem329;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class Problem329Test {

	private Problem329 unit;


	@BeforeEach
	public void setup() {
		unit = new Problem329();
	}

	@Test
	void test1() {
		Map<String, List<String>> guy_preferences = new HashMap<>();
		guy_preferences.put("andrew", new ArrayList<>(List.of("caroline", "abigail", "betty")));
		guy_preferences.put("bill", new ArrayList<>(List.of("caroline", "betty", "abigail")));
		guy_preferences.put("chester", new ArrayList<>(List.of("betty", "caroline", "abigail")));

		Map<String, List<String>> gal_preferences = new HashMap<>();
		gal_preferences.put("abigail", new ArrayList<>(List.of("andrew", "bill", "chester")));
		gal_preferences.put("betty", new ArrayList<>(List.of("bill", "andrew", "chester")));
		gal_preferences.put("caroline", new ArrayList<>(List.of("bill", "chester", "andrew")));
	
		unit.solve(guy_preferences, gal_preferences);
	}
	
	@Test
	void test2() {
		Map<String, List<String>> guy_preferences = new HashMap<>();
		guy_preferences.put("aurel", new ArrayList<>(List.of("catinca", "maria","aglaia", "safta")));
		guy_preferences.put("dorel", new ArrayList<>(List.of("maria","catinca", "safta", "aglaia")));
		guy_preferences.put("valerica", new ArrayList<>(List.of("safta", "catinca", "aglaia","maria")));
		guy_preferences.put("neculai", new ArrayList<>(List.of("safta", "maria", "catinca", "aglaia")));

		Map<String, List<String>> gal_preferences = new HashMap<>();
		gal_preferences.put("aglaia", new ArrayList<>(List.of("neculai","aurel", "dorel", "valerica")));
		gal_preferences.put("catinca", new ArrayList<>(List.of("dorel", "valerica", "aurel", "neculai")));
		gal_preferences.put("safta", new ArrayList<>(List.of("dorel", "neculai", "aurel", "valerica")));
		gal_preferences.put("maria", new ArrayList<>(List.of("aurel", "neculai", "valerica", "dorel")));
	
		unit.solve(guy_preferences, gal_preferences);
	}	
	
}
