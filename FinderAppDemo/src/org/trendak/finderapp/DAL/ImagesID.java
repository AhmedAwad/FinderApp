package org.trendak.finderapp.DAL;
//Data Access Layer (DAL)
import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Embeddable
public class ImagesID implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
    @OneToOne
    @JoinColumn (name = "image1_ID")
	private Image image1; 

    @OneToOne
    @JoinColumn (name = "image2_ID")
	private Image image2;

	public Image getImage1() {
		return image1;
	}

	public void setImage1(Image image1) {
		this.image1 = image1;
	}

	public Image getImage2() { 
		return image2;
	}

	public void setImage2(Image image2) {
		this.image2 = image2;
	} 
	
}
