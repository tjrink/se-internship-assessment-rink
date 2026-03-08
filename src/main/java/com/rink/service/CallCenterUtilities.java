package com.rink.service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Year;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class CallCenterUtilities {
	
	private static final long min_time_between = 5;
	private static final long max_time_between = 30;
	
	//Generate a random date within the current year
	public static LocalDate generateRandomDate() {
		int current_year = Year.now().getValue(); //Gets the current year	
		
		//Sets the min and max values of potential dates
		long minDate = LocalDate.of(current_year, 1, 1).toEpochDay();
		long maxDate = LocalDate.of(current_year, 12, 31).toEpochDay();
		
		//Picks a random day from the list and returns it as a LocalDate
		return LocalDate.ofEpochDay(ThreadLocalRandom.current().nextLong(minDate, maxDate));		
	}
	
	public static long generateTimeBetweenCalls() {
		Random random = new Random();
		
		return ThreadLocalRandom.current().nextLong(min_time_between, max_time_between);
	}
	
	public static String selectRandomCountry(List<String> countries) {
			return countries.get(ThreadLocalRandom.current().nextInt(0, countries.size()));
	}
	
	public static String selectRandomLanguage(List<String> languages) {
		if (languages.size() == 0) {
			return null;
		}
		
		Random random = new Random();
		int idx = random.nextInt(languages.size());
		return languages.get(idx);
		
	}
	
	public static ZonedDateTime getTimeAtUTC0(ZonedDateTime localTime) {
		return localTime.withZoneSameInstant(ZoneOffset.UTC);
	}

	
	public static ZonedDateTime getLocalTime(ZonedDateTime call_time, String timezone) {
	    ZoneOffset offset;
	    if (timezone.equals("UTC")) {
	        offset = ZoneOffset.UTC;
	    } else {
	        offset = ZoneOffset.of(timezone.replace("UTC", ""));
	    }
	    return call_time.withZoneSameInstant(offset);
	}
	
	//Determines if it is currently within working hours at the given time.
	public static boolean getTimeToOpen(ZonedDateTime zdt) {
		DayOfWeek weekday = zdt.getDayOfWeek();
		int hour = zdt.getHour();
		
		if (weekday != DayOfWeek.SATURDAY && weekday != DayOfWeek.SUNDAY && hour >= 8 && hour < 17) {
			return true;
		} else {
			return false;
		}
		
	}
	
	public static double haversineDistance(double lat1, double lon1, double lat2, double lon2) {
        // Convert degrees to radians
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);

        // Convert start and end latitudes to radians for use in the formula
        double lat1Rad = Math.toRadians(lat1);
        double lat2Rad = Math.toRadians(lat2);

        // Haversine formula parts
        double a = Math.pow(Math.sin(dLat / 2), 2) + 
                   Math.cos(lat1Rad) * Math.cos(lat2Rad) * 
                   Math.pow(Math.sin(dLon / 2), 2);
        
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        // Calculate final distance
        //6371 is the radius of the Earth in km
        return 6371 * c;
	}
}
