package org.trendak.finderapp.DAL;
//Data Access Layer (DAL)
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.ForeignKey;

@Entity  
@Table (name = "ACCOUNT")
public class Account {
	
	public Account() {
		
		
	}
	
	public Account(int userID, String userName, String password, String facebookAccount) {
		// TODO Auto-generated constructor stub
		
		this.FacebookAccount = facebookAccount; 
		this.username = userName; 
		this.password = password; 
		this.userID = userID;
	}

	private String username;
	@Id @GeneratedValue
	private int userID; 
	private String password;
	@ForeignKey(name = "FacebookAccount")
	private String FacebookAccount; 
	@ElementCollection (fetch = FetchType.EAGER) 
	@OneToMany
	@JoinColumn (name = "userID")
	
	private Collection<Case> listOfCases = new ArrayList<>();   
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getUserID() {
		return userID ;
	}

	public void setUserID(int userID) {
		this.userID = userID; 
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFacebookAccount() {
		return FacebookAccount;
	}

	public java.util.Collection<Case> getCase() {
	      if (listOfCases == null) 
	    	  listOfCases = new java.util.HashSet<Case>();
	      return listOfCases;
	   }
	
	 public java.util.Iterator getIteratorCase() {
	      if (listOfCases == null)
	    	  listOfCases = new java.util.HashSet<Case>();
	      return listOfCases.iterator();
	   }
	
	 public void setCase(java.util.Collection<Case> newCase) {
	      removeAllCases();
	      for (java.util.Iterator iter = newCase.iterator(); iter.hasNext();)
	         addCase((Case)iter.next());
	   }
	 
	 public void removeAllCases() {
	      if (listOfCases != null)
	      {
	         Case oldCase;
	         for (java.util.Iterator iter = getIteratorCase(); iter.hasNext();)
	         {
	            oldCase = (Case)iter.next();
	            iter.remove();
	            oldCase.setAccount((Account)null);
	         }
	      }
	   }
	 
	public void setFacebookAccount(String facebookAccount) {
		FacebookAccount = facebookAccount;
	}
	public void addCase(Case newCase) {
		 if (newCase == null)
	         return;
	      if (this.listOfCases == null)
	         this.listOfCases = new java.util.HashSet<Case>();
	      if (!this.listOfCases.contains(newCase))
	      {
	         this.listOfCases.add(newCase);
	         newCase.setAccount(this);       
	      }
	}

	
	
}
