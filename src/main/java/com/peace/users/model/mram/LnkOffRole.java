package com.peace.users.model.mram;
// Generated Nov 18, 2015 5:50:18 PM by Hibernate Tools 4.3.1.Final

import java.math.BigDecimal;

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

/**
 * LnkOffRole generated by hbm2java
 */
@Entity
@Table(name = "LNK_OFF_ROLE")
public class LnkOffRole implements java.io.Serializable {

	private Long id;
	private LutUsers lutUsers;
	private LutRole lutRole;
	private Long roleid;
	private Long userid;
	public LnkOffRole() {
	}

	public LnkOffRole(Long id) {
		this.id = id;
	}

	public LnkOffRole(Long id, LutUsers lutUsers, LutRole lutRole) {
		this.id = id;
		this.lutUsers = lutUsers;
		this.lutRole = lutRole;
	}

	@Id
	@SequenceGenerator(name="LNK_OFF_ROLE_SEQ", sequenceName="LNK_OFF_ROLE_SEQ")
	@GeneratedValue(strategy = GenerationType.SEQUENCE ,generator="LNK_OFF_ROLE_SEQ")
	@Column(name = "ID", unique = true, nullable = false, precision = 22, scale = 0)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	
	@Column(name = "ROLE_ID", nullable = false, length = 100)
	public Long getRoleid() {
		return roleid;
	}

	public void setRoleid(Long roleid) {
		this.roleid = roleid;
	}

	@Column(name = "USER_ID", nullable = false, length = 100)
	public Long getUserid() {
		return userid;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_ID",nullable=false,insertable=false,updatable=false)
	public LutUsers getLutUsers() {
		return this.lutUsers;
	}

	public void setLutUsers(LutUsers lutUsers) {
		this.lutUsers = lutUsers;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ROLE_ID",nullable=false,insertable=false,updatable=false)
	public LutRole getLutRole() {
		return this.lutRole;
	}

	public void setLutRole(LutRole lutRole) {
		this.lutRole = lutRole;
	}

}
