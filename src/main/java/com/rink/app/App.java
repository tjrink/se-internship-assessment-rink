package com.rink.app;

import java.time.LocalDate;

import com.rink.client.ApiClient;
import com.rink.model.CountryDirectory;
import com.rink.service.CallCenter;
import com.rink.service.CallCenterUtilities;

public class App {

	public static void main(String[] args) {
		
//		Creates an API client and sends a list of the required fields to retrieve data from server
		ApiClient client = new ApiClient();		
		String json_response = client.getAllCountries(client.buildFieldList());
		
		
		//Parses JSON response from server and creates an object for each country
		CountryDirectory cd = new CountryDirectory(json_response);
		
	
		//Establishes a random date in the current year for the c
		LocalDate d = CallCenterUtilities.generateRandomDate();
		
		//Creates a new CallCenter
		CallCenter cc = new CallCenter(d, cd);
		new GUI(cc);		
	}
}