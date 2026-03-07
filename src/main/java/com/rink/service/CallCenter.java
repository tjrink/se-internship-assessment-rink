package com.rink.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.rink.model.Country;
import com.rink.model.CountryDirectory;

import java.time.ZoneOffset;

public class CallCenter {
	private CountryDirectory cd;
	private ZonedDateTime call_center_time;
	private ArrayList<Call> calls_list = new ArrayList<Call>();

	public CallCenter(LocalDate date, CountryDirectory cd) {
		// Combines the provided date and starts at midnight UTC
		this.call_center_time = date.atTime(LocalTime.MIDNIGHT).atZone(ZoneOffset.UTC);

		this.cd = cd;
	}

	// Adds a specified number of minutes to the call center's time
	public void addTime(long minutes) {
		call_center_time = this.call_center_time.plusMinutes(minutes);
	}

	// Returns the current time at the call center
	public ZonedDateTime getCurrentTime() {
		return this.call_center_time;
	}

	public Call getLatestCall() {
		return this.calls_list.get(this.calls_list.size() - 1);
	}

	// Creates a call from a random country in a random language after a random
	// interval
	public void generateCall() {
		// Selects a random country for the call from the list of valid countries
		List<String> country_code_list = cd.getValidCountryCodes();
		String selected_country_code = CallCenterUtilities.selectRandomCountry(country_code_list);

		// Selects a random language from the country
		Country selected_country = cd.getCountryByCode(selected_country_code);
		Map<String, String> spoken_languages = selected_country.getLanguages();
		List<String> language_names = new ArrayList<>(spoken_languages.values());
		String selected_language = CallCenterUtilities.selectRandomLanguage(language_names);

		// Generates a new call and adds it to the array list
		calls_list.add(new Call(selected_country_code, selected_language, this.getCurrentTime()));
	}

	// Identifies the eligible countries to take the call
	public List<String> getCountriesWithLanguage(String language) {
		// Gets the list of valid countries from the CountryDirectory
		List<String> valid_countries = cd.getValidCountryCodes();

		// Filters list to only include those countries that speak the desired langauge
		List<String> countries_with_language = valid_countries.stream()
				.filter(country -> cd.getCountryByCode(country).usesLanguage(language)).collect(Collectors.toList());

		return countries_with_language;
	}

	//Performs the full routing process for a cal
	public Country routeCall(Call call) {
		//Print out the time of the call and its information
		System.out.println(call.getCallIntro());
		
		//Creates a list of all countries that speak the specified language
		List<String> valid_countries = getCountriesWithLanguage(call.getLanguage());
		
		//Loop through all valid countries to check for open timezones
		for (String s: valid_countries) {
			ZonedDateTime local_time; //Initialization
			
			//Loop through each time zone, return the country if the time is open
			Country c = cd.getCountryByCode(s);
			for (String zone: c.getTimeZones()) {
				//Converts the call center time to the local time in the specified time zone
				if (zone.equalsIgnoreCase("UTC")) { //If the timezone is UTC-0, returns local time
					local_time = call.getCallTime();
				} else {
					local_time = CallCenterUtilities.getLocalTime(call_center_time, zone.replace("UTC", ""));
				}
				
				if (CallCenterUtilities.getTimeToOpen(local_time)) {
					System.out.println("Call being routed to " + c.getCommonName() + ". Local time is: " + local_time.toString());
					return c;
				}
			}	
		}
		
		//Handles no zones being open
		System.out.println("No countries available. Call dropped");
		return null;
	}
}