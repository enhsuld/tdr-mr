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
@Table(name = "LNK_WEEKLY")
public class LnkWeekly implements java.io.Serializable {

	private Long id;
	private Long ReportTypeID;
	private Long FormID;
	private Long RepStatusID;
	private String LpReg;
	private Integer LicenseNum;
	private String submissiondate;


	@Id
	@SequenceGenerator(name="REG_REQ_REPORT_SEQ", sequenceName="REG_REQ_REPORT_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE ,generator="REG_REQ_REPORT_SEQ")
	@Column(name = "ID", unique = true, nullable = false, precision = 22, scale = 0)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "ReportTypeID")
	public Long getReportTypeID() {
		return ReportTypeID;
	}

	public void setReportTypeID(Long reportTypeID) {
		ReportTypeID = reportTypeID;
	}

	@Column(name = "FormID")
	public Long getFormID() {
		return FormID;
	}

	public void setFormID(Long formID) {
		FormID = formID;
	}



	@Column(name = "RepStatusID")
	public Long getRepStatusID() {
		return RepStatusID;
	}

	public void setRepStatusID(Long repStatusID) {
		RepStatusID = repStatusID;
	}

	@Column(name = "LpReg")
	public String getLpReg() {
		return LpReg;
	}

	public void setLpReg(String lpReg) {
		LpReg = lpReg;
	}
	
	@Column(name = "LISENCENUM")
	public Integer getLicenseNum() {
		return LicenseNum;
	}

	public void setLicenseNum(Integer licenseNum) {
		LicenseNum = licenseNum;
	}
	
	@Column(name = "SUBMISSIONDATE")
	public String getSubmissiondate() {
		return submissiondate;
	}


	public void setSubmissiondate(String submissiondate) {
		this.submissiondate = submissiondate;
	}

}