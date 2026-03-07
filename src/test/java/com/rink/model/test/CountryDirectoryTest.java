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
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CountryDirectoryTest {
	
	private CountryDirectory cd;
	
	@BeforeEach
	public void setUpCountryDirectory() {
		ApiClient client = new ApiClient();

		
		//Sets the basic fields to be requested when creating country objects
		ArrayList<String> fields = new ArrayList<>();
		fields.add("name");
		fields.add("cca3");
		fields.add("capital");
		fields.add("languages");
		fields.add("timezones");
		fields.add("capitalInfo");

		String json_response = client.getAllCountries(fields);
		
		cd = new CountryDirectory(json_response);
	}
	
	
	@Test
	//Checks whether SASL is an official langauge of South Africa
	public void southAfricanSignLanguageTest() {
		Country sa = cd.getCountryByCode("ZAF"); //Stores the South African object for testing
		
		assertNotNull(sa); //Asserts that the South African object was found
		assertTrue(sa.usesLanguage("sfs")); //Asserts that SASL is an official language using the ISO 639-3 code
		assertTrue(sa.usesLanguage("South African Sign Language")); //Asserst that SASL is an official language using the official name
	}
	
}
