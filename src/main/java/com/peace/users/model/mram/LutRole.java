package com.peace.users.model.mram;
// Generated Nov 17, 2015 5:32:33 PM by Hibernate Tools 4.3.1.Final

import java.math.BigDecimal;
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
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.peace.users.model.Pmenu;
import com.peace.users.model.User;

/**
 * LutRole generated by hbm2java
 */
@Entity
@Table(name = "LUT_ROLE")
public class LutRole implements java.io.Serializable {

	private Long id;
	private String roleNameMon;
	private String roleNameEng;
	private Integer access;
	private Set<LnkOffRole> lnkOffRoles = new HashSet<LnkOffRole>(0);
	private List<LnkMenuRole> lnkMenuRoles = new ArrayList<LnkMenuRole>();
	//private Set<LnkMenuRole> lnkMenuRoles = new HashSet<LnkMenuRole>(0);
	private Set<LnkRoleAuth> lnkRoleAuths = new HashSet<LnkRoleAuth>(0);
	//private Set<LutUsers> userRoles;
	public LutRole() {
	}

	public LutRole(Long id, String roleNameMon, String roleNameEng,Integer access) {
		this.id = id;
		this.access = access;
		this.roleNameMon = roleNameMon;
		this.roleNameEng = roleNameEng;
	}

	public LutRole(Long id, String roleNameMon, String roleNameEng, 
			List<LnkMenuRole> lnkMenuRoles, Set<LnkRoleAuth> lnkRoleAuths,Integer access) {
		this.id = id;
		this.access = access;
		this.roleNameMon = roleNameMon;
		this.roleNameEng = roleNameEng;
		this.lnkMenuRoles = lnkMenuRoles;
		this.lnkRoleAuths = lnkRoleAuths;
	}

	@Id
	@SequenceGenerator(name="LUT_ROLE_SEQ", sequenceName="LUT_ROLE_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE ,generator="LUT_ROLE_SEQ")
	@Column(name = "ROLEID", unique = true, nullable = false, precision = 22, scale = 0)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(name = "ACCESSID", length = 10)
	public Integer getAccess() {
		return access;
	}

	public void setAccess(Integer access) {
		this.access = access;
	}

/*	@OneToMany(cascade=CascadeType.ALL)
	@JoinTable(name="LNK_OFF_ROLE", 
		joinColumns = {@JoinColumn(name="role_id", referencedColumnName="ROLEID")},
		inverseJoinColumns = {@JoinColumn(name="user_id", referencedColumnName="OFFICERID")}
	)
	@JsonBackReference
	public Set<LutUsers> getUserRoles() {
		return userRoles;
	}

	public void setUserRoles(Set<LutUsers> userRoles) {
		this.userRoles = userRoles;
	}*/

	@Column(name = "ROLENAMEMON", nullable = false, length = 100)
	public String getRoleNameMon() {
		return this.roleNameMon;
	}

	public void setRoleNameMon(String roleNameMon) {
		this.roleNameMon = roleNameMon;
	}

	@Column(name = "ROLENAMEENG", nullable = false, length = 100)
	public String getRoleNameEng() {
		return this.roleNameEng;
	}

	public void setRoleNameEng(String roleNameEng) {
		this.roleNameEng = roleNameEng;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "lutRole")
	@JsonBackReference
	public Set<LnkOffRole> getLnkOffRoles() {
		return this.lnkOffRoles;
	}

	public void setLnkOffRoles(Set<LnkOffRole> lnkOffRoles) {
		this.lnkOffRoles = lnkOffRoles;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "lutRole")
	@JsonBackReference
	@OrderBy("ORDERID")
	public List<LnkMenuRole> getLnkMenuRoles() {
		return lnkMenuRoles;
	}

	public void setLnkMenuRoles(List<LnkMenuRole> lnkMenuRoles) {
		this.lnkMenuRoles = lnkMenuRoles;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "lutRole")
	@JsonBackReference
	public Set<LnkRoleAuth> getLnkRoleAuths() {
		return this.lnkRoleAuths;
	}

	

	public void setLnkRoleAuths(Set<LnkRoleAuth> lnkRoleAuths) {
		this.lnkRoleAuths = lnkRoleAuths;
	}

}