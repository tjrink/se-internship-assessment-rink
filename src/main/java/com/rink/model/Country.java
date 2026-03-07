package com.rink.model;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Country {

	@JsonProperty("name")
	private Name name; // The country's "common name"

	@JsonProperty("cca3")
	private String cca3; // The country's 3 character CCA code

	@JsonProperty("capital")
	private List<String> capital; // A list of the country's capital cities

	@JsonProperty("languages")
	private Map<String, String> languages; // A map of the country's languages - abbreviation as key, full name as value

	@JsonProperty("timezones")
	private List<String> timezones; // A list of the country's timezones

	@JsonProperty("capitalInfo")
	private CapitalInfo capitalLatLng; // The latitude and longitude of the capital city

	// Returns a string for the country's capital
	// Some countries have multiple capitals, so the data is stored as a list of
	// strings
	// For the purposes of this application, I am just using whichever capital is
	// first on the list
	public String getCapital() {
		return (capital != null) ? capital.get(0) : "Capital City";
	}

	// Returns the capital city's latitude
	public Double getCapitalLat() {
		return capitalLatLng.latlng.get(0);
	}

	// Returns the capital city's longitude
	public Double getCapitalLng() {
		return capitalLatLng.latlng.get(1);
	}

	// Returns the country's CCA3 code
	// CCA3 is a 3-character unique identifier for each country
	public String getCca3() {
		return cca3;
	}

	// Returns the English common name for each country
	public String getCommonName() {
		return (name != null) ? name.common : null;
	}
	
	//Returns the flag emoji for the country
	public String getFlag() {
		return this.flag;
	}

	//Returns the hash map of spoken languages
	public Map<String, String> getLanguages() {
		return this.languages;
	}

	// Returns a list of the UTC timezones that are present in the country
	public List<String> getTimeZones() {
		return timezones;
	}

	// Determines if a country uses a specified language
	public boolean usesLanguage(String language) {
		if (this.languages.containsKey(language) || this.languages.containsValue(language)) {
			return true;
		} else {
			return false;
		}
	}

	// Wrapper class that maps the "name" variable in the class to the "name ->
	// common' field in the JSON response
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class Name {
		@JsonProperty("common")
		public String common;
	}

	// Wrapper class that maps the "callingCode" variable in the class to the "idd
	// -> root" field in the JSON response
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class CallingCode {
		@JsonProperty("root")
		public String root;

	}

	// Wrapper class that creates a list of 2 doubles from "capitalInfo -> latlng"
	// Item 0 in the list is the capital city's latitude
	// Item 1 is the capital city's longitude
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class CapitalInfo {
		@JsonProperty("latlng")
		public List<Double> latlng;

	}
}