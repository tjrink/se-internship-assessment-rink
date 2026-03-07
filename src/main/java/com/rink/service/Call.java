package com.rink.service;

import java.time.ZonedDateTime;

import com.rink.model.Country;

public class Call {
	
	private final String call_country_code; //The country the call is coming from
	private final String call_language; //The language that 
	
	private final ZonedDateTime call_time_utc; //The UTC 0 time of the call
	
	
	
	public Call(String country_code, String language, ZonedDateTime call_time) {
		this.call_country_code = country_code;
		this.call_language = language;
		this.call_time_utc= call_time;
		
	}
	
	//Returns a string identifying the time of the call, along with its originating country and the UTC-0 time 
	public String getCallIntro() {
		return String.format("%02d", call_time_utc.getHour()) + ":" + String.format("%02d", call_time_utc.getMinute()) + ": Call incoming from " + this.call_country_code + ". Requested Language: " + this.call_language;
	}
	
	//Return the language the call is held in
	public String getLanguage() {
		return this.call_language;
	}
	
	public ZonedDateTime getCallTime() {
		return this.call_time_utc;
	}

}
