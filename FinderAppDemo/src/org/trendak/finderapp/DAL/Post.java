package org.trendak.finderapp.DAL;
//Data Access Layer (DAL)
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity  
public class Post {
	
	@Id @GeneratedValue
	@Column (name = "PostID")
	private int postID; 
	public Image getImage() {
		return image;
	}
	public void setImage(Image image) {
		this.image = image;
	}
	public int getPostID() {
		return postID;
	}
	public void setPostID(int postID) {
		this.postID = postID;
	}
 
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getRawText() {
		return rawText;
	}
	public void setRawText(String rawText) {
		this.rawText = rawText;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public int getOtherInfo() {
		return otherInfo;
	}
	public void setOtherInfo(int otherInfo) {
		this.otherInfo = otherInfo;
	}
	public Case getNewCase() {
		return newCase;
	}
	public void setNewCase(Case newCase) {
		this.newCase = newCase;
	}
	private String type;
	private String source; 
	@Lob 
	private String rawText; 
	@Temporal (TemporalType.DATE)
	private Date createDate; 
	
	private int otherInfo;
	
	@ManyToOne (cascade = CascadeType.ALL)  
	@JoinColumn (name = "caseID")
	private Case newCase; 
	@OneToOne
	@JoinColumn (name = "image_ID", insertable = true)
	private Image image;

}
