package com.peace.users.model;

// Generated Sep 25, 2015 3:40:55 PM by Hibernate Tools 4.3.1

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * PMenuRel generated by hbm2java
 */
@Entity
@Table(name = "p_menu_rel", catalog = "university")
public class PMenuRel implements java.io.Serializable {

	private Integer id;
	private Role PUserRoles;
	private Pmenu pmenu;
	private Integer menuid;
	private Integer roleid;
	private Integer rcreate;
	private Integer rread;
	private Integer rupdate;
	private Integer rdelete;
	private Integer rexport;

	public PMenuRel() {
	}

	public PMenuRel(Role PUserRoles, Pmenu pmenu,  Integer menuid,  Integer roleid, Integer rcreate,
			Integer rread, Integer rupdate, Integer rdelete, Integer rexport) {
		this.PUserRoles = PUserRoles;
		this.pmenu = pmenu;
		this.menuid = menuid;
		this.roleid = roleid;
		this.rcreate = rcreate;
		this.rread = rread;
		this.rupdate = rupdate;
		this.rdelete = rdelete;
		this.rexport = rexport;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
	@JoinColumn(name = "role_id",nullable = false,insertable=false,updatable=false)
	public Role getPUserRoles() {
		return this.PUserRoles;
	}

	public void setPUserRoles(Role PUserRoles) {
		this.PUserRoles = PUserRoles;
	}

	@ManyToOne(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
	@JoinColumn(name = "menu_id")
	public Pmenu getPmenu() {
		return this.pmenu;
	}

	public void setPmenu(Pmenu pmenu) {
		this.pmenu = pmenu;
	}

	
	@Column(name = "menu_id",nullable = false,insertable=false,updatable=false)
	public Integer getMenuid() {
		return menuid;
	}

	public void setMenuid(Integer menuid) {
		this.menuid = menuid;
	}

	@Column(name = "role_id")
	public Integer getRoleid() {
		return roleid;
	}

	public void setRoleid(Integer roleid) {
		this.roleid = roleid;
	}

	@Column(name = "rcreate")
	public Integer getRcreate() {
		return this.rcreate;
	}

	public void setRcreate(Integer rcreate) {
		this.rcreate = rcreate;
	}

	@Column(name = "rread")
	public Integer getRread() {
		return this.rread;
	}

	public void setRread(Integer rread) {
		this.rread = rread;
	}

	@Column(name = "rupdate")
	public Integer getRupdate() {
		return this.rupdate;
	}

	public void setRupdate(Integer rupdate) {
		this.rupdate = rupdate;
	}

	@Column(name = "rdelete")
	public Integer getRdelete() {
		return this.rdelete;
	}

	public void setRdelete(Integer rdelete) {
		this.rdelete = rdelete;
	}

	@Column(name = "rexport")
	public Integer getRexport() {
		return this.rexport;
	}

	public void setRexport(Integer rexport) {
		this.rexport = rexport;
	}

	@Override
	public String toString(){
		return "{\"domain\":\"PMenuRel\","+
				"\"id\":"+this.id+','+
				"\"roleid\":\""+this.roleid+"\","+
				"\"menuid\":\""+this.menuid+"\","+
				"\"rcreate\":\""+this.rcreate+"\","+
				"\"rread\":\""+this.rread+"\","+
				"\"rupdate\":\""+this.rupdate+"\","+
				"\"rdelete\":\""+this.rdelete+"\","+
				"\"rexport\":\""+this.rexport+"\"}";
		
	}
}
