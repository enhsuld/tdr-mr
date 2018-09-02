package com.peace.users.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name="roles")
public class Role {
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Integer id;	
	private String role;
	private String definition;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "PUserRoles", cascade=CascadeType.ALL,orphanRemoval=true)
	//private Set<PMenuRel> PMenuRels = new HashSet(0);
	private List<PMenuRel> PMenuRels=new ArrayList<PMenuRel>();
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "tbstudents")
	private Set<PUserAuth> PUserAuths = new HashSet(0);

	
	@OneToMany(cascade=CascadeType.ALL)
	@JoinTable(name="user_roles", 
		joinColumns = {@JoinColumn(name="role_id", referencedColumnName="id")},
		inverseJoinColumns = {@JoinColumn(name="user_id", referencedColumnName="id")}
	)
	@JsonManagedReference
	private Set<User> userRoles;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "role", length = 100)
	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	
	public String getDefinition() {
		return definition;
	}

	public void setDefinition(String definition) {
		this.definition = definition;
	}

	public Set<User> getUserRoles() {
		return userRoles;
	}

	public void setUserRoles(Set<User> userRoles) {
		this.userRoles = userRoles;
	}
	
	public Set<PUserAuth> getPUserAuths() {
		return this.PUserAuths;
	}

	public void setPUserAuths(Set<PUserAuth> PUserAuths) {
		this.PUserAuths = PUserAuths;
	}
	


	public List<PMenuRel> getPMenuRels() {
		return PMenuRels;
	}

	public void setPMenuRels(List<PMenuRel> pMenuRels) {
		PMenuRels = pMenuRels;
	}

	@Override
	public String toString(){
		return "{\"domain\":\"Tbuniversities\","+
				"\"id\":"+this.id+','+
				"\"value\":\""+this.id+"\","+
				"\"text\":\""+this.role+"\","+
				"\"menu\":\""+this.PMenuRels.toString()+"\","+
				"\"role\":\""+this.role+"\"}";
		
	}
	
}
