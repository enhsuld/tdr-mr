/*package com.peace.users.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.peace.users.model.User;

@Entity
@Table(name="edu_organization")
public class PeaceOrganization implements  java.io.Serializable {
	private int id = 0;
	private int parentID=0;
	private PeaceOrganization parent;
	private String address="";
	private String email = "";
	private String phone = "";
	private String description = "";
	private String name = "";
	
	
	private List<User> users = new ArrayList<User>();
	private List<UserRole> roles = new ArrayList<UserRole>();
	//private List<PeaceOrganization> childorgs=new ArrayList<PeaceOrganization>();
	private List<User> userList;  

	
	
	// platform fields 
	private String DateAdded="";
	private String DateModify="";
	private int UserID=0;
	private String domainName = "";
	private String addedAdmin = "";
	private String regnumber = null;
	private String fax=null;
	
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	@Column(name = "parentid", nullable = false)
	public int getParentID() {
		return parentID;
	}

	public void setParentID(int parentID) {
		this.parentID = parentID;
	}
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "parentid",referencedColumnName="id",nullable=false,insertable=false,updatable=false)
	@JsonBackReference
	public PeaceOrganization getParent() {
		return parent;
	}

	public void setParent(PeaceOrganization parent) {
		this.parent = parent;
	}
	@Column(name = "address", nullable = false)
	
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	
	@Column(name = "email", nullable = false)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	

	@Column(name = "phone", nullable = false)
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	@Column(name = "description", nullable = false)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "organization",cascade=CascadeType.ALL,orphanRemoval=true)  
	@OrderBy("id asc")
	@JsonManagedReference
	public List<UserRole> getRoles() {
		return roles;
	}

	public void setRoles(List<UserRole> roles) {
		this.roles = roles;
	}
	
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "parent",cascade=CascadeType.ALL,orphanRemoval=true)  
	@OrderBy("id asc")
	@JsonManagedReference
	public List<PeaceOrganization> getChildorgs() {
		return childorgs;
	}
	public void setChildorgs(List<PeaceOrganization> childorgs) {
		//this.childorgs = childorgs;
		this.childorgs.clear();
	    if (childorgs != null) {
	      this.childorgs.addAll(childorgs);
	    }
	}
	
	
	@Column(name = "name", nullable = false)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	@Column(name = "dateadded", nullable = false)
	public String getDateAdded() {
		return DateAdded;
	}

	public void setDateAdded(String dateAdded) {
		DateAdded = dateAdded;
	}
	@Column(name = "modidate", nullable = false)
	public String getDateModify() {
		return DateModify;
	}

	public void setDateModify(String dateModify) {
		DateModify = dateModify;
	}
	@Column(name = "userid", nullable = false)
	public int getUserID() {
		return UserID;
	}

	public void setUserID(int userID) {
		UserID = userID;
	}
	
	@Column(name = "addedadmin", nullable = false)
	public String getAddedAdmin() {
		return addedAdmin;
	}

	public void setAddedAdmin(String addedAdmin) {
		this.addedAdmin = addedAdmin;
	}
	
	@Column(name = "regnumber")
	public String getRegnumber() {
		return regnumber;
	}

	public void setRegnumber(String regnumber) {
		this.regnumber = regnumber;
	}
	
	@Column(name = "fax")
	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}
	
	@Transient
	public String getDomainName() {
		return domainName;
	}
	public void setDomainName(String domainName) {
		this.domainName = domainName;
	}

	@OneToMany(cascade=CascadeType.ALL)  
    @JoinTable(name="user_org_rel",  
    joinColumns={@JoinColumn(name="org_id", referencedColumnName="id")},  
    inverseJoinColumns={@JoinColumn(name="user_id", referencedColumnName="id")})  
	public List<User> getUserList() {
		return userList;
	}
	public void setUserList(List<User> userList) {
		this.userList = userList;
	}

	
	@OneToMany(mappedBy = "org",cascade=CascadeType.ALL,orphanRemoval=true)  
	@OrderBy("id asc")
	
	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}
	

}
*/