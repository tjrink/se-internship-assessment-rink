package com.rink.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;

public class CallCenter {
	private ZonedDateTime callCenterTime;

	public CallCenter(LocalDate date) {
		//Combines the provided date and starts at midnight UTC
		this.callCenterTime = date.atTime(LocalTime.MIDNIGHT).atZone(ZoneOffset.UTC);
	}

	//Adds a specified number of minutes to the call center's time
	public void addTime(long minutes) {
		callCenterTime = this.callCenterTime.plusMinutes(minutes);
	}
	
	//Returns the current time at the call center
	public ZonedDateTime getCurrentTime() {
		return this.callCenterTime;
	}
}