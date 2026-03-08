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
	
	//Returns a string identifying the time of the call, along with its originating country and the UTC-0 time 
	public String getCallIntro() {
		//Breaks out the hours, mins, seconds for printing to console
		ZonedDateTime utc_time = getCallTimeInUTC0();
		String utc_hours = String.format("%02d", utc_time.getHour());
		String utc_mins = String.format("%02d", utc_time.getMinute());
		String utc_seconds = String.format("%02d", utc_time.getSecond());
		
		String local_hours = String.format("%02d", call_time_local.getHour());
		String local_mins = String.format("%02d", call_time_local.getMinute());
		String local_seconds = String.format("%02d", call_time_local.getSecond());
		

		return utc_hours + ":" + utc_mins + ":" + utc_seconds + ": Call From " + this.call_country_code + ". Requested Language: " + this.call_language + ". Local Time: " + local_hours + ":" + local_mins + ":" + local_seconds;
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
