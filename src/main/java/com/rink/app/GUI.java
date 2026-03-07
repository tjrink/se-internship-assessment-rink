package com.rink.app;

import javax.swing.*;
import javax.swing.border.TitledBorder;

import com.rink.model.Country;
import com.rink.model.CountryDirectory;
import com.rink.service.CallCenter;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.Year;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class GUI extends JFrame {

	private CallCenter cc;

	private JTextArea console;
	private JComboBox<CountryComboItem> countryDropdown;
	private JComboBox<String> languageDropdown;
	private JComboBox<String> timezoneDropdown;
	private JComboBox<String> dayDropdown;
	private JComboBox<String> monthDropdown;
	private JComboBox<String> yearDropdown;
	private JSpinner hourSpinner;
	private JSpinner minuteSpinner;
	private JSpinner secondSpinner;
	private JButton createCallButton;
	private JButton simulateDayButton;

	public GUI(CallCenter cc) {
		this.cc = cc;

		// Outlines basic window attributes
		setTitle("Call Center Simulation");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setLayout(new BorderLayout(10, 10));

		// Left side control panel
		JPanel controlPanel = new JPanel();
		controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.Y_AXIS));
		controlPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		controlPanel.setPreferredSize(new Dimension(400, 0));

		// Route Call Panel
		JPanel routePanel = new JPanel(new GridBagLayout());
		routePanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Route Call",
				TitledBorder.LEFT, TitledBorder.TOP));

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(5, 10, 5, 10);
		gbc.fill = GridBagConstraints.HORIZONTAL;

		// Initialize Components
		countryDropdown = new JComboBox<CountryComboItem>(); // Used to select country of call
		languageDropdown = new JComboBox<>(); // Used to select language of call
		timezoneDropdown = new JComboBox<>(); // Used to selec timezone of call
		dayDropdown = new JComboBox<>(new String[] {}); // Used to select date of call
		monthDropdown = new JComboBox<>(new String[] {}); // Used to select month of call
		yearDropdown = new JComboBox<>(new String[] {}); // Used to select Year of call

		// Spinners for selecting call time
		hourSpinner = new JSpinner(new SpinnerNumberModel(12, 0, 23, 1));
		minuteSpinner = new JSpinner(new SpinnerNumberModel(0, 0, 59, 1));
		secondSpinner = new JSpinner(new SpinnerNumberModel(0, 0, 59, 1));

		createCallButton = new JButton("Create Call");

		// Row for Country dropdown
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 0.3;
		routePanel.add(new JLabel("Country:"), gbc);
		gbc.gridx = 1;
		gbc.weightx = 0.7;
		routePanel.add(countryDropdown, gbc);

		// Row for Language dropdown
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.weightx = 0.3;
		routePanel.add(new JLabel("Language:"), gbc);
		gbc.gridx = 1;
		gbc.weightx = 0.7;
		routePanel.add(languageDropdown, gbc);

		// Row for selection of timezone
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.weightx = 0.3;
		routePanel.add(new JLabel("Timezone:"), gbc);
		gbc.gridx = 1;
		gbc.weightx = 0.7;
		routePanel.add(timezoneDropdown, gbc);

		// Row for Date Selection label
		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.gridwidth = 2;
		gbc.insets = new Insets(15, 10, 5, 10);
		routePanel.add(new JLabel("Select Date (DD/MM/YYYY)"), gbc);

		// Row for Date selection
		gbc.gridy = 4;
		gbc.insets = new Insets(5, 10, 5, 10);
		JPanel datePanel = new JPanel(new GridLayout(1, 3, 5, 0));
		datePanel.add(dayDropdown);
		datePanel.add(monthDropdown);
		datePanel.add(yearDropdown);
		routePanel.add(datePanel, gbc);

		// Row for Time label
		gbc.gridy = 5;
		gbc.insets = new Insets(15, 10, 5, 10);
		routePanel.add(new JLabel("Select Local Time:"), gbc);

		// Row for Time selection
		gbc.gridy = 6;
		gbc.insets = new Insets(5, 10, 5, 10);
		JPanel timePanel = new JPanel(new GridLayout(1, 3, 5, 0));
		timePanel.add(hourSpinner);
		timePanel.add(minuteSpinner);
		timePanel.add(secondSpinner);
		routePanel.add(timePanel, gbc);

		// Row for Create Call Button
		gbc.gridy = 7;
		gbc.insets = new Insets(20, 10, 10, 10);
		routePanel.add(createCallButton, gbc);

		// Button to simulate a full day
		simulateDayButton = new JButton("Simulate Day");
		simulateDayButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		simulateDayButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));

		// Pack control panel items
		controlPanel.add(routePanel);
		controlPanel.add(Box.createRigidArea(new Dimension(0, 20)));
		controlPanel.add(simulateDayButton);

		// Add control panel to frame
		add(controlPanel, BorderLayout.WEST);

		// Text area on the right side
		console = new JTextArea();
		console.setEditable(false);
		console.setBackground(new Color(30, 30, 30));
		console.setForeground(Color.CYAN);
		console.setFont(new Font("Dialog", Font.PLAIN, 14));
		console.setMargin(new Insets(10, 10, 10, 10));

		JScrollPane scrollPane = new JScrollPane(console);
		add(scrollPane, BorderLayout.CENTER);

		// Add countries to the countryDropdown
		populateCountryDropdown();
		populateDateDropdowns();

		// Changes language and timezone dropdowns when countryDropdown is changed
		countryDropdown.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				CountryComboItem cci = (CountryComboItem) countryDropdown.getSelectedItem();

				// Place the country's languages in the languageDropdown
				languageDropdown.removeAllItems();
				List<String> cci_languages = cci.getLanguages();
				cci_languages.sort(null);
				for (String l : cci_languages) {
					languageDropdown.addItem(l);
				}

				// Place the country's timezones in the timezoneDropdown
				List<String> cci_timezones = cci.getTimezones();
				timezoneDropdown.removeAllItems();
				for (String s : cci_timezones) {
					timezoneDropdown.addItem(s);
				}
				// You can add your logic here, e.g., update another component
				// or perform a database query
			}
		});

		createCallButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				CountryComboItem cci = (CountryComboItem) countryDropdown.getSelectedItem();
				String selectedLanguage = (String) languageDropdown.getSelectedItem();
				String selectedTimezone = (String) timezoneDropdown.getSelectedItem();

				int monthSelected = Integer.parseInt((String) monthDropdown.getSelectedItem());
				int daySelected = Integer.parseInt((String) dayDropdown.getSelectedItem());
				int yearSelected = Integer.parseInt((String) yearDropdown.getSelectedItem());

				int hourSelected = (int) hourSpinner.getValue();
				int minuteSelected = (int) minuteSpinner.getValue();
				int secondSelected = (int) secondSpinner.getValue();

				//Confirms that the selected date is valid and submits the call.
				//If not valid, shows an error message
				if (validateDateSelections(monthSelected, daySelected, yearSelected)) {
					LocalDateTime callTime = LocalDateTime.of(yearSelected, monthSelected, daySelected, hourSelected,
							minuteSelected, secondSelected);
					submitCall(cci, selectedLanguage, selectedTimezone, callTime);
				} else {
					showInvalidDateMessage();
				}
			}
		});

		setVisible(true);
	}

	// Creates and adds available countries to the countryDropdown
	public void populateCountryDropdown() {
		CountryDirectory cd = this.cc.getCountryDirectory(); // Access the CallCenter's CountryDirectory

		List<String> validCountries = cd.getValidCountryCodes(); // Get a list of all valid country codes
		List<CountryComboItem> countryObjects = new ArrayList<>(); // Initialize a list to hold CountryComboItem objects

		for (String code : validCountries) { // Create a new CountryComboItem for each valid country code
			countryObjects.add(new CountryComboItem(cd.getCountryByCode(code)));
		}

		countryObjects.sort(Comparator.comparing(CountryComboItem::toString)); // Alphabetize CountryComboItems by
																				// common name

		// Populate the countryDropdown with the names of each CountryComboItem
		for (CountryComboItem cci : countryObjects) {
			countryDropdown.addItem(cci);
		}

	}

	// Places dates, months and years in the appropriate dropdowns
	public void populateDateDropdowns() {

		// Places values 1 through 31 in days
		for (int i = 1; i < 32; i++) {
			dayDropdown.addItem(String.valueOf(i));
		}

		// Places values 1 through 12 in months
		for (int j = 1; j < 13; j++) {
			monthDropdown.addItem(String.valueOf(j));
		}

		// Places current year down to 2000 in years
		for (int k = Year.now().getValue(); k >= 2000; k--) {
			yearDropdown.addItem(String.valueOf(k));
		}

	}
	
	//Shows a popup error message if an invalid date is selected
	public void showInvalidDateMessage() {
		JFrame errorFrame = new JFrame();
		JOptionPane.showMessageDialog(errorFrame, "Please Select a Valid Date", "Invalid Date", JOptionPane.INFORMATION_MESSAGE);		
	}
	
	public void submitCall(CountryComboItem cci, String selectedLanguage, String selectedTimezone, LocalDateTime callTime) {
		console.append("Call Incoming From " + cci.toString() + "\nRequested Language: " + selectedLanguage
				+ "\nLocalTime: " + callTime + "\n");

	}
	
	//Determines if the selected date is valid
	public boolean validateDateSelections(int month, int date, int year) {		
		//The 31st is invalid on these months
		if (month == 2 || month == 4 || month == 6 || month == 9 || month == 11) {
			if (date == 31) {
				return false;
			}
		}
		
		//February 30 is always invalid
		if (month == 2 && date == 30) {
			return false;
		}
		
		//February 29 is invalid if it is not a leap year
		if (month == 2 && date == 29 && !Year.of(year).isLeap()) {
			return false;
		}
		
		//If none of these conditions are met, the date is valid
		return true;
	}

	// Inline class created to hold Country objects so that drop downs can access
	// their properties
	class CountryComboItem {

		private Country country;

		private CountryComboItem(Country c) {
			this.country = c;
		}

		@Override
		public String toString() {
			return this.country.getCommonName();
		}

		public List<String> getLanguages() {
			Map<String, String> langMap = this.country.getLanguages();
			List<String> langList = new ArrayList<>(langMap.values());
			return langList;
		}

		public List<String> getTimezones() {
			return this.country.getTimeZones();
		}
	}

}