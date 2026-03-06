# se-internship-assessment-rink
This repo contains my assessment for the SE internship.


# Assumptions


# Test Automation Task
# Scenario 1
- For this scenario, the user story states the the map builder would like to ensure that their maps are correct.
- I am assuming that the map builder's main concern is ensuring that their existing maps match current data.
- To match this assumption, I have written the test to match against the number of countries in the dataset as I began the project (250).
**TEST RESULT**
Pass


# Scenario 2
- To address this story, I wrote test CountryDirectoryTest.southAfricanSignLanguageTest.
- This test queries the API to create a new CountryDirectory and then pulls out the South African object.
- The object is then tested to determine if SASL is included as an official langauge.
- While SASL was added as an official language of South Africa in 2023, it does not appear in the restcountries dataset as of the time of testing.
**TEST RESULT **
Fail


# Scenario 3
- For this scenario,I put logic in the ApiClient that prevents the API request from being sent if there are too many (or too few) fields.
- To test this story, I wrote ApiClientTest.tooManyFieldsTest.
- This test creates a list of 11 fields to pass to the client and checks to ensure that a message is returned stating that too many fields have been sent
**TEST RESULT**
Pass
