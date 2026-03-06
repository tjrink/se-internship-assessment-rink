package com.rink.app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rink.client.ApiClient;
import com.rink.model.Country;
import com.rink.model.CountryDirectory;

public class App {

	public static void main(String[] args) {
		ApiClient client = new ApiClient();
		
		
		//Sets the basic fields to be requested when creating country objects
		ArrayList<String> fields = new ArrayList<>();
		fields.add("name");
		fields.add("cca3");
		fields.add("idd");
		fields.add("capital");
		fields.add("languages");
		fields.add("timezones");
		fields.add("capitalInfo");

		String json_response = client.getAllCountries(fields);
		
		CountryDirectory cd = new CountryDirectory(json_response);
		Country nz = cd.getCountryByCode("NZL");
		






	}
}
