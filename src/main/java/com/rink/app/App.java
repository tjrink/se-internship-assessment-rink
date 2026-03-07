package com.rink.app;

import java.time.LocalDate;
import java.util.ArrayList;

import com.rink.client.ApiClient;
import com.rink.model.Country;
import com.rink.model.CountryDirectory;
import com.rink.service.CallCenter;
import com.rink.service.CallCenterUtilities;

public class App {

	public static void main(String[] args) {
		
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
		
		while (cc.getCurrentTime().getDayOfMonth() == d.getDayOfMonth()) {
			cc.generateCall();
			long tb = CallCenterUtilities.generateTimeBetweenCalls();
			cc.addTime(tb);
		}
		
//		Country c = cd.getRandomCountry();
		
//		System.out.println(c.getCommonName());
	}
}
