package org.trendak.finderapp.textintelligence.extractors;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NameExtractor {
	
	
	public static ArrayList<String> checkNames(String text) throws IOException {

		ArrayList<String> allNames = new ArrayList<String>();

		File file = new File("names.txt");
		BufferedReader bufferedReader = new BufferedReader(
				new InputStreamReader(new FileInputStream(file), "UTF-8"));

		String line = ""; 
		
		List<String> myList = new ArrayList<String>(Arrays.asList(text.split(" ")));
		ArrayList<String> names = new ArrayList<>();

		while ((line = bufferedReader.readLine()) != null) {

			String thisName = line; 
			names.add(thisName);

		}

		for (int i = 0; i < myList.size(); i++) {

			String one = myList.get(i);
			if (names.contains(one) 
					&& !allNames.contains(one))
				allNames.add(one);
			if (i < myList.size() - 1) {

				String more = one + " " + myList.get(i + 1);
				if (names.contains(more) && !allNames.contains(more))
					allNames.add(more);

			}

		}

		bufferedReader.close();
		return allNames;
	}

	
	
	public static String onlyArabic(String string) {

		String strangers = "ABCDEFGHIGKMLNOPQRSTUVWXYZ1234567890٠١٢٣٤٥٦٧٨٩abcdefghijklmnopqrstuvwxyz";
		String newString = "";

		for (int i = 0; i < string.length(); i++) {
			if (strangers.indexOf(string.charAt(i)) == -1) {
				newString += string.charAt(i);
			}
		}
		return newString;

	}
	
	public static ArrayList<String> removeStoppingWords(String[] post) throws IOException {

		File file = new File("SW.txt");
		StringBuilder sb = new StringBuilder();
		
		BufferedReader bufferedReader = new BufferedReader(
				new InputStreamReader(new FileInputStream(file), "UTF-8"));
		
		String line = ""; 
		
		while ((line = bufferedReader.readLine()) != null)
        {
			sb.append(line);
        }
		

		bufferedReader.close();
		
		ArrayList<String> newPst = new ArrayList<>();

		String stopWords = sb.toString();

		// cleaning
		for (int i = 0; i < post.length; i++) {

			if (!stopWords.contains(post[i]) && post[i].length() > 2)
				newPst.add(post[i].replaceAll("(.)\\1{2,}", "$1"));
		}

		return newPst;
	}
	
	public static String repairText(String text) throws IOException {

		ArrayList<String> tmp = new ArrayList<>(); 
		String finalText = "";

		text = text.replaceAll("[^\\p{L}\\p{Nd}]+", " "); // remove all emojis and symbols

		text = onlyArabic(text); // remove all non-Arabic chars and Numbers

		String[] textArr = text.split(" ");
		ArrayList<String> newtext = removeStoppingWords(textArr); 

		for (int i = 0; i < newtext.size(); i++) {

			String word = newtext.get(i); 
			word = Normalizer.normalize(word);
			tmp.add(word); 
		}

		finalText = tmp.toString().replaceAll("[^\\p{L}\\p{Nd}]+", " "); 
		return finalText;

	}
	
	public static ArrayList<String> getNames(String text) throws IOException  {
		
		text = text.replaceAll("[^\\p{L}\\p{Nd}]+", " ");
		
		text = repairText(text); 

		ArrayList<String> Names = checkNames(text);
		return Names; 
	}

}

