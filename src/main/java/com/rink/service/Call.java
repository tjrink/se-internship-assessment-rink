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
		
		System.out.println("Call created from " + call_country_code + " in " + call_language + " at " + this.call_time_utc);
	}

}
