package com.peace.users.model.mram;
// Generated Nov 17, 2015 5:32:33 PM by Hibernate Tools 4.3.1.Final

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

/**
 * LutCountries generated by hbm2java
 */
@Entity
@Table(name = "LUT_COUNTRIES")
public class LutCountries implements java.io.Serializable {

	private Long countryId;
	private String countryNameMon;
	private String countryNameEng;
	//private Set<SubLegalpersons> subLegalpersonses = new HashSet<SubLegalpersons>(0);



	@Id

	@Column(name = "CountryID", unique = true, nullable = false, precision = 22, scale = 0)
	public Long getCountryId() {
		return this.countryId;
	}

	public void setCountryId(Long countryId) {
		this.countryId = countryId;
	}

	@Column(name = "CountryNameMon", nullable = false, length = 70)
	public String getCountryNameMon() {
		return this.countryNameMon;
	}

	public void setCountryNameMon(String countryNameMon) {
		this.countryNameMon = countryNameMon;
	}

	@Column(name = "CountryNameEng", nullable = false, length = 70)
	public String getCountryNameEng() {
		return this.countryNameEng;
	}

	public void setCountryNameEng(String countryNameEng) {
		this.countryNameEng = countryNameEng;
	}

/*	@OneToMany(fetch = FetchType.LAZY, mappedBy = "lutCountries")
	@JsonBackReference
	public Set<SubLegalpersons> getSubLegalpersonses() {
		return this.subLegalpersonses;
	}

	public void setSubLegalpersonses(Set<SubLegalpersons> subLegalpersonses) {
		this.subLegalpersonses = subLegalpersonses;
	}*/

}