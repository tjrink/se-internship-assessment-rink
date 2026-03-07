package com.rink.service.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Year;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.rink.client.ApiClient;
import com.rink.model.CountryDirectory;
import com.rink.service.CallCenter;
import com.rink.service.CallCenterUtilities;

public class CallCenterTest {

	CallCenter cc;
	
	@BeforeEach
	public void setUp() {
		//Creates an API client and sends a list of the required fields to retrieve data from server
		ApiClient client = new ApiClient();		
		String json_response = client.getAllCountries(client.buildFieldList());
		
		//Parses JSON response from server and creates an object for each country
		CountryDirectory cd = new CountryDirectory(json_response);
		
		//Establishes a random date in the current year for the c
		LocalDate d = CallCenterUtilities.generateRandomDate();
		
		//Creates a new CallCenter
		cc = new CallCenter(d, cd);
	}
	
	@Test
	//Tests generation of language lists
	public void countriesWithLanguageTest() {
		//Checks that there are no countries in the list if a non-existant language is used
		List<String> no_lang_list = cc.getCountriesWithLanguage("nolanguage");
		assertEquals(0, no_lang_list.size());
		
		//Checks that known nations appear in English list when given full language name
		List<String> english_list_full = cc.getCountriesWithLanguage("English");
		assertTrue(english_list_full.contains("NZL"));
		assertTrue(english_list_full.contains("USA"));
		
		//Checks that known nations appear in English list when given language code
		List<String> english_list_code = cc.getCountriesWithLanguage("eng");
		assertTrue(english_list_code.contains("NZL"));
		assertTrue(english_list_code.contains("USA"));
		
	}
	
	@Test
	//Confirms call center addTime is functioning
	public void addTimeTest() {
		assertEquals(LocalTime.MIDNIGHT, cc.getCurrentTime().toLocalTime()); //Confirms date starts at midnight
		
		cc.addTime(25); //Add 25 minutes to the time
		
		assertEquals(LocalTime.of(0, 25), cc.getCurrentTime().toLocalTime()); //Confirms that adding time is reflected in call center time
	}
}

