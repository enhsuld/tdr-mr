package com.peace.users.model.mram;
// Generated Feb 4, 2016 6:23:07 PM by Hibernate Tools 4.3.1.Final

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * LnkLicenseConfig generated by hbm2java
 */
@Entity
@Table(name = "LNK_LICENSE_CONFIG")
public class LnkLicenseConfig implements java.io.Serializable {

	private Long id;
	private SubLicenses subLicenses;
	private Long mineralid;
	private Long depositid;
	private Set<LnkLicProduct> lnkLicProducts = new HashSet<LnkLicProduct>(0);

	public LnkLicenseConfig() {
	}

	public LnkLicenseConfig(Long id) {
		this.id = id;
	}

	public LnkLicenseConfig(Long id, SubLicenses subLicenses, Long mineralid, Long depositid,
			Set<LnkLicProduct> lnkLicProducts) {
		this.id = id;
		this.subLicenses = subLicenses;
		this.mineralid = mineralid;
		this.depositid = depositid;
		this.lnkLicProducts = lnkLicProducts;
	}

	@Id

	@Column(name = "ID", unique = true, nullable = false, precision = 22, scale = 0)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "LICNUM")
	public SubLicenses getSubLicenses() {
		return this.subLicenses;
	}

	public void setSubLicenses(SubLicenses subLicenses) {
		this.subLicenses = subLicenses;
	}

	@Column(name = "MINERALID", precision = 22, scale = 0)
	public Long getMineralid() {
		return this.mineralid;
	}

	public void setMineralid(Long mineralid) {
		this.mineralid = mineralid;
	}

	@Column(name = "DEPOSITID", precision = 22, scale = 0)
	public Long getDepositid() {
		return this.depositid;
	}

	public void setDepositid(Long depositid) {
		this.depositid = depositid;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "lnkLicenseConfig")
	public Set<LnkLicProduct> getLnkLicProducts() {
		return this.lnkLicProducts;
	}

	public void setLnkLicProducts(Set<LnkLicProduct> lnkLicProducts) {
		this.lnkLicProducts = lnkLicProducts;
	}

}
