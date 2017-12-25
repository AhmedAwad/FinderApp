package org.trendak.finderapp;

import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

public class GetTheData {

	public static ArrayList<Record> records = new ArrayList<>();
	private static final String[] FILE_HEADER_MAPPING = { "id", "createdDate", "text", "class", "username", "parent_ID",
			"document_type", "post_link", "image_Link", "type" };

	// Record data
	private static final String recordID = "id";
	private static final String createdDate = "createdDate";
	private static final String Text = "text";
	private static final String Class = "class";
	private static final String username = "username";
	private static final String parentID = "parent_ID";
	private static final String documentType = "document_type";
	private static final String postLink = "post_link";
	private static final String imageLink = "image_Link";
	private static final String type = "type";

	public static ArrayList<Record> getData(String fileName) throws ParseException {

		CSVFormat csvFileFormat = CSVFormat.DEFAULT.withHeader(FILE_HEADER_MAPPING);

		Scanner scan = new Scanner(System.in);

		FileReader fileReader = null;

		CSVParser csvFileParser = null;

		try {

			fileReader = new FileReader(fileName);
			csvFileParser = new CSVParser(fileReader, csvFileFormat);

			List csvRecords = csvFileParser.getRecords();

			for (int i = 1; i < csvRecords.size(); i++) {

				CSVRecord row = (CSVRecord) csvRecords.get(i);
		
				Record record = new Record(); 
				
				record.setId(row.get(recordID));
				
				DateFormat date = new SimpleDateFormat("MM/dd/yyyy hh:mm");
				String tmp = row.get(createdDate); 
				
				if(!tmp.isEmpty()) {
					Date d = date.parse(tmp);  
					record.setDateCreated(d); 
				}
				
				record.setClassLabel(row.get(Class));
				record.setText(row.get(Text));
				record.setUsername(row.get(username));
				record.setParentID(row.get(parentID));
				record.setDocumentType(row.get(documentType)); 
				record.setPostLink(row.get(postLink));
				record.setImageLink(row.get(imageLink));
                record.setType(row.get(type)); 
			
                records.add(record); 

			}

		} catch (Exception e) {

			System.out.println("Error in CsvFileReader !!!");

			e.printStackTrace();

		} finally {

			try {

				fileReader.close();

				csvFileParser.close();
				
				scan.close();

			} catch (IOException e) {

				System.out.println("Error while closing fileReader/csvFileParser !!!");

				e.printStackTrace();

			}

		}

		return records;

	}
}
