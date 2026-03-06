package com.rink.service.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalTime;
import java.time.Year;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.rink.service.CallCenter;
import com.rink.service.CallCenterUtilities;

public class CallCenterTest {

	CallCenter cc;
	
	@BeforeEach
	public void setUp() {
		cc = new CallCenter(CallCenterUtilities.generateRandomDate());
	}
	
	@Test
	//Confirms the call center is within the current year
	public void currentYearTest() {
		assertEquals(cc.getCurrentTime().getYear(), Year.now().getValue());
	}
	
	@Test
	//Confirms call center addTime is functioning
	public void addTimeTest() {
		assertEquals(LocalTime.MIDNIGHT, cc.getCurrentTime().toLocalTime()); //Confirms date starts at midnight
		
		cc.addTime(25); //Add 25 minutes to the time
		
		assertEquals(LocalTime.of(0, 25), cc.getCurrentTime().toLocalTime()); //Confirms that adding time is reflected in call center time
	}
}

