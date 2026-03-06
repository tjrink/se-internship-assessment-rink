package com.rink.app;

import java.util.ArrayList;

import com.rink.client.ApiClient;

public class App {

	public static void main(String[] args) {
		ApiClient client = new ApiClient();
		
		ArrayList<String> fields = new ArrayList<>();
		fields.add("name");
		fields.add("cca3");
		fields.add("idd");
		
		System.out.println(client.getAllCountries(fields));
	}

}
