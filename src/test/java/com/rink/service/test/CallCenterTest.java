package com.rink.service.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.rink.model.CountryDirectory;
import com.rink.service.CallCenter;

public class CallCenterTest {

	CallCenter cc;

	@BeforeEach
	public void setUp() {
		String dummy_json = """
											    [
				  {
				    "capitalInfo": { "latlng": [-41.3, 174.78] },
				    "name": { "common": "New Zealand", "official": "New Zealand" },
				    "languages": { "eng": "English", "mri": "Māori", "nzs": "New Zealand Sign Language" },
				    "cca3": "NZL",
				    "capital": ["Wellington"],
				    "timezones": ["UTC-11:00", "UTC-10:00", "UTC+12:00", "UTC+12:45", "UTC+13:00"]
				  },
				  {
				    "capitalInfo": { "latlng": [35.68, 139.75] },
				    "name": { "common": "Japan", "official": "Japan" },
				    "languages": { "jpn": "Japanese" },
				    "cca3": "JPN",
				    "capital": ["Tokyo"],
				    "timezones": ["UTC+09:00"]
				  },
				  {
				    "capitalInfo": { "latlng": [38.89, -77.05] },
				    "name": { "common": "United States", "official": "United States of America" },
				    "languages": { "eng": "English" },
				    "cca3": "USA",
				    "capital": ["Washington, D.C."],
				    "timezones": ["UTC-05:00"]
				  },
				  {
				    "capitalInfo": { "latlng": [-35.27, 149.13] },
				    "name": { "common": "Australia", "official": "Commonwealth of Australia" },
				    "languages": { "eng": "English" },
				    "cca3": "AUS",
				    "capital": ["Canberra"],
				    "timezones": ["UTC+10:00"]
				  },
				  {
				    "capitalInfo": { "latlng": [0.0, 0.0] },
				    "name": { "common": "Chaos Land", "official": "The Republic of Null" },
				    "languages": {},
				    "cca3": "CHL",
				    "capital": ["Void"],
				    "timezones": []
				  }
				]							    """;
		// Parses JSON response from server and creates an object for each country
		CountryDirectory cd = new CountryDirectory(dummy_json);

		// Creates a new CallCenter
		cc = new CallCenter(cd);
	}

	@Test
	// Tests generation of language lists
	public void countriesWithLanguageTest() {
		// Checks that there are no countries in the list if a non-existant language is
		// used
		List<String> no_lang_list = cc.getCountriesWithLanguage("nolanguage");
		assertEquals(0, no_lang_list.size());

		// Checks that known nations appear in English list when given full language
		// name
		List<String> english_list_full = cc.getCountriesWithLanguage("English");
		assertTrue(english_list_full.contains("NZL"));
		assertTrue(english_list_full.contains("USA"));
		

		// Checks that known nations appear in English list when given language code
		List<String> english_list_code = cc.getCountriesWithLanguage("eng");
		assertTrue(english_list_code.contains("NZL"));
		assertTrue(english_list_code.contains("USA"));

	}
}
