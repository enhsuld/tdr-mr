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
@Table(name = "LUT_INTERNALIZATION")
public class LutInternalization implements java.io.Serializable {

	private Long id;
	private String nameMn;
	private String nameEn;
	

	@Id
	@SequenceGenerator(name="LUT_INTERNALIZATION_SEQ", sequenceName="LUT_INTERNALIZATION_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE ,generator="LUT_INTERNALIZATION_SEQ")
	@Column(name = "ID", unique = true, nullable = false)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "NAME_MN")
	public String getNameMn() {
		return nameMn;
	}

	public void setNameMn(String nameMn) {
		this.nameMn = nameMn;
	}

	@Column(name = "NAME_EN")
	public String getNameEn() {
		return nameEn;
	}

	public void setNameEn(String nameEn) {
		this.nameEn = nameEn;
	}
	

}