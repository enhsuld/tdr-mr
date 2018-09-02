package com.peace.users.model.mram;
// Generated Feb 1, 2016 8:06:02 PM by Hibernate Tools 4.3.1.Final

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * LnkReportWeekly generated by hbm2java
 */
@Entity
@Table(name = "LNK_REPORT_WEEKLY")
public class LnkReportWeekly implements java.io.Serializable {

	private Long id;
	private RegReportReq regReportReq;
	private LutWeeks lutWeeks;
	private Long onoffid;
	private Long reqid;
	private Long weekid;

	public LnkReportWeekly() {
	}

	public LnkReportWeekly(Long id) {
		this.id = id;
	}

	public LnkReportWeekly(Long id, RegReportReq regReportReq, LutWeeks lutWeeks, Long onoffid) {
		this.id = id;
		this.regReportReq = regReportReq;
		this.lutWeeks = lutWeeks;
		this.onoffid = onoffid;
	}

	@Id

	@Column(name = "ID", unique = true, nullable = false, precision = 22, scale = 0)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	
	
	@Column(name = "REQID")
	public Long getReqid() {
		return reqid;
	}

	public void setReqid(Long reqid) {
		this.reqid = reqid;
	}

	@Column(name = "WEEKID")
	public Long getWeekid() {
		return weekid;
	}

	public void setWeekid(Long weekid) {
		this.weekid = weekid;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "REQID", nullable=false,insertable=false,updatable=false)
	public RegReportReq getRegReportReq() {
		return this.regReportReq;
	}

	public void setRegReportReq(RegReportReq regReportReq) {
		this.regReportReq = regReportReq;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "WEEKID", nullable=false,insertable=false,updatable=false)
	public LutWeeks getLutWeeks() {
		return this.lutWeeks;
	}

	public void setLutWeeks(LutWeeks lutWeeks) {
		this.lutWeeks = lutWeeks;
	}

	@Column(name = "ONOFFID", precision = 22, scale = 0)
	public Long getOnoffid() {
		return this.onoffid;
	}

	public void setOnoffid(Long onoffid) {
		this.onoffid = onoffid;
	}

}
