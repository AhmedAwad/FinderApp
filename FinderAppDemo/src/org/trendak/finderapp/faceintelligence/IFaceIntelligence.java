package org.trendak.finderapp.faceintelligence;

public interface IFaceIntelligence {
	
	public String imageLink = "";  
	public String subsKey = ""; 
	// TODO:[Ahmed Awad] What is the nature of the returned String here?
	//Check what I have done withe post cateogires enum and where it was used
	public String runAPI(String imageLink);  
	

}
