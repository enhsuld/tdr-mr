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

/**
 * SubReportDoc generated by hbm2java
 */
@Entity
@Table(name = "SUB_REPORT_DOC")
public class SubReportDoc implements java.io.Serializable {

	private Long repDocId;
	private Integer reportRegId;
	private String subDateTime;
	private Integer fileTypeId;
	private Integer formId;
	private Set<SubReportRegistration> subReportRegistrations = new HashSet<SubReportRegistration>(0);

	public SubReportDoc() {
	}

	public SubReportDoc(Long repDocId, Integer reportRegId, String subDateTime, Integer fileTypeId,
			Integer formId) {
		this.repDocId = repDocId;
		this.reportRegId = reportRegId;
		this.subDateTime = subDateTime;
		this.fileTypeId = fileTypeId;
		this.formId = formId;
	}

	public SubReportDoc(Long repDocId, Integer reportRegId, String subDateTime, Integer fileTypeId,
			Integer formId, Set<SubReportRegistration> subReportRegistrations) {
		this.repDocId = repDocId;
		this.reportRegId = reportRegId;
		this.subDateTime = subDateTime;
		this.fileTypeId = fileTypeId;
		this.formId = formId;
		this.subReportRegistrations = subReportRegistrations;
	}

	@Id

	@Column(name = "RepDocID", unique = true, nullable = false, precision = 22, scale = 0)
	public Long getRepDocId() {
		return this.repDocId;
	}

	public void setRepDocId(Long repDocId) {
		this.repDocId = repDocId;
	}

	@Column(name = "ReportRegID", nullable = false, precision = 22, scale = 0)
	public Integer getReportRegId() {
		return this.reportRegId;
	}

	public void setReportRegId(Integer reportRegId) {
		this.reportRegId = reportRegId;
	}

	@Column(name = "SubDateTime", nullable = false, length = 20)
	public String getSubDateTime() {
		return this.subDateTime;
	}

	public void setSubDateTime(String subDateTime) {
		this.subDateTime = subDateTime;
	}

	@Column(name = "FileTypeID", nullable = false, precision = 22, scale = 0)
	public Integer getFileTypeId() {
		return this.fileTypeId;
	}

	public void setFileTypeId(Integer fileTypeId) {
		this.fileTypeId = fileTypeId;
	}

	@Column(name = "FormID", nullable = false, precision = 22, scale = 0)
	public Integer getFormId() {
		return this.formId;
	}

	public void setFormId(Integer formId) {
		this.formId = formId;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "subReportDoc")
	public Set<SubReportRegistration> getSubReportRegistrations() {
		return this.subReportRegistrations;
	}

	public void setSubReportRegistrations(Set<SubReportRegistration> subReportRegistrations) {
		this.subReportRegistrations = subReportRegistrations;
	}

}
