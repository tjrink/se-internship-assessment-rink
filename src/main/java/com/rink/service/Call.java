package com.rink.service;

import java.time.ZonedDateTime;

import com.rink.model.Country;

public class Call {
	
	private final String call_country_code; //The country the call is coming from
	private final String call_language; //The language that the language is in
	private final String call_timezone; //The timezone of the call
	private final ZonedDateTime call_time_local; //The local time of the call
	
	
	
	public Call(String country_code, String language, String timezone, ZonedDateTime call_time) {
		this.call_country_code = country_code;
		this.call_language = language;
		this.call_timezone = timezone;
		this.call_time_local= call_time;
	}
	
	//Returns the call's country
	public String getCountryCode() {
		return this.call_country_code;
	}
	
	//Return the language the call is held in
	public String getLanguage() {
		return this.call_language;
	}
	
	//Returns the zoned time of the call
	public ZonedDateTime getCallTime() {
		return this.call_time_local;
	}
	
	//Gets the call time in UTC0 to be represented in the call center's time
	public ZonedDateTime getCallTimeInUTC0() {
		return CallCenterUtilities.getTimeAtUTC0(this.call_time_local);
	}

}
