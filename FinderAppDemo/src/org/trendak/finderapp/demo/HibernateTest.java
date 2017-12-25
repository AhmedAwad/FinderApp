package org.trendak.finderapp.demo;

import org.hibernate.cfg.Configuration;
import org.trendak.finderapp.DAL.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.trendak.finderapp.DAL.Case;
import org.trendak.finderapp.DAL.Image;
import org.trendak.finderapp.DAL.Post;
import org.trendak.finderapp.faceintelligence.azure.Azure;


import org.trendak.finderapp.textintelligence.PostCategories;
import org.trendak.finderapp.textintelligence.machinelearning.postclassification.BayesianClassifier;
import org.trendak.finderapp.textintelligence.machinelearning.preprocessing.TextCleaning;

import java.util.ArrayList;
import java.util.Date;

import org.hibernate.Session; 
import org.hibernate.SessionFactory;


public class HibernateTest {
	

	public static void main(String[] args) throws Exception {
		
		public static ArrayList<Record> Records = new ArrayList<>();
	public static ArrayList<String> IDs = new ArrayList<>();
	public static SessionFactory sf;
	public static Session session;

	public static Post createNewPost(Record record, boolean isCase) throws IOException {

		Post post = new Post();

		if (isCase) {

			post.setCreateDate(record.getDateCreated());
			
			if(record.getText() != null) {
				String text = record.getText();
				text = text.replaceAll("[^\\p{L}\\p{Nd}]+", " ");
				post.setRawText(text);
			}
			
			post.setType(record.getType());
			post.setSource("8488");
			

		} else {

			post.setCreateDate(record.getDateCreated());
			if(record.getText() != null) {
				String text = record.getText();
				text = text.replaceAll("[^\\p{L}\\p{Nd}]+", " ");
				post.setRawText(text);
			}
			post.setType(record.getType());
			post.setSource("8488");

			if (IDs.contains(record.getParentID())) {

				int index = IDs.indexOf(record.getParentID());
				String caseID = IDs.get(index);

				Case thisCase = (Case) session.get(Case.class, caseID);
				post.setNewCase(thisCase);
			}

		}

		return post;
	}

	public static Account createNewAccount(Record record) {

		Account account = new Account();
		account.setUsername(record.getUsername());

		return account;
	}

	public static Image createNewImage(Record record) {

		Image image = new Image();
		image.setImageLink(record.getImageLink());
		return image;

	}

	public static Case createNewCase(Record record) throws Exception {

		Case newCase = new Case();
		newCase.setCaseID(record.getId());
		IDs.add(newCase.getCaseID());

		String gender = "";
		String phoneNumber = "";
		String name = "";
		double age = 0.0;
		Image image = null;
		int lostOrFound = 0;
		boolean check = false;
		String text = "";

		ArrayList<String> classVal = new ArrayList<String>();
		classVal.add("Irrelevant");
		classVal.add("Found");
		classVal.add("Found_Suspected");
		classVal.add("Lost");
		classVal.add("Found_Dead");

		newCase.setCreatedDate(record.getDateCreated());

		if (record.getText() != null) {
			
			text = record.getText(); 

			text = TextCleaning.repairText(text);
			BayesianClassifier bayes = new BayesianClassifier();

			String res = bayes.classify(text);
			lostOrFound = classVal.indexOf(res);
 			
		}
		newCase.setLostOrFound(lostOrFound);

		if (lostOrFound != 0) {

			ArrayList<String> phoneNumbers = PhoneNumberExtractor.getNumber(text);

			if (phoneNumbers.size() > 0) {
				phoneNumber = phoneNumbers.get(0);

				newCase.setContactPhone(phoneNumber);
			}

			ArrayList<String> Names = NameExtractor.getNames(text);
			if (Names.size() > 0) {
				name = NameExtractor.getNames(text).get(0);
				newCase.setContactPerson(name);
			}

			String address = AddressExtractor.getAddress(text);
			if (address.length() > 0) {

				newCase.setContactAddress(address);
			}
		}

		Post post = createNewPost(record, true);
		String documentType = removeQuotes(record.getDocumentType());

		if (documentType.equals("photo")) {

			String imageLink = removeQuotes(record.getImageLink());

			image = createNewImage(record);

			Azure azureAPI = new Azure();
			String result = azureAPI.runAPI(imageLink);

			// Check if the link is valid
			try {
				if (result.charAt(0) == '[') {
					JSONArray array = new JSONArray(result);

					// Azure returns actual results
					if (array.length() > 0) {
						JSONObject obj = array.getJSONObject(0);

						obj = (JSONObject) obj.getJSONObject("faceAttributes");
						gender = obj.getString("gender");
						age = obj.getDouble("age");
						check = true;

					}
				}
			}
			catch (JSONException e) {
				
				check = false;  
				
			}

		}

		if (!check) {

			gender = GenderExtractor.getGender(name);
			age = AgeExtractor.getAge(text);
			newCase.setGender(gender);
			newCase.setAge(age);
		}

		Account account = createNewAccount(record);
		newCase.setAccount(account);
		account.getCase().add(newCase);
		post.setNewCase(newCase);
		newCase.getAllPosts().add(post);

		if (check) {

			image.setNewCase(newCase);
			image.setPost(post);
			post.setImage(image);
			newCase.setImage(image);
			session.save(image);

		}

		session.save(post);

		session.save(account);

		return newCase;

	}

	public static String removeQuotes(String quotedString) {

		String unQuotedString = quotedString.replace("\"", "");

		return unQuotedString;

	}

	public static void buildSession() {

		sf = new Configuration().configure().buildSessionFactory();
		session = sf.openSession();

	}

	public static void checkImageMatches(Image im, List list, int index) throws InterruptedException {

		String id1 = "", id2 = "", result = "";
		boolean isIdentical = false;
		double conf = 0.0;
		session.beginTransaction();

		for (int i = 0; i < list.size(); i++) {

			if (i == index)
				continue;
			else {
				Image other = (Image) list.get(i);

				String caseID = other.getNewCase().getCaseID();
				Case c = (Case) session.get(Case.class, caseID);

				int type = c.getLostOrFound();

				caseID = im.getNewCase().getCaseID();
				c = (Case) session.get(Case.class, caseID);

				int tmp = c.getLostOrFound();

				if (type != tmp) {

					JSONArray arr;
					JSONObject obj;

					Azure azure = new Azure();
					String link = removeQuotes(im.getImageLink());

					String res = azure.runAPI(link);

					try {
						if (res.charAt(0) == '[') {

							arr = new JSONArray(res);

							if (arr.length() > 0) {

								obj = arr.getJSONObject(0);

								id1 = obj.getString("faceId");
							}
						}
					}
					catch (JSONException e){
						continue; 
					}

					link = removeQuotes(other.getImageLink());
					res = azure.runAPI(link);

					try {
						if (res.charAt(0) == '[') {

							arr = new JSONArray(res);

							if (arr.length() > 0) {

								obj = arr.getJSONObject(0);

								id2 = obj.getString("faceId");
							}
						}
					}
					catch (Exception e) {
						continue; 
					}

					if (id1.length() > 0 && id2.length() > 0) {

						result = checkFaceMatching.runAPI(id1, id2);

						obj = new JSONObject(result);

						try { 
							if (obj.optString("isIdentical") != null) {

								isIdentical = obj.getBoolean("isIdentical");
								conf = obj.getDouble("confidence");

								ImageMatches match = new ImageMatches();
								ImagesID imID = new ImagesID();
								imID.setImage1(im);
								imID.setImage2(other);
								match.setImagesID(imID);
								match.setIdentical(isIdentical);
								match.setConfidence(conf);
								session.save(match);
								session.getTransaction().commit();

							}
						}
						catch (Exception e) {
							continue; 
						}
					}

				}

			}

			session.beginTransaction();

			Thread.sleep(10000);
		}

	}

	public static boolean checkAgeRange(double age1, double age2) {

		boolean result = false;

		if (age1 == age2)
			result = true;

		else {
			if (Math.abs(age1 - age2) == 5)
				result = true;
		}

		return result;

	}

	public static void checkCaseMatches(Case c, List list) {
		double conf = 0.0;

		for (int i = 0; i < list.size(); i++) {

			Case other = (Case) list.get(i);

			if (c.getGender().equals(other.getGender()) && c.getLostOrFound() != other.getLostOrFound()
					&& checkAgeRange(c.getAge(), other.getAge()) && c.getCaseID() != other.getCaseID()) {

				session.beginTransaction();

				CaseMatches cd = new CaseMatches();
				CasesID cID = new CasesID();
				cID.setCase1(c);
				cID.setCase2(other);
				cd.setCasesID(cID);
				cd.setConfidence(conf);
				cd.setConfirmed(false);

				session.save(cd);

				session.getTransaction().commit();

			}
		}

	}

	public static void checkCaseDuplicates(Case c, List list) {

		double conf = 0.0;
		for (int i = 0; i < list.size(); i++) {

			Case other = (Case) list.get(i);

			if (c.getGender().equals(other.getGender()) && c.getLostOrFound() == other.getLostOrFound()
					&& checkAgeRange(c.getAge(), other.getAge()) && c.getCaseID() != other.getCaseID()) {

				session.beginTransaction();

				CaseDuplicates cd = new CaseDuplicates();
				CasesID cID = new CasesID();
				cID.setCase1(c);
				cID.setCase2(other);
				cd.setCasesID(cID);
				cd.setConfidence(conf);

				session.save(cd);

				session.getTransaction().commit();
				

			}
		}
	}

	public static void main(String[] args) throws Exception {

		// read the data
		Records = GetTheData.getData("Data1.csv");
		buildSession();
		
		
		  for (int i = 0; i < Records.size(); i++) {

			session.beginTransaction();

			Record record = Records.get(i);
			String type = record.getType().replaceAll("[^\\p{L}\\p{Nd}]+", ""); 
			
			if (type.equals("2000")) {

				Case newCase = createNewCase(record);
 
				session.save(newCase);

			} else {

				Post post = createNewPost(record, false); 
				
				session.save(post);

			}

			session.getTransaction().commit();

		}
		 
		
//		Criteria cr = session.createCriteria(Image.class);
//		List allImages = cr.list(); 
//		
//		for(int i = 0; i < allImages.size(); i++) {
//			
//			Image image = (Image) allImages.get(i); 
//			
//			checkImageMatches(image, allImages, i); 
//			
//		}
		
		
		

		session.close();


	}

}
