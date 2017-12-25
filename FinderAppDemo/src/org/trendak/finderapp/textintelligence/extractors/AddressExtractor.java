package org.trendak.finderapp.textintelligence.extractors;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AddressExtractor {

	public static String keyWords[] = {"قرية", "شارع", "مدينة", "مركز", "كوبري", "حي", "مول" , "محافظة" , "نفق" , "حارة", "مترو"};
	public static ArrayList<String> kWords = new ArrayList<>();

	public static void normalizeKeywords() {
 
		for (int i = 0; i < keyWords.length; i++) { 

			String word = keyWords[i];
			word = Normalizer.normalize(word);
			kWords.add(word);
		}

	}

	public static String NgramsForAddress(String text) {

		String address = "";

		normalizeKeywords();

		List<String> myList = new ArrayList<String>(Arrays.asList(text.split(" ")));

		String textArr[] = text.split(" ");
		for (int i = 0; i < textArr.length; i++) {

			String word = textArr[i];
			int index = kWords.indexOf(word);

			if (index > -1) {

				int idx = myList.indexOf(word);
				address = textArr[idx - 1] + " " + textArr[idx] + " " + textArr[idx + 1];

			}
		}

		return address;

	}

	public static ArrayList<String> checkAddress(String text) throws IOException {

		ArrayList<String> allPlaces = new ArrayList<String>();

		File file = new File("places.txt");

		BufferedReader bufferedReader = new BufferedReader(
				new InputStreamReader(new FileInputStream(file), "UTF-8"));

		String line = ""; 

		while ((line = bufferedReader.readLine()) != null) {

			String thisAddress = line; 
			
			
			thisAddress = Normalizer.normalize(thisAddress);
			
			if (text.contains(thisAddress))
				allPlaces.add(thisAddress);

		}

		bufferedReader.close();
		return allPlaces;
	}

	public static String onlyArabic(String string) {

		String strangers = "ABCDEFGHIGKMLNOPQRSTUVWXYZ1234567890Ù Ù¡Ù¢Ù£Ù¤Ù¥Ù¦Ù§Ù¨Ù©abcdefghijklmnopqrstuvwxyz";
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

	public static String getAddress(String text) throws IOException {

		text = text.replaceAll("[^\\p{L}\\p{Nd}]+", " ");

		text = repairText(text);
		String Place = "";

		ArrayList<String> places = checkAddress(text);
		Place = places.toString().replaceAll("[^\\p{L}\\p{Nd}]+", " ");

		Place = Place + NgramsForAddress(text); 
		

		return Place;  
	}

}
