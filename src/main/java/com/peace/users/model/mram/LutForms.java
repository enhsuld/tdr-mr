package com.peace.users.model.mram;
// Generated Nov 25, 2015 5:34:04 PM by Hibernate Tools 4.3.1.Final

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
 * LutForms generated by hbm2java
 */
@Entity
@Table(name = "LUT_FORMS")
public class LutForms implements java.io.Serializable {

	private Long id;
	private LutReporttype lutReporttype;
	private LutFiletype lutFiletype;
	private String formnamemon;
	private String formnameeng;
	private Integer fileTypeId;
	private Integer reportTypeId;
	private String form3d;
	private Long divisionid;
	private Set<SubReportRegistration> subReportRegistrations = new HashSet<SubReportRegistration>(0);
	private Set<RegWeeklyMontly> regWeeklyMontlies = new HashSet<RegWeeklyMontly>(0);
	private Set<SubApprovals> subApprovalses = new HashSet<SubApprovals>(0);
	private List<LutFormindicators> lutFormindicatorses = new ArrayList<LutFormindicators>();
	private Set<LnkFormreporttype> lnkFormreporttypes = new HashSet<LnkFormreporttype>(0);

	public LutForms() {
	}

	public LutForms(Long id, LutReporttype lutReporttype, LutFiletype lutFiletype, String formnamemon,
			String formnameeng) {
		this.id = id;
		this.lutReporttype = lutReporttype;
		this.lutFiletype = lutFiletype;
		this.formnamemon = formnamemon;
		this.formnameeng = formnameeng;
	}

	public LutForms(Long id, LutReporttype lutReporttype, LutFiletype lutFiletype, String formnamemon,
			String formnameeng, String form3d, Long divisionid, Set<SubReportRegistration> subReportRegistrations,
			Set<RegWeeklyMontly> regWeeklyMontlies, Set<SubApprovals> subApprovalses,
			List<LutFormindicators> lutFormindicatorses, Set<LnkFormreporttype> lnkFormreporttypes) {
		this.id = id;
		this.lutReporttype = lutReporttype;
		this.lutFiletype = lutFiletype;
		this.formnamemon = formnamemon;
		this.formnameeng = formnameeng;
		this.form3d = form3d;
		this.divisionid = divisionid;
		this.subReportRegistrations = subReportRegistrations;
		this.regWeeklyMontlies = regWeeklyMontlies;
		this.subApprovalses = subApprovalses;
		this.lutFormindicatorses = lutFormindicatorses;
		this.lnkFormreporttypes = lnkFormreporttypes;
	}

	@Id
	@SequenceGenerator(name="LUT_FORMS_SEQ", sequenceName="LUT_FORMS_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE ,generator="LUT_FORMS_SEQ")	
	@Column(name = "FORMID", unique = true, nullable = false, precision = 22, scale = 0)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "REPORTTYPEID", nullable = false,insertable=false,updatable=false)
	@JsonBackReference
	public LutReporttype getLutReporttype() {
		return this.lutReporttype;
	}



	public void setLutReporttype(LutReporttype lutReporttype) {
		this.lutReporttype = lutReporttype;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FILETYPEID", nullable = false,insertable=false,updatable=false)
	@JsonBackReference
	public LutFiletype getLutFiletype() {
		return this.lutFiletype;
	}

	public void setLutFiletype(LutFiletype lutFiletype) {
		this.lutFiletype = lutFiletype;
	}
	
	
		
	@Column(name = "REPORTTYPEID", nullable = false, length = 200)
	public Integer getReportTypeId() {
		return reportTypeId;
	}

	public void setReportTypeId(Integer reportTypeId) {
		this.reportTypeId = reportTypeId;
	}

	@Column(name = "FILETYPEID", nullable = false, length = 200)
	public Integer getFileTypeId() {
		return fileTypeId;
	}

	public void setFileTypeId(Integer fileTypeId) {
		this.fileTypeId = fileTypeId;
	}

	@Column(name = "FORMNAMEMON", nullable = false, length = 200)
	public String getFormnamemon() {
		return this.formnamemon;
	}

	public void setFormnamemon(String formnamemon) {
		this.formnamemon = formnamemon;
	}

	@Column(name = "FORMNAMEENG", nullable = false, length = 200)
	public String getFormnameeng() {
		return this.formnameeng;
	}

	public void setFormnameeng(String formnameeng) {
		this.formnameeng = formnameeng;
	}

	@Column(name = "FORM3D", length = 50)
	public String getForm3d() {
		return this.form3d;
	}

	public void setForm3d(String form3d) {
		this.form3d = form3d;
	}

	@Column(name = "DIVISIONID", precision = 22, scale = 0)
	public Long getDivisionid() {
		return this.divisionid;
	}

	public void setDivisionid(Long divisionid) {
		this.divisionid = divisionid;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "lutForms")
	@JsonBackReference
	public Set<SubReportRegistration> getSubReportRegistrations() {
		return this.subReportRegistrations;
	}

	public void setSubReportRegistrations(Set<SubReportRegistration> subReportRegistrations) {
		this.subReportRegistrations = subReportRegistrations;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "lutForms")
	@JsonBackReference
	public Set<RegWeeklyMontly> getRegWeeklyMontlies() {
		return this.regWeeklyMontlies;
	}

	public void setRegWeeklyMontlies(Set<RegWeeklyMontly> regWeeklyMontlies) {
		this.regWeeklyMontlies = regWeeklyMontlies;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "lutForms")
	@JsonBackReference
	public Set<SubApprovals> getSubApprovalses() {
		return this.subApprovalses;
	}

	public void setSubApprovalses(Set<SubApprovals> subApprovalses) {
		this.subApprovalses = subApprovalses;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "lutForms")
	@JsonBackReference
	public List<LutFormindicators> getLutFormindicatorses() {
		return lutFormindicatorses;
	}

	public void setLutFormindicatorses(List<LutFormindicators> lutFormindicatorses) {
		this.lutFormindicatorses = lutFormindicatorses;
	}	
	

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "lutForms")
	@JsonBackReference
	public Set<LnkFormreporttype> getLnkFormreporttypes() {
		return this.lnkFormreporttypes;
	}

	public void setLnkFormreporttypes(Set<LnkFormreporttype> lnkFormreporttypes) {
		this.lnkFormreporttypes = lnkFormreporttypes;
	}

}
