package com.rink.service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Year;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;


public class CallCenterUtilities {
	
		
	
	public static ZonedDateTime getTimeAtUTC0(ZonedDateTime localTime) {
		return localTime.withZoneSameInstant(ZoneOffset.UTC);
	}

	
	//Converts a ZonedDateTime to a different timezone
	public static ZonedDateTime getLocalTime(ZonedDateTime call_time, String timezone) {
	    try {
	    	
	    	//Converts the timezone string into a useable offset
	    	//If the timezone is not "UTC", it has the UTC removed
	    	//If it is UTC, it is converted to the UTC value of ZoneOffset
	        ZoneOffset offset;
	        if (timezone.equals("UTC")) {
	            offset = ZoneOffset.UTC;	
	        } else {
	            offset = ZoneOffset.of(timezone.replace("UTC", ""));
	        }
	        //Converts the time using the offset
	        return call_time.withZoneSameInstant(offset);
	    } catch (Exception e) { //Returns null if the offset value cannot be properly handled by withZoneSameInstant
	        return null;
	    }
	}
	
	//Determines if it is currently within working hours at the given time.
	public static boolean checkIfInWorkingHours(ZonedDateTime zdt) {
		DayOfWeek weekday = zdt.getDayOfWeek();
		int hour = zdt.getHour();
		
		if (weekday != DayOfWeek.SATURDAY && weekday != DayOfWeek.SUNDAY && hour >= 9 && hour < 17) {
			return true;
		} else {
			return false;
		}
		
	}
	
}
