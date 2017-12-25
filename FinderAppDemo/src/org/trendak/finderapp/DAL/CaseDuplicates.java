package org.trendak.finderapp.DAL;
//Data Access Layer (DAL)
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
 

@Entity 
@Table( name = "CaseDuplicates",
uniqueConstraints = { @UniqueConstraint( columnNames = { "case1_ID", "case2_ID" } ) } )
public class CaseDuplicates implements Serializable {
	 
	/**
	 * 
	 */ 
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId 
	CasesID CasesID; 
 
	public CasesID getCasesID() {
		return CasesID;
	}

	public void setCasesID(CasesID CasesID) {
		this.CasesID = CasesID;
	}

	  
	
	public double getConfidence() {
		return confidence;
	}
	public void setConfidence(double confidence) {
		this.confidence = confidence;
	}
 
	private double confidence; 
  

}
