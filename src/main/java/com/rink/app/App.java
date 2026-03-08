package com.rink.app;

import com.rink.client.ApiClient;
import com.rink.model.CountryDirectory;
import com.rink.service.CallCenter;

public class App {

	public static void main(String[] args) {
		
//		Creates an API client and sends a list of the required fields to retrieve data from server
		ApiClient client = new ApiClient();		
		String json_response = client.getAllCountries(client.buildFieldList());
		
		
		//Parses JSON response from server and creates an object for each country
		CountryDirectory cd = new CountryDirectory(json_response);
		
	
		//Creates a new CallCenter
		CallCenter cc = new CallCenter(cd);
		new GUI(cc);		
	}
}