package com.rink.service;

import java.time.ZonedDateTime;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import com.rink.model.Country;
import com.rink.model.CountryDirectory;

public class CallCenter {
	private CountryDirectory cd;
	private ArrayList<Call> calls_list = new ArrayList<Call>();

	public CallCenter(CountryDirectory cd) {
		this.cd = cd;
	}
	
	public void acceptCall(String country_code, String language, String timezone, ZonedDateTime local_call_time) {
		calls_list.add(new Call(country_code, language, timezone, local_call_time));
	}
	
	public CountryDirectory getCountryDirectory() { return this.cd;}
	
	public Call getLatestCall() {
		return this.calls_list.get(this.calls_list.size() - 1);
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
	public HashSet<Country> routeCall(Call call) {		
		//Creates a list of all countries that speak the specified language
		List<String> valid_countries = getCountriesWithLanguage(call.getLanguage());

		//Initialize a set for countries that have at least one open call center
		HashSet<Country> openCountries = new HashSet<>();
		
		//Checks each country to determine if they have any time zones that are open.
		//If a country has an "open" timezone, it is placed in the set
		for (String countryCode: valid_countries) {
			String openZone = checkCountryTimezones(countryCode, call);
			if (openZone != null) {
				openCountries.add(cd.getCountryByCode(countryCode));
			}
		}
		
		return openCountries;
	}
	
	//Loops through each timezone for a country
	//Checks to see if the timezones are open for business
	//As soon as one open timezone is found, it is returned
	public String checkCountryTimezones(String country_code, Call call) {
		List<String> country_timezones = cd.getCountryByCode(country_code).getTimeZones();
		
		for (String zone : country_timezones) {
			ZonedDateTime time_to_check = CallCenterUtilities.getLocalTime(call.getCallTimeInUTC0(), zone);
			if (time_to_check != null && CallCenterUtilities.checkIfInWorkingHours(time_to_check)) {
			    return zone;
			}
		}
		
		return null;
	}
	
}