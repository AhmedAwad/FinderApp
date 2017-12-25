package org.trendak.finderapp.DAL;
//Data Access Layer (DAL)
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

 
@Entity
public class Image {

	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public int getIdentifier() {
		return Identifier;
	}
	public void setIdentifier(int identifier) {
		Identifier = identifier;
	}
	
	private String imageLink; 
	private String location; 
	@OneToOne
	@JoinColumn (name = "CaseID", insertable = true, referencedColumnName = "CaseID")
	private Case newCase;
	@Id 
	@GeneratedValue
	private int Identifier; 
	@OneToOne (targetEntity = Post.class)
	@JoinColumn (name = "PostID", insertable = true)
	private Post post;
	public Case getNewCase() {
		return newCase;
	}
	public void setNewCase(Case newCase) {
		this.newCase = newCase;
	}
	public Post getPost() {
		return post;
	}
	public void setPost(Post post) {
		this.post = post;
	}
	public String getImageLink() {
		return imageLink;
	}
	public void setImageLink(String imageLink) {
		this.imageLink = imageLink;
	}
	
	

}
