package com.rink.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import com.rink.model.Country;
import com.rink.model.CountryDirectory;

import java.time.ZoneOffset;

public class CallCenter {
	private CountryDirectory cd;
	private ZonedDateTime call_center_time;
	private HashSet<Call> calls_list = new HashSet<Call>();

	public CallCenter(LocalDate date, CountryDirectory cd) {
		//Combines the provided date and starts at midnight UTC
		this.call_center_time = date.atTime(LocalTime.MIDNIGHT).atZone(ZoneOffset.UTC);
		
		this.cd = cd;
	}

	//Adds a specified number of minutes to the call center's time
	public void addTime(long minutes) {
		call_center_time = this.call_center_time.plusMinutes(minutes);
	}
	
	//Returns the current time at the call center
	public ZonedDateTime getCurrentTime() {
		return this.call_center_time;
	}
	
	//create
	public void generateCall() {
		List<String> country_code_list = cd.getValidCountryCodes();
		String selected_country_code = CallCenterUtilities.selectRandomCountry(country_code_list);
		
		Country selected_country = cd.getCountryByCode(selected_country_code);

		Map<String, String> spoken_languages = selected_country.getLanguages();
		List<String> language_names = new ArrayList<>(spoken_languages.values());
		
		String selected_language = CallCenterUtilities.selectRandomLanguage(language_names);
		
		calls_list.add(new Call(selected_country_code, selected_language, this.getCurrentTime()));
	}
}