package com.rink.client.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.rink.client.ApiClient;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;

public class ApiClientTest {
	
	
	private ApiClient client;
	
	@BeforeEach
	public void setUpCountry() {
		client = new ApiClient();
	}
	
	@Test
	//Sends more than 10 fields to the request, expects too many fields message back
	public void tooManyFieldsTest() {
		List<String> field_list = new ArrayList<>(Arrays.asList("item1", "item2", "item3", "item4", "item5", "item6", "item7", "item8", "item9", "item10", "item11"));
		assertEquals("NOT SENT: Too many fields", client.getAllCountries(field_list));
	}
	
	@Test
	//Sends a request with no fields, expects no fields message back
	public void noFieldsTest() {
		List<String> fields_list = new ArrayList<>();
		assertEquals("NOT SENT: No fields provided", client.getAllCountries(fields_list));
	}
	
//	@Test
	//Sends a request with field names that are not in the database
	
}
