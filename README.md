# se-internship-assessment-rink
This repo contains my assessment for the SE internship.

# Program Summary
This program allows users to operate a call routing center.
Upon startup, the program will connect to the RESTCountries API and create a world map using the resulting data.
The user can use a GUI to select a country, language, timezone, date and time.
The program will then provide a list of all countries that speak the requested langauges and have at least one timezone that is currently within business hours.

# Assumptions
- Business hours are defined in the brief as 09:00 - 17:00 Monday through Friday.
- Bank or public holidays were not taken into account.
- Countries are only considered "valid" if they contain at least one timezone *and* one spoken language

# Instructions
1. Clone the repository to your local environment
2. Run the program
3. When the GUI starts, select a country
4. When a country has been selected, the Language and Timezone sections will populate with that country's information
5. Select a language and timezone
6. Enter a date and time
7. Click on "Create Call"
8. The program will respond with a list of all countries that are available to take the call

# Requirements
- The program requires an Internet connection in order to perform the API call on startup.
- The Java JDK must be installed on your machine

# Dependencies
The following packages are included in the Maven dependencies:
- jUnit - Used for unit testing
- Jackson - Used to parse the JSON response from the API into Country objects