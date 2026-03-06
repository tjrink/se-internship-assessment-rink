package com.rink.client;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;

public class ApiClient {

	//Calls the restCountries API /all route
	//Returns JSON for all countries
	public String getAllCountries(ArrayList<String> fields) {

		//Returns a null value if the 
		if (fields.size() > 10) {
			return null;
		}

		//Creates a list of all fields provided to be appended to the end of the API request string
		//Format requires names in order with a comma between and no spaces
		String field_list = String.join(",", fields);
		String request_string = "https://restcountries.com/v3.1/all?fields=" + field_list;

		//Creates a client to send the API request and receive the result
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder().uri(URI.create(request_string)).GET().build();

		try {
			// Sends the request through the client
			HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

			// Returns the body of the response as a string
			return response.body();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
