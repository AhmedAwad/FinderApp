package org.trendak.finderapp.DAL;
//Data Access Layer (DAL)
import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Embeddable

public class CasesID implements Serializable{
	
	@OneToOne
    @JoinColumn (name = "Case1_ID")
	private Case Case1; 

    @OneToOne
    @JoinColumn (name = "Case2_ID")
	private Case Case2;

	public Case getCase1() {
		return Case1;
	}

	public void setCase1(Case Case1) {
		this.Case1 = Case1;
	}

	public Case getCase2() { 
		return Case2;
	}

	public void setCase2(Case Case2) { 
		this.Case2 = Case2;
	} 

}
