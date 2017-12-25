package org.trendak.finderapp.textintelligence.extractors;

import java.util.ArrayList;

public class PhoneNumberExtractor {
	
	public static ArrayList<String> phoneNumbers = new ArrayList<>(); 

	public static void getPhoneNumbers(String[] text) {


		for (int i = 0; i < text.length; i++) {
			if (text[i].matches("[0-9]+")) {
				if (text[i].length() == 11)
					phoneNumbers.add(text[i]);

			}
			if (text[i].matches("[٠-٩]+")) {
				if (text[i].length() == 11)
					phoneNumbers.add(text[i]);

			}
		}

	}

	public static ArrayList<String> getNumber(String text) {

		text = text.replaceAll("[^\\p{L}\\p{Nd}]+", " ");
		String[] textArr = text.split(" ");
	    getPhoneNumbers(textArr); 
		return phoneNumbers;  

	}

}
