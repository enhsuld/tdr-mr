package com.peace.users.model.mram;
// Generated Nov 17, 2015 5:32:33 PM by Hibernate Tools 4.3.1.Final

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

/**
 * SubReportRegistration generated by hbm2java
 */
@Entity
@Table(name = "SUB_REPORT_REGISTRATION")
public class SubReportRegistration implements java.io.Serializable {

	private Long reportRegId;
	private LutReporttype lutReporttype;
	private LutForms lutForms;
	private LutAppstatus lutAppstatus;
	private SubLegalpersons subLegalpersons;
	private SubReportDoc subReportDoc;
	private Integer refReportRegId;
	private String submitDateTime;
	private String procEndingDate;
	private String referenceYear;
	private Integer personId;

	public SubReportRegistration() {
	}

	public SubReportRegistration(Long reportRegId, LutReporttype lutReporttype, LutForms lutForms,
			LutAppstatus lutAppstatus, SubLegalpersons subLegalpersons, SubReportDoc subReportDoc,
			Integer refReportRegId, String submitDateTime, String procEndingDate, String referenceYear,
			Integer personId) {
		this.reportRegId = reportRegId;
		this.lutReporttype = lutReporttype;
		this.lutForms = lutForms;
		this.lutAppstatus = lutAppstatus;
		this.subLegalpersons = subLegalpersons;
		this.subReportDoc = subReportDoc;
		this.refReportRegId = refReportRegId;
		this.submitDateTime = submitDateTime;
		this.procEndingDate = procEndingDate;
		this.referenceYear = referenceYear;
		this.personId = personId;
	}

	@Id

	@Column(name = "ReportRegID", unique = true, nullable = false, precision = 22, scale = 0)
	public Long getReportRegId() {
		return this.reportRegId;
	}

	public void setReportRegId(Long reportRegId) {
		this.reportRegId = reportRegId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ReportTypeID", nullable = false)
	@JsonBackReference
	public LutReporttype getLutReporttype() {
		return this.lutReporttype;
	}

	public void setLutReporttype(LutReporttype lutReporttype) {
		this.lutReporttype = lutReporttype;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FormID", nullable = false)
	@JsonBackReference
	public LutForms getLutForms() {
		return this.lutForms;
	}

	public void setLutForms(LutForms lutForms) {
		this.lutForms = lutForms;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "StatusID", nullable = false)
	@JsonBackReference
	public LutAppstatus getLutAppstatus() {
		return this.lutAppstatus;
	}

	public void setLutAppstatus(LutAppstatus lutAppstatus) {
		this.lutAppstatus = lutAppstatus;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "LP_Reg", nullable = false)
	 @JsonBackReference
	public SubLegalpersons getSubLegalpersons() {
		return this.subLegalpersons;
	}

	public void setSubLegalpersons(SubLegalpersons subLegalpersons) {
		this.subLegalpersons = subLegalpersons;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "RepDocID", nullable = false)
	@JsonBackReference
	public SubReportDoc getSubReportDoc() {
		return this.subReportDoc;
	}

	public void setSubReportDoc(SubReportDoc subReportDoc) {
		this.subReportDoc = subReportDoc;
	}

	@Column(name = "Ref ReportRegID", nullable = false, precision = 22, scale = 0)
	public Integer getRefReportRegId() {
		return this.refReportRegId;
	}

	public void setRefReportRegId(Integer refReportRegId) {
		this.refReportRegId = refReportRegId;
	}

	@Column(name = "SubmitDateTime", nullable = false, length = 20)
	public String getSubmitDateTime() {
		return this.submitDateTime;
	}

	public void setSubmitDateTime(String submitDateTime) {
		this.submitDateTime = submitDateTime;
	}

	@Column(name = "ProcEndingDate", nullable = false, length = 10)
	public String getProcEndingDate() {
		return this.procEndingDate;
	}

	public void setProcEndingDate(String procEndingDate) {
		this.procEndingDate = procEndingDate;
	}

	@Column(name = "ReferenceYear", nullable = false, length = 4)
	public String getReferenceYear() {
		return this.referenceYear;
	}

	public void setReferenceYear(String referenceYear) {
		this.referenceYear = referenceYear;
	}

	@Column(name = "PersonID", nullable = false, precision = 22, scale = 0)
	public Integer getPersonId() {
		return this.personId;
	}

	public void setPersonId(Integer personId) {
		this.personId = personId;
	}

}
