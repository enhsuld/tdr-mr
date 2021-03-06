package com.peace.users.model.mram;
// Generated Jan 4, 2016 6:20:28 PM by Hibernate Tools 4.3.1.Final

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

/**
 * LnkPlanTransition generated by hbm2java
 */
@Entity
@Table(name = "LNK_PLAN_TRANSITION")
public class LnkPlanTransition implements java.io.Serializable {

	private Long id;
	private Long planid;
	private Long noteid;
	private Integer onoffid;
	private Integer tabid;
	private Integer decisionid;
	private Integer mdecisionid;
	private Integer offposition;
	private Integer isapproval;
	private Boolean istodotgol;
	private AnnualRegistration annualRegistration;
	private LutFormNotes lutFormNotes;
	private LutDecisions lutDecisions;
	
	public LnkPlanTransition() {
		this.istodotgol = false;
	}

	public LnkPlanTransition(Long id) {
		this.id = id;
	}

	public LnkPlanTransition(Long id, Long planid, Long noteid, Integer onoffid,
			Integer tabid) {
		this.id = id;
		this.planid = planid;
		this.noteid = noteid;
		this.onoffid = onoffid;
		this.tabid = tabid;
	}

	@Id
	@SequenceGenerator(name="LNK_PLAN_TRANSITION_SEQ", sequenceName="LNK_PLAN_TRANSITION_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE ,generator="LNK_PLAN_TRANSITION_SEQ")	
	@Column(name = "ID", unique = true, nullable = false, precision = 22, scale = 0)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonBackReference
	@JoinColumn(name = "PLANID",nullable=false,insertable=false,updatable=false)
	public AnnualRegistration getAnnualRegistration() {
		return this.annualRegistration;
	}

	public void setAnnualRegistration(AnnualRegistration annualRegistration) {
		this.annualRegistration = annualRegistration;
	}

	@Column(name = "PLANID", precision = 22, scale = 0)
	public Long getPlanid() {
		return this.planid;
	}

	public void setPlanid(Long planid) {
		this.planid = planid;
	}
		
	@Column(name = "MANAGEMENTDESICION")
	public Integer getMdecisionid() {
		return mdecisionid;
	}

	public void setMdecisionid(Integer mdecisionid) {
		this.mdecisionid = mdecisionid;
	}

	@Column(name = "OFFPOSITION")
	public Integer getOffposition() {
		return offposition;
	}

	public void setOffposition(Integer offposition) {
		this.offposition = offposition;
	}

	@Column(name = "ISAPPROVAL")
	public Integer getIsapproval() {
		return isapproval;
	}

	public void setIsapproval(Integer isapproval) {
		this.isapproval = isapproval;
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
	
	@Column(name = "NOTEID", precision = 22, scale = 0)
	public Long getNoteid() {
		return this.noteid;
	}

	public void setNoteid(Long noteid) {
		this.noteid = noteid;
	}

	@Column(name = "ONOFFID", precision = 22, scale = 0)
	public Integer getOnoffid() {
		return this.onoffid;
	}

	public void setOnoffid(Integer onoffid) {
		this.onoffid = onoffid;
	}

	@Column(name = "TABID", precision = 22, scale = 0)
	public Integer getTabid() {
		return this.tabid;
	}

	public void setTabid(Integer tabid) {
		this.tabid = tabid;
	}

	@Column(name = "DECISIONID", precision = 22, scale = 0)
	public Integer getDecisionid() {
		return decisionid;
	}

	public void setDecisionid(Integer decisionid) {
		this.decisionid = decisionid;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "DECISIONID", nullable=false,insertable=false,updatable=false)
	@JsonBackReference
	public LutDecisions getLutDecisions() {
		return lutDecisions;
	}

	public void setLutDecisions(LutDecisions lutDecisions) {
		this.lutDecisions = lutDecisions;
	}

	public Boolean getIstodotgol() {
		return istodotgol;
	}

	public void setIstodotgol(Boolean istodotgol) {
		this.istodotgol = istodotgol;
	}

	
}
