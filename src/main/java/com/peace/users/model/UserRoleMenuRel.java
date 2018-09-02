/*package com.peace.users.model;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
@Entity
@Table(name="p_menu_rel")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="jid")
public class UserRoleMenuRel implements java.io.Serializable{
	private int id=0;
	private int menuID=0;
	private PeaceMenu menu ;
	private UserRole role;
	private int roleID=0;
	private int rcreate=0;
	private int rread=0;
	private int rupdate=0;
	private int rdelete=0;
	private int rexport=0;
	
	
	@Id	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	@Column(name = "menuid",nullable = false)
	public int getMenuID() {
		return menuID;
	}
	public void setMenuID(int menuID) {
		this.menuID = menuID;
	}
	
	
	@Column(name = "rcreate")
	public int getRcreate() {
		return rcreate;
	}
	public void setRcreate(int rcreate) {
		this.rcreate = rcreate;
	}
	
	@Column(name = "rread")
	public int getRread() {
		return rread;
	}
	public void setRread(int rread) {
		this.rread = rread;
	}
	
	@Column(name = "rupdate")
	public int getRupdate() {
		return rupdate;
	}
	public void setRupdate(int rupdate) {
		this.rupdate = rupdate;
	}
	
	@Column(name = "rdelete")
	public int getRdelete() {
		return rdelete;
	}
	public void setRdelete(int rdelete) {
		this.rdelete = rdelete;
	}
	
	@Column(name = "rexport")
	public int getRexport() {
		return rexport;
	}
	public void setRexport(int rexport) {
		this.rexport = rexport;
	}
	

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "menuid",referencedColumnName="id",nullable=false,insertable=false,updatable=false)
	@JsonBackReference
	public PeaceMenu getMenu() {
		return menu;
	}
	public void setMenu(PeaceMenu menu) {
		this.menu = menu;
	}

	@Column(name = "roleid",nullable = false)
	public int getRoleID() {
		return roleID;
	}
	public void setRoleID(int roleID) {
		this.roleID = roleID;
	}
	

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "roleid",referencedColumnName="id",nullable=false,insertable=false,updatable=false)
	@JsonBackReference
	public UserRole getRole() {
		return role;
	}
	public void setRole(UserRole role) {
		this.role = role;
	}
}
*/