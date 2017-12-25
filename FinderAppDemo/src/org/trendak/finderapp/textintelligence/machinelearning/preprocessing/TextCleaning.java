package org.hibernate.mohsin;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Scanner;

public class TextCleaning {
	
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

		File file = new File("StoppingWords.txt");
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
	
	public static String repairText(String post) throws IOException {

		post = post.replaceAll("[^\\p{L}\\p{Nd}]+", " "); // remove all emojis and symbols

		post = onlyArabic(post); // remove all non-Arabic chars and Numbers
		
		String rPost = ""; 

		String[] postArr = post.split(" ");
		ArrayList<String> newPost = removeStoppingWords(postArr);
		if(newPost.size() == 0) {
			
//			Posts.remove(idx); 
//			classLabels.remove(idx);  
		}  
		
		else { 

			rPost = newPost.toString();
			
			rPost = rPost.replaceAll("[^\\p{L}\\p{Nd}]+", " ");
			if(rPost.endsWith(" ")) {
				
				int newSize = rPost.length() - 1; 
				rPost = rPost.substring(0, newSize); 
				
			}
			

		}
		return rPost;

		
	} 

}
