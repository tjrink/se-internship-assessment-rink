package com.rink.app;

import java.time.LocalDate;

import com.rink.client.ApiClient;
import com.rink.model.CountryDirectory;
import com.rink.service.Call;
import com.rink.service.CallCenter;
import com.rink.service.CallCenterUtilities;

public class App {

	public static void main(String[] args) throws InterruptedException {

		//Creates an API client and sends a list of the required fields to retrieve data from server
		ApiClient client = new ApiClient();		
		String json_response = client.getAllCountries(client.buildFieldList());
		
		//Parses JSON response from server and creates an object for each country
		CountryDirectory cd = new CountryDirectory(json_response);
		
	
		//Establishes a random date in the current year for the c
		LocalDate d = CallCenterUtilities.generateRandomDate();
		
		//Creates a new CallCenter
		CallCenter cc = new CallCenter(d, cd);
		
		
		//Runs a loop for the entire selected day
		while (cc.getCurrentTime().getDayOfMonth() == d.getDayOfMonth()) {
			//Create a new call and add it to the CallCenter's list			
			cc.generateCall();
			
			//Grab the call out of the list to be processed
			Call c = cc.getLatestCall();
			
			//Performs the routing process
			cc.routeCall(c);
						
			//Create a random interval between calls and add it to the call center time
			long tb = CallCenterUtilities.generateTimeBetweenCalls();
			cc.addTime(tb);
		}
	}
}