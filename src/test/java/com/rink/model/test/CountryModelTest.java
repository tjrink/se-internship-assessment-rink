package com.rink.model.test;

import java.util.ArrayList;

import com.rink.client.ApiClient;
import com.rink.model.Country;
import com.rink.model.CountryDirectory;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class CountryModelTest {
	
	private Country nz;
	
	@BeforeEach
	public void setUpCountry() {
		String nz_json = """
			    [
			      {
			        "capitalInfo": { "latlng": [-41.3, 174.78] },
			        "name": {
			          "common": "New Zealand",
			          "official": "New Zealand"
			        },
			        "languages": {
			          "eng": "English",
			          "mri": "Māori",
			          "nzs": "New Zealand Sign Language"
			        },
			        "cca3": "NZL",
			        "idd": { "root": "+6", "suffixes": ["4"] },
			        "capital": ["Wellington"],
			        "timezones": ["UTC-11:00", "UTC-10:00", "UTC+12:00", "UTC+12:45", "UTC+13:00"]
			      }
			    ]
			    """;
		
		CountryDirectory cd = new CountryDirectory(nz_json);
		nz = cd.getCountryByCode("NZL");
	}
	
	@Test
	public void countryNameTest() {
		assertEquals("New Zealand", nz.getCommonName());
	}
	
	@Test
	public void countryCapitalTest() {
		assertEquals("Wellington", nz.getCapital());
	}
	
	@Test
	public void countryLanguagesTest() {
		assertTrue(nz.usesLanguage("eng")); //Tests English using language code - First language in list works with code
		assertTrue(nz.usesLanguage("English")); //Tests English using full name - First langauge in list works with full name
		assertTrue(nz.usesLanguage("mri")); //Tests Maori using code - Later element works with code
		assertTrue(nz.usesLanguage("New Zealand Sign Language")); //Tests NZSL using full name - Later element works with name
		
		assertFalse(nz.usesLanguage("spa")); //Tests Spanish using langauge code 
	}
	
	@Test
	public void countryTimezonesTest() {
		assertEquals(5, nz.getTimeZones().size()); //Appropriate number of time zones appear
		assertTrue(nz.getTimeZones().contains("UTC-11:00")); //First item appears
		assertTrue(nz.getTimeZones().contains("UTC+12:45")); //Later item appears
		
		assertFalse(nz.getTimeZones().contains("UTC -11")); //Reject partial match
	}
	
	

	
}
