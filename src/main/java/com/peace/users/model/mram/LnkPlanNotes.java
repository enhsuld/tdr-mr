package com.peace.users.model.mram;
// Generated Dec 18, 2015 10:24:25 AM by Hibernate Tools 4.3.1.Final

import java.math.BigDecimal;
import java.sql.Clob;
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
 * LnkPlanNotes generated by hbm2java
 */
@Entity
@Table(name = "LNK_PLAN_NOTES")
public class LnkPlanNotes implements java.io.Serializable {

	private Long id;
	private AnnualRegistration annualRegistration;
	private LutFormNotes lutFormNotes;
	private String notes;
	private String lpreg;
	private String notedate;
	private long noteid;
	private long expid;
	private List<LnkComment> lnkComments = new ArrayList<LnkComment>();
	
	public LnkPlanNotes() {
	}

	public LnkPlanNotes(Long id) {
		this.id = id;
	}

	public LnkPlanNotes(Long id, AnnualRegistration annualRegistration, LutFormNotes lutFormNotes,
			String notes, String lpreg, List<LnkComment> lnkComments) {
		this.id = id;
		this.annualRegistration = annualRegistration;
		this.lutFormNotes = lutFormNotes;
		this.notes = notes;
		this.lpreg = lpreg;
		this.lnkComments = lnkComments;
	}

	@Id
	@SequenceGenerator(name="LNK_PLAN_NOTES_SEQ", sequenceName="LNK_PLAN_NOTES_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE ,generator="LNK_PLAN_NOTES_SEQ")
	@Column(name = "ID", unique = true, nullable = false, precision = 22, scale = 0)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	
	
	@Column(name = "NOTEDATE")
	public String getNotedate() {
		return notedate;
	}

	public void setNotedate(String notedate) {
		this.notedate = notedate;
	}

	@Column(name = "NOTEID")
	public long getNoteid() {
		return noteid;
	}

	public void setNoteid(long noteid) {
		this.noteid = noteid;
	}

	@Column(name = "PLANID")
	public long getExpid() {
		return expid;
	}

	public void setExpid(long expid) {
		this.expid = expid;
	}
	



	@ManyToOne(fetch = FetchType.LAZY)
	@JsonBackReference
	@JoinColumn(name = "PLANID", nullable=false,insertable=false,updatable=false)
	public AnnualRegistration getAnnualRegistration() {
		return this.annualRegistration;
	}

	public void setAnnualRegistration(AnnualRegistration annualRegistration) {
		this.annualRegistration = annualRegistration;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JsonBackReference
	@JoinColumn(name = "NOTEID", nullable=false,insertable=false,updatable=false)
	public LutFormNotes getLutFormNotes() {
		return this.lutFormNotes;
	}

	public void setLutFormNotes(LutFormNotes lutFormNotes) {
		this.lutFormNotes = lutFormNotes;
	}

	@Column(name = "GEOLOGALFORMATION")
	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}
	
	@Column(name = "LPREG")
	public String getLpreg() {
		return this.lpreg;
	}

	public void setLpreg(String lpreg) {
		this.lpreg = lpreg;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "lnkPlanNotes")
	@JsonBackReference
	public List<LnkComment> getLnkComments() {
		return this.lnkComments;
	}

	public void setLnkComments(List<LnkComment> lnkComments) {
		this.lnkComments = lnkComments;
	}

}