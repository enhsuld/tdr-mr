package com.peace.users.model.mram;
// Generated Dec 18, 2015 10:24:25 AM by Hibernate Tools 4.3.1.Final

import java.math.BigDecimal;
import java.sql.Clob;
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

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * LnkComment generated by hbm2java
 */
@Entity
@Table(name = "LNK_COMMENT")
public class LnkComment implements java.io.Serializable {

	private Long id;
	private LnkPlanNotes lnkPlanNotes;
	private LutDecisions lutDecisions;
	private String comnote;
	private Integer planid;
	private Long noteid;
	private Integer desicionid;
	private Long officerid;
	private String comdate;
	private String officername;
	private AnnualRegistration annualRegistration;
	private LutFormNotes lutFormNotes;
	//private LnkPlanAttachedFiles lnkPlanAttachedFiles;
	
	public LnkComment() {
	}

	public LnkComment(Long id) {
		this.id = id;
	}

	public LnkComment(Long id, LnkPlanNotes lnkPlanNotes, String comnote, Integer planid, Long officerid,
			String comdate) {
		this.id = id;
		this.lnkPlanNotes = lnkPlanNotes;
		this.comnote = comnote;
		this.planid = planid;
		this.officerid = officerid;
		this.comdate = comdate;
	}

	@Id
	@SequenceGenerator(name="LNK_COMMENT_SEQ", sequenceName="LNK_COMMENT_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE ,generator="LNK_COMMENT_SEQ")
	@Column(name = "ID", unique = true, nullable = false, precision = 22, scale = 0)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	
	@ManyToOne(fetch = FetchType.LAZY)
	//@JsonBackReference
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	@JoinColumn(name = "PLANID",nullable=false,insertable=false,updatable=false)
	public AnnualRegistration getAnnualRegistration() {
		return this.annualRegistration;
	}

	public void setAnnualRegistration(AnnualRegistration annualRegistration) {
		this.annualRegistration = annualRegistration;
	}
	
	@Column(name = "NOTEID")
	public Long getNoteid() {
		return noteid;
	}

	public void setNoteid(Long noteid) {
		this.noteid = noteid;
	}

	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonBackReference
	@JoinColumn(name = "NOTEID", nullable=false,insertable=false,updatable=false)
	public LutFormNotes getLutFormNotes() {
		return lutFormNotes;
	}

	public void setLutFormNotes(LutFormNotes lutFormNotes) {
		this.lutFormNotes = lutFormNotes;
	}
	
	
	
/*	@ManyToOne(fetch = FetchType.LAZY)
	@JsonBackReference
	@JoinColumn(name = "NOTEID", nullable=false,insertable=false,updatable=false)
	public LnkPlanAttachedFiles getLnkPlanAttachedFiles() {
		return lnkPlanAttachedFiles;
	}

	public void setLnkPlanAttachedFiles(LnkPlanAttachedFiles lnkPlanAttachedFiles) {
		this.lnkPlanAttachedFiles = lnkPlanAttachedFiles;
	}*/

	@ManyToOne(fetch = FetchType.LAZY)
	@JsonBackReference
	@JoinColumn(name = "NOTEID", nullable=false,insertable=false,updatable=false)
	public LnkPlanNotes getLnkPlanNotes() {
		return this.lnkPlanNotes;
	}

	public void setLnkPlanNotes(LnkPlanNotes lnkPlanNotes) {
		this.lnkPlanNotes = lnkPlanNotes;
	}

	@Column(name = "COMNOTE")
	public String getComnote() {
		return this.comnote;
	}

	public void setComnote(String comnote) {
		this.comnote = comnote;
	}

	@Column(name = "PLANID", precision = 22, scale = 0)
	public Integer getPlanid() {
		return this.planid;
	}

	public void setPlanid(Integer planid) {
		this.planid = planid;
	}

	@Column(name = "OFFICERID", precision = 22, scale = 0)
	public Long getOfficerid() {
		return this.officerid;
	}

	public void setOfficerid(Long officerid) {
		this.officerid = officerid;
	}

	
	@Column(name = "OFFICERNAME")
	public String getOfficername() {
		return officername;
	}

	public void setOfficername(String officername) {
		this.officername = officername;
	}

	@Column(name = "DESICIONID")
	public Integer getDesicionid() {
		return desicionid;
	}

	public void setDesicionid(Integer desicionid) {
		this.desicionid = desicionid;
	}

	@Column(name = "COMDATE", length = 200)
	public String getComdate() {
		return this.comdate;
	}

	public void setComdate(String comdate) {
		this.comdate = comdate;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "DESICIONID", nullable=false,insertable=false,updatable=false)
	@JsonBackReference
	public LutDecisions getLutDecisions() {
		return lutDecisions;
	}

	public void setLutDecisions(LutDecisions lutDecisions) {
		this.lutDecisions = lutDecisions;
	}

}
