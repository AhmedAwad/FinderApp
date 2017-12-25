package org.trendak.finderapp.textintelligence.extractors;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AgeExtractor {
	
	public static String[] keyWords = {"سنة" , "سنوات" , "سنين" , "السن"};
	public static ArrayList<String> kWords = new ArrayList<>();
	
	public static void normlaizeKeyWords() {
		
		for (int i = 0; i < keyWords.length; i++) {

			String word = keyWords[i];
			word = Normalizer.normalize(word);
			kWords.add(word);
		}

		
	}
	
	public static String normalizeText(String text) {
		
		text = text.replaceAll("[^\\p{L}\\p{Nd}]+", " "); 
		
		String finalText = ""; 
		
		finalText =  Normalizer.normalize(text);
		
		return finalText; 
	}

	
	public static Integer checkAge(String[] textArr) {
		
		Integer age = 0;
		List<String> myList = new ArrayList<String>(Arrays.asList(textArr));

		normlaizeKeyWords();
		
		for(int i = 0; i < textArr.length; i++) {
			
			String word = textArr[i]; 
			
			int index = kWords.indexOf(word); 
			
			if(index > -1) {
				
				int idx = myList.indexOf(word); //i
				
				for(int j = idx - 1; j < idx + 1; j++) {
										
					if(myList.get(j).matches("[0-9]+")) {  
						
						age = Integer.valueOf(myList.get(j));  
						break; 
					}
				}
			}
		}
		
		return age;
		
	}

	
	public static Integer getAge(String text) throws IOException {		 
		
		
		text = normalizeText(text);
		
		String[] textArr = text.split(" ");
		
		Integer age = checkAge(textArr);
		
		return age;
	}

}
