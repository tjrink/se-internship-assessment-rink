package com.rink.client;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class ApiClient {

	private int max_fields = 10; // Maximum number of fields that can be requested in a single call

	// Calls the restCountries API /all route
	// Returns JSON for all countries
	public String getAllCountries(List<String> fields) {
		String request_string = "";
		// Returns a null value if the field count is higher than allowed or if no
		// fields are specified
		// The /all path must include a field set with 1 to 10 values
		if (fields.size() > max_fields) {
			return "NOT SENT: Too many fields";
		} else if (fields.size() == 0) {
			return "NOT SENT: No fields provided";
		}
		else {
			// Creates a list of all fields provided to be appended to the end of the API
			// request string
			// Format requires names in order with a comma between and no spaces
			String field_list = String.join(",", fields);
			request_string = "https://restcountries.com/v3.1/all?fields=" + field_list;
		}

		// Creates a client to send the API request and receive the result
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder().uri(URI.create(request_string)).GET().build();

		try {
			// Sends the request through the client
			HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

			if (response.statusCode() == 200) { // Returns the body of the response as a string if the status code is
												// 200
				return response.body();
			} else {
				return "BAD STATUS CODE";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "ERROR: " + e.getMessage();
		}
	}
}
