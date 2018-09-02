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
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;


import org.hibernate.annotations.Cascade;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.sun.istack.internal.NotNull;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="jid")

@Entity
@Table(name="p_menu")

@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="jid")
public class PeaceMenu implements java.io.Serializable{
	private int id=0;
	private String name=null;
	private String nameEn=null;
	private String nameRu=null;
	private String parentID=null;
	private String url=null;
	private int orderid=0;
	
	
	private int isactive=0;
	
	
	private List<UserRole> role;  
	private PeaceMenu parent;
	private List<PeaceMenu> childs = new ArrayList<PeaceMenu>();
	
	//private Set<MacroCmsUserRoleMenuRel> rroles=new HashSet<MacroCmsUserRoleMenuRel>();
	
	private String icon =null;
	private int inc=0;
	
	
	
	@Column(name = "parentid",nullable = true)
	public String getParentID() {
		return parentID;
	}
	public void setParentID(String parentID) {
		this.parentID = parentID;
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	@Column(name = "nameEn")
	public String getNameEn() {
		return nameEn;
	}
	public void setNameEn(String nameEn) {
		this.nameEn = nameEn;
	}
	
	@Column(name = "nameRu")
	public String getNameRu() {
		return nameRu;
	}
	public void setNameRu(String nameRu) {
		this.nameRu = nameRu;
	}
	

	@Transient
	public int getInc() {
		return inc;
	}


	public void setInc(int inc) {
		this.inc = inc;
	}


	@Column(name = "icon", nullable = false)
	public String getIcon() {
		return icon;
	}


	public void setIcon(String icon) {
		this.icon = icon;
	}


	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "parentid",referencedColumnName="id",nullable = false,insertable=false,updatable=false)
	public PeaceMenu getParent() {
		return parent;
	}
	public void setParent(PeaceMenu parent) {
		this.parent = parent;
	}

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "parent",cascade=CascadeType.ALL,orphanRemoval=true)	
	public List<PeaceMenu> getChilds() {
		return childs;
	}
	public void setChilds(List<PeaceMenu> childs) {
		this.childs = childs;
	}
	

	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "menu",cascade=CascadeType.ALL,orphanRemoval=true)  
	@OrderBy("id asc")
	@JsonManagedReference
	public Set<MacroCmsUserRoleMenuRel> getRroles() {
		return rroles;
	}


	public void setRroles(Set<MacroCmsUserRoleMenuRel> rroles) {
		this.rroles = rroles;
	}


	@NotNull
	@Column(name = "name",nullable = false)
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	@Column(name = "url", unique = true,nullable = false)
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	
	@Column(name = "isactive", unique = true,nullable = false)
	public int getIsactive() {
		return isactive;
	}
	public void setIsactive(int isactive) {
		this.isactive = isactive;
	}
	
	@Column(name = "orderid")
	public int getOrderid() {
		return orderid;
	}
	public void setOrderid(int orderid) {
		this.orderid = orderid;
	}
	
	@OneToMany(fetch=FetchType.EAGER) 
    @JoinTable(name="p_menu_rel",  
    joinColumns={@JoinColumn(name="menu_id", referencedColumnName="id")},  
    inverseJoinColumns={@JoinColumn(name="role_id", referencedColumnName="id")})  
	public List<UserRole> getRole() {
		return role;
	}
	public void setRole(List<UserRole> role) {
		this.role = role;
	}
	
}
*/