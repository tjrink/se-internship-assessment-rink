package com.rink.app;

import java.util.ArrayList;

import com.rink.client.ApiClient;
import com.rink.model.Country;
import com.rink.model.CountryDirectory;
import com.rink.service.CallCenter;
import com.rink.service.CallCenterUtilities;

public class App {

	public static void main(String[] args) {
		CallCenter cc = new CallCenter(CallCenterUtilities.generateRandomDate());
		System.out.println(cc.getCurrentTime().toLocalTime());		
		
//		ApiClient client = new ApiClient();
//		
//		;
//		//Sets the basic fields to be requested when creating country objects
//		ArrayList<String> fields = new ArrayList<>();
//		fields.add("name");
//		fields.add("cca3");
//		fields.add("national_bird");
//		fields.add("capital");
//		fields.add("languages");
//		fields.add("timezones");
//		fields.add("capitalInfo");
//
//		String json_response = client.getAllCountries(fields);
//		
//		CountryDirectory cd = new CountryDirectory(json_response);
//		
//		Country nz = cd.getCountryByCode("NZL");
//		System.out.println(cd.getHashCode());
	
	}
}
