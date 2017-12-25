package org.trendak.finderapp.DAL;
//Data Access Layer (DAL)
import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.ForeignKey;
 
@Entity 
 public class ImageMatches implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L; 
	private boolean isIdentical;
	private double confidence;
	


	@EmbeddedId 
	ImagesID imagesID; 
 
	public ImagesID getImagesID() {
		return imagesID;
	}

	public void setImagesID(ImagesID imagesID) {
		this.imagesID = imagesID;
	}

	public boolean isIdentical() { 
		return isIdentical;
	}

	public void setIdentical(boolean isIdentical) {
		this.isIdentical = isIdentical;
	}

	public double getConfidence() {
		return confidence;
	}

	public void setConfidence(double confidence) {
		this.confidence = confidence;
	}


	


	
	
}
