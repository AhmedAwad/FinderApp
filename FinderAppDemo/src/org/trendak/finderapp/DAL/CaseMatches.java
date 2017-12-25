package org.trendak.finderapp.DAL;
//Data Access Layer (DAL)
import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;


@Entity 

public class CaseMatches implements Serializable {
	
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
	public boolean isConfirmed() {
		return confirmed;
	}
	public void setConfirmed(boolean confirmed) {
		this.confirmed = confirmed;
	}

	private double confidence; 
	private boolean confirmed; 

}
