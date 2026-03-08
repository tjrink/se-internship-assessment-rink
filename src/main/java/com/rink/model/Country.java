package com.rink.model;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Country {

	@JsonProperty("name")
	private Name name; // The country's "common name"

	@JsonProperty("cca3")
	private String cca3; // The country's 3 character CCA code

	@JsonProperty("languages")
	private Map<String, String> languages; // A map of the country's languages - abbreviation as key, full name as value

	@JsonProperty("timezones")
	private List<String> timezones; // A list of the country's timezones

	// Returns the country's CCA3 code
	// CCA3 is a 3-character unique identifier for each country
	public String getCca3() {
		return cca3;
	}

	// Returns the English common name for each country
	public String getCommonName() {
		return (name != null) ? name.common : null;
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
}