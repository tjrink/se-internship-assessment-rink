package com.rink.app;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import com.rink.client.ApiClient;
import com.rink.model.Country;
import com.rink.model.CountryDirectory;
import com.rink.service.Call;
import com.rink.service.CallCenter;
import com.rink.service.CallCenterUtilities;

public class App {

	public static void main(String[] args) {
		
//		ZonedDateTime simulationTime = ZonedDateTime.of(
//			    2026, 3, 4, 16, 59, 0, 0, ZoneOffset.UTC
//			);
//		ZonedDateTime ahora = ZonedDateTime.now(ZoneOffset.UTC);
//		String zone = "UTC+13:00";
//		
//		ZonedDateTime zdt = CallCenterUtilities.getLocalTime(ahora, zone);
		ApiClient client = new ApiClient();
		
		//Sets the basic fields to be requested when creating country objects
		ArrayList<String> fields = new ArrayList<>();
		fields.add("name");
		fields.add("cca3");
		fields.add("national_bird");
		fields.add("capital");
		fields.add("languages");
		fields.add("timezones");
		fields.add("capitalInfo");

		String json_response = client.getAllCountries(fields);
		
		CountryDirectory cd = new CountryDirectory(json_response);
		LocalDate d = CallCenterUtilities.generateRandomDate();
		CallCenter cc = new CallCenter(d, cd);
		
		
		//Runs a loop for the entire selected day
		while (cc.getCurrentTime().getDayOfMonth() == d.getDayOfMonth()) {
			//Create a new call and add it to the CallCenter's list			
			cc.generateCall();
			
			//Grab the call out of the list to be processed
			Call c = cc.getLatestCall();
			
			cc.routeCall(c);
			
			
			//Check each country to see if open
				//Get list of timezones
				//Check time in each timezone
				//If open, add country to list of possible options
			
			
			//Result
			//If list has no options - print no center available
			//If list has 1 options - print option
			//If list has multiple options, sort by distance between capital cities

			
			
			//Create a random interval between calls and add it to the call center time
			long tb = CallCenterUtilities.generateTimeBetweenCalls();
			cc.addTime(tb);
		}
	}
}
