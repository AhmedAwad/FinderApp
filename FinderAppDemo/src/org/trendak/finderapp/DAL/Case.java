package org.trendak.finderapp.DAL;
//Data Access Layer (DAL)
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.ForeignKey;

  
@Entity (name = "`Case`")
public class Case {
	 
	@Id 
	@Column (name = "CaseID")
	private String caseID;  
	public Image getImage() {
		return image;
	}
	public void setImage(Image image) {
		this.image = image;
	}
	private Date createdDate;
	private int lostOrFound; 
	private double age;
	private String fullName; 
	private String gender;  
	@Temporal (TemporalType.DATE)
	private Date DateOfBirth;
	private String contactPerson; 
	private String contactAddress; 
	private String contactPhone; 
	private boolean isClosed; 
	@ManyToOne (cascade = CascadeType.ALL)  
	@JoinColumn (name = "userID", updatable = true, insertable = true, nullable = false)
	private Account account; 
	
	@ElementCollection (fetch = FetchType.EAGER) 
	@OneToMany
	@JoinColumn (name = "caseID")
	private Collection<Post> allPosts = new ArrayList<>(); 
	
	@OneToOne 
	@JoinColumn (name = "image_identifier")
	private Image image;
	public String getCaseID() {
		return caseID;
	}
	public void setCaseID(String caseID) {
		this.caseID = caseID;
	}
	 
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public int getLostOrFound() {
		return lostOrFound;
	}
	public void setLostOrFound(int lostOrFound) {
		this.lostOrFound = lostOrFound;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public Date getDateOfBirth() {
		return DateOfBirth;
	}
	public void setDateOfBirth(Date dateOfBirth) {
		DateOfBirth = dateOfBirth;
	}
	public String getContactPerson() {
		return contactPerson;
	}
	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}
	public String getContactAddress() {
		return contactAddress;
	}
	public void setContactAddress(String contactAddress) {
		this.contactAddress = contactAddress;
	}
	public String getContactPhone() {
		return contactPhone;
	}
	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}
	public boolean isClosed() {
		return isClosed;
	}
	public void setClosed(boolean isClosed) {
		this.isClosed = isClosed;
	}
	
	public Account getAccount() {
		return account;
	}
	public void setAccount(Account account) {
		this.account = account;
	}
	public Collection<Post> getAllPosts() {
		return allPosts;
	}
	public void setAllPosts(Collection<Post> allPosts) {
		this.allPosts = allPosts;
	}
	public double getAge() {
		return age;
	}
	public void setAge(double age) {
		this.age = age;
	}
	
	
	

}
