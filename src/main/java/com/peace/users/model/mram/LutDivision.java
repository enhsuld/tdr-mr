package com.peace.users.model.mram;
// Generated Nov 25, 2015 5:34:04 PM by Hibernate Tools 4.3.1.Final

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

/**
 * LutDivision generated by hbm2java
 */
@Entity
@Table(name = "LUT_DIVISION")
public class LutDivision implements java.io.Serializable {

	private Long id;
	private SubLegalpersons subLegalpersons;
	private String divisionnamemon;
	private String divisionnameeng;
	private String lpreg;
	private Set<LnkFormreporttype> lnkFormreporttypes = new HashSet<LnkFormreporttype>(0);

	public LutDivision() {
	}

	public LutDivision(Long id, String divisionnamemon, String divisionnameeng) {
		this.id = id;
		this.divisionnamemon = divisionnamemon;
		this.divisionnameeng = divisionnameeng;
	}

	public LutDivision(Long id, SubLegalpersons subLegalpersons, String divisionnamemon,
			String divisionnameeng, Set<LnkFormreporttype> lnkFormreporttypes) {
		this.id = id;
		this.subLegalpersons = subLegalpersons;
		this.divisionnamemon = divisionnamemon;
		this.divisionnameeng = divisionnameeng;
		this.lnkFormreporttypes = lnkFormreporttypes;
	}

	@Id
	@SequenceGenerator(name="LUT_DIVISION_SEQ", sequenceName="LUT_DIVISION_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE ,generator="LUT_DIVISION_SEQ")
	@Column(name = "DIVISIONID", unique = true, nullable = false, precision = 22, scale = 0)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	

	@Column(name = "LP_REG",  length = 150)
	public String getLpreg() {
		return lpreg;
	}

	public void setLpreg(String lpreg) {
		this.lpreg = lpreg;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "LP_REG", nullable=false,insertable=false,updatable=false)
	@JsonBackReference
	public SubLegalpersons getSubLegalpersons() {
		return this.subLegalpersons;
	}

	public void setSubLegalpersons(SubLegalpersons subLegalpersons) {
		this.subLegalpersons = subLegalpersons;
	}

	@Column(name = "DIVISIONNAMEMON", nullable = false, length = 150)
	public String getDivisionnamemon() {
		return this.divisionnamemon;
	}

	public void setDivisionnamemon(String divisionnamemon) {
		this.divisionnamemon = divisionnamemon;
	}

	@Column(name = "DIVISIONNAMEENG", nullable = false, length = 150)
	public String getDivisionnameeng() {
		return this.divisionnameeng;
	}

	public void setDivisionnameeng(String divisionnameeng) {
		this.divisionnameeng = divisionnameeng;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "lutDivision")
	@JsonBackReference
	public Set<LnkFormreporttype> getLnkFormreporttypes() {
		return this.lnkFormreporttypes;
	}

	public void setLnkFormreporttypes(Set<LnkFormreporttype> lnkFormreporttypes) {
		this.lnkFormreporttypes = lnkFormreporttypes;
	}

}
