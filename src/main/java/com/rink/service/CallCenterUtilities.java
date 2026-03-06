package com.rink.service;

import java.time.LocalDate;
import java.time.Year;
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

}
