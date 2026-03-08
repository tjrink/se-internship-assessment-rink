package com.rink.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.rink.model.Country;
import com.rink.model.CountryDirectory;

import java.time.ZoneOffset;

public class CallCenter {
	private CountryDirectory cd;
	private ZonedDateTime call_center_time; //The UTC time of the call
	private ArrayList<Call> calls_list = new ArrayList<Call>();

	public CallCenter(LocalDate date, CountryDirectory cd) {
		// Combines the provided date and starts at midnight UTC
		this.call_center_time = date.atTime(LocalTime.MIDNIGHT).atZone(ZoneOffset.UTC);

		this.cd = cd;
	}
	
	public void acceptCall(String country_code, String language, String timezone, ZonedDateTime local_call_time) {
		calls_list.add(new Call(country_code, language, timezone, local_call_time));
	}
	
//	public Call(String country_code, String language, ZonedDateTime call_time) {
//		this.call_country_code = country_code;
//		this.call_language = language;
//		this.call_time_utc= call_time;

	// Adds a specified number of minutes to the call center's time
	public void addTime(long minutes) {
		call_center_time = this.call_center_time.plusMinutes(minutes);
	}
	
	public CountryDirectory getCountryDirectory() { return this.cd;}


	// Returns the current time at the call center
	public ZonedDateTime getCurrentTime() {
		return this.call_center_time;
	}
	
	//Returns the current time at the call center as a formatted string
	public String getCurrentTimeString() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy - HH:mm:ss z");
		return this.call_center_time.format(formatter);
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
		//calls_list.add(new Call(selected_country_code, selected_language, this.getCurrentTime()));
		
		System.out.println("You have messed with CallCenter.generateCall. You need to fix it.");
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
	public String routeCall(Call call) {		
				//Creates a list of all countries that speak the specified language
		List<String> valid_countries = getCountriesWithLanguage(call.getLanguage());
		
		//Checks each country to determine if they have any time zones that are open.
		//When an open country is found, returns the country in a string to display
		for (String countryCode: valid_countries) {
			String openZone = checkCountryTimezones(countryCode, call);
			if (openZone != null) {
				Country routedCountry = cd.getCountryByCode(countryCode);
				ZonedDateTime routedTime= CallCenterUtilities.getLocalTime(call.getCallTime(), openZone);
				String routedHours = String.format("%02d", routedTime.getHour());
				String routedMins = String.format("%02d", routedTime.getMinute());
				String routedTimePrintout = routedHours + ":" + routedMins;

				return "Call being routed to call center in " + routedCountry.getCapital() + ", " + routedCountry.getCommonName() + ". Local time at call center is " + routedTimePrintout;
			}
		}
//		String finalResult = getFinalResult(potentialCountries);
		
		
//		//Loop through all valid countries to check for open timezones
//		for (String s: valid_countries) {
//			ZonedDateTime local_time; //Initialization
//			
//			//Loop through each time zone, return the country if the time is open
//			Country c = cd.getCountryByCode(s);
//			for (String zone: c.getTimeZones()) {
//				//Converts the call center time to the local time in the specified time zone
//				if (zone.equalsIgnoreCase("UTC")) { //If the timezone is UTC-0, returns local time
//					local_time = call.getCallTime();
//				} else {
//					local_time = CallCenterUtilities.getLocalTime(call_center_time, zone.replace("UTC", ""));
//				}
//				
//				if (CallCenterUtilities.getTimeToOpen(local_time)) {
//					System.out.println("Call being routed to " + c.getCommonName() + ". Local time is: " + local_time.toString());
//					return c;
//				}
//			}	
//		}
//		
		return null;
	}
	
	//Loops through each timezone for a country
	//Checks to see if the timezones are open for business
	//As soon as one open timezone is found, it is returned
	public String checkCountryTimezones(String country_code, Call call) {
		List<String> country_timezones = cd.getCountryByCode(country_code).getTimeZones();
		ZonedDateTime call_time_local = call.getCallTime();
		
		
		for (String zone : country_timezones) {
			ZonedDateTime time_to_check = CallCenterUtilities.getLocalTime(call_time_local, zone);
			if (CallCenterUtilities.getTimeToOpen(time_to_check)) {
				return zone;
			}
		}
		
		return null;
	}
	
	public String getFinalResult(HashMap<String, String> set, Call call) {
		if (set.size() == 0) {
			return "No call centers open with desired language. Call disconnected.";
		} else {
			
//			Country c = getClosestCountry()
		}
		return null;

	}
}