package com.peace.users.model.mram;
// Generated Dec 18, 2015 10:24:25 AM by Hibernate Tools 4.3.1.Final

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
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

/**
 * AnnualRegistration generated by hbm2java
 */
@Entity
@Table(name = "ANNUAL_REGISTRATION")
public class AnnualRegistration implements java.io.Serializable {

	private Long id;
	private String licensenum;
	private String lpReg;
	private String lpName;
	private Long reporttype;
	private Integer lictype;
	private String reportyear;
	private String xcomment;
	private String xffile;
	private String xlfile;
	private Long repstatusid;
	private Long repstepid;
	private String submissiondate;
	private String licenseXB;
	private Long personid;
	private Long officerid;
	private Long reqid;
	private Long minid;
	private Long divisionid;
	private Long depositid;
	private Integer xtype;
	private Integer groupid;
	private Integer reject;
	private Long rejectstep;
	private Integer reasonid;
	private String approveddate;
	private String lastmodified;
	private String cduration;
	private String gduration;
	private String sourceE;
	private String sourceW;
	
	private String fiduration;
	private String seduration;
	private String thduration;
	private String foduration;
	private String faduration;
	
	private String fitime;
	private String setime;
	private String thtime;
	private String fotime;
	private String fatime;
	private boolean ishidden;
	
	
	private String cfiduration;
	private String cseduration;
	private String cthduration;
	private String cfoduration;
	private String cfaduration;
	
	
	private Boolean istodotgol;
	private LutUsers lutUsers;
	private SubLegalpersons subLegalpersons;
	private RegReportReq regReportReq;
	private List<LnkPlanNotes> lnkPlanNoteses = new ArrayList<LnkPlanNotes>();
	private List<LnkPlanAttachedFiles> lnkPlanAttachedFileses = new ArrayList<LnkPlanAttachedFiles>();
	private List<LnkCommentMain> lnkCommentMains = new ArrayList<LnkCommentMain>();
	

	private List<LnkPlanTab> lnkPlanTabs = new ArrayList<LnkPlanTab>(0);
	private List<LnkPlanTransition> lnkPlanTransitions = new ArrayList<LnkPlanTransition>();
	private List<LnkComment> lnkComments = new ArrayList<LnkComment>();
	private List<DataMinPlan1> dataMinPlan1s = new ArrayList<DataMinPlan1>();
	
	
	
	private Set<DataGeoForm10> dataGeoForm10s = new HashSet<DataGeoForm10>(0);
	private Set<DataForm16> dataForm16s = new HashSet<DataForm16>(0);
	private Set<DataGeoForm2> dataGeoForm2s = new HashSet<DataGeoForm2>(0);
	private Set<DataGeoForm1> dataGeoForm1s = new HashSet<DataGeoForm1>(0);
	private Set<DataGeoForm6> dataGeoForm6s = new HashSet<DataGeoForm6>(0);
	private Set<DataGeoForm4> dataGeoForm4s = new HashSet<DataGeoForm4>(0);
	private Set<DataGeoForm5> dataGeoForm5s = new HashSet<DataGeoForm5>(0);
	private Set<DataGeoForm8> dataGeoForm8s = new HashSet<DataGeoForm8>(0);
	private Set<DataGeoForm9> dataGeoForm9s = new HashSet<DataGeoForm9>(0);
	private Set<DataForm14> dataForm14s = new HashSet<DataForm14>(0);
	private Set<DataForm15> dataForm15s = new HashSet<DataForm15>(0);
	
	
	private Set<DataGeoReport9> dataGeoReport9s = new HashSet<DataGeoReport9>(0);
	private Set<DataForm11> dataForm11s = new HashSet<DataForm11>(0);
	private Set<DataGeoPlan1> dataGeoReport1s = new HashSet<DataGeoPlan1>(0);
	private Set<DataGeoReport2> dataGeoReport2s = new HashSet<DataGeoReport2>(0);
	private Set<DataGeoReport8> dataGeoReport8s = new HashSet<DataGeoReport8>(0);
	private Set<DataGeoReport4> dataGeoReport4s = new HashSet<DataGeoReport4>(0);
	private Set<DataGeoReport6> dataGeoReport6s = new HashSet<DataGeoReport6>(0);
	
	public AnnualRegistration() {
		this.istodotgol = false;
	}

	public AnnualRegistration(Long id) {
		this.id = id;
	}

	public AnnualRegistration(Long id, String licensenum, String lpReg, Long reporttype, Integer lictype,
			String reportyear, Long repstatusid, String submissiondate, Long personid, Long officerid,
			String approveddate, List<LnkPlanNotes> lnkPlanNoteses) {
		this.id = id;
		this.licensenum = licensenum;
		this.lpReg = lpReg;
		this.reporttype = reporttype;
		this.lictype = lictype;
		this.reportyear = reportyear;
		this.repstatusid = repstatusid;
		this.submissiondate = submissiondate;
		this.personid = personid;
		this.officerid = officerid;
		this.approveddate = approveddate;
		this.lnkPlanNoteses = lnkPlanNoteses;
	}

	@Id
	@SequenceGenerator(name="ANNUAL_REGISTRATION_SEQ", sequenceName="ANNUAL_REGISTRATION_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE ,generator="ANNUAL_REGISTRATION_SEQ")
	@Column(name = "ID", unique = true, nullable = false, precision = 22, scale = 0)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getReasonid() {
		return reasonid;
	}

	public void setReasonid(Integer reasonid) {
		this.reasonid = reasonid;
	}

	@Column(name = "SOURCE_E")
	public String getSourceE() {
		return sourceE;
	}

	public void setSourceE(String sourceE) {
		this.sourceE = sourceE;
	}

	@Column(name = "SOURCE_W")
	public String getSourceW() {
		return sourceW;
	}

	public void setSourceW(String sourceW) {
		this.sourceW = sourceW;
	}

	@Column(name = "ISHIDDEN")
	public boolean isIshidden() {
		return ishidden;
	}

	public void setIshidden(boolean ishidden) {
		this.ishidden = ishidden;
	}

	@Column(name = "LPNAME")
	public String getLpName() {
		return lpName;
	}

	public void setLpName(String lpName) {
		this.lpName = lpName;
	}

	@Column(name = "CDURATION")
	public String getCduration() {
		return cduration;
	}

	public void setCduration(String cduration) {
		this.cduration = cduration;
	}
		
	@Column(name = "FADURATION")
	public String getFaduration() {
		return faduration;
	}

	public void setFaduration(String faduration) {
		this.faduration = faduration;
	}

	@Column(name = "FITIME")
	public String getFitime() {
		return fitime;
	}

	public void setFitime(String fitime) {
		this.fitime = fitime;
	}

	@Column(name = "SETIME")
	public String getSetime() {
		return setime;
	}

	public void setSetime(String setime) {
		this.setime = setime;
	}

	@Column(name = "THTIME")
	public String getThtime() {
		return thtime;
	}

	public void setThtime(String thtime) {
		this.thtime = thtime;
	}

	@Column(name = "FOTIME")
	public String getFotime() {
		return fotime;
	}

	public void setFotime(String fotime) {
		this.fotime = fotime;
	}

	@Column(name = "FATIME")
	public String getFatime() {
		return fatime;
	}

	public void setFatime(String fatime) {
		this.fatime = fatime;
	}

	@Column(name = "FIDURATION")
	public String getFiduration() {
		return fiduration;
	}

	public void setFiduration(String fiduration) {
		this.fiduration = fiduration;
	}

	@Column(name = "SEDURATION")
	public String getSeduration() {
		return seduration;
	}

	public void setSeduration(String seduration) {
		this.seduration = seduration;
	}

	@Column(name = "THDURATION")
	public String getThduration() {
		return thduration;
	}

	public void setThduration(String thduration) {
		this.thduration = thduration;
	}

	@Column(name = "FODURATION")
	public String getFoduration() {
		return foduration;
	}

	public void setFoduration(String foduration) {
		this.foduration = foduration;
	}

	@Column(name = "GDURATION")
	public String getGduration() {
		return gduration;
	}

	public void setGduration(String gduration) {
		this.gduration = gduration;
	}
	
	
	@Column(name = "CFIDURATION")
	public String getCfiduration() {
		return cfiduration;
	}

	public void setCfiduration(String cfiduration) {
		this.cfiduration = cfiduration;
	}

	@Column(name = "CSEDURATION")
	public String getCseduration() {
		return cseduration;
	}

	public void setCseduration(String cseduration) {
		this.cseduration = cseduration;
	}

	@Column(name = "CTHDURATION")
	public String getCthduration() {
		return cthduration;
	}

	public void setCthduration(String cthduration) {
		this.cthduration = cthduration;
	}

	@Column(name = "CFODURATION")
	public String getCfoduration() {
		return cfoduration;
	}

	public void setCfoduration(String cfoduration) {
		this.cfoduration = cfoduration;
	}

	@Column(name = "CFADURATION")
	public String getCfaduration() {
		return cfaduration;
	}

	public void setCfaduration(String cfaduration) {
		this.cfaduration = cfaduration;
	}

	@Column(name = "LASTMODIFIED")
	public String getLastmodified() {
		return lastmodified;
	}

	public void setLastmodified(String lastmodified) {
		this.lastmodified = lastmodified;
	}

	@Column(name = "DEPOSITID")
	public Long getDepositid() {
		return depositid;
	}

	public void setDepositid(Long depositid) {
		this.depositid = depositid;
	}

	@Column(name = "LICENSENUM", length = 20)
	public String getLicensenum() {
		return this.licensenum;
	}

	public void setLicensenum(String licensenum) {
		this.licensenum = licensenum;
	}

	
	@Column(name = "LICENSEXB", length = 20)
	
	public String getLicenseXB() {
		return licenseXB;
	}

	public void setLicenseXB(String licenseXB) {
		this.licenseXB = licenseXB;
	}

	@Column(name = "LP_REG", length = 7)
	public String getLpReg() {
		return this.lpReg;
	}

	public void setLpReg(String lpReg) {
		this.lpReg = lpReg;
	}

	@Column(name = "REPORTTYPE")
	public Long getReporttype() {
		return this.reporttype;
	}

	public void setReporttype(Long reporttype) {
		this.reporttype = reporttype;
	}

	@Column(name = "LICTYPE")
	public Integer getLictype() {
		return this.lictype;
	}

	public void setLictype(Integer lictype) {
		this.lictype = lictype;
	}

	@Column(name = "REPORTYEAR", length = 200)
	public String getReportyear() {
		return this.reportyear;
	}

	public void setReportyear(String reportyear) {
		this.reportyear = reportyear;
	}

	@Column(name = "REPSTATUSID", precision = 22, scale = 0)
	public Long getRepstatusid() {
		return this.repstatusid;
	}

	public void setRepstatusid(Long repstatusid) {
		this.repstatusid = repstatusid;
	}
	
	@Column(name = "REJECTSTEP", precision = 22, scale = 0)	
	public Long getRejectstep() {
		return rejectstep;
	}

	public void setRejectstep(Long rejectstep) {
		this.rejectstep = rejectstep;
	}

	@Column(name = "REJECT", precision = 22, scale = 0)	
	public Integer getReject() {
		return reject;
	}

	public void setReject(Integer reject) {
		this.reject = reject;
	}

	@Column(name = "XCOMMENT")
	public String getXcomment() {
		return xcomment;
	}

	public void setXcomment(String xcomment) {
		this.xcomment = xcomment;
	}

	@Column(name = "XFFILE")
	public String getXffile() {
		return xffile;
	}

	public void setXffile(String xffile) {
		this.xffile = xffile;
	}

	@Column(name = "XLFILE")
	public String getXlfile() {
		return xlfile;
	}

	public void setXlfile(String xlfile) {
		this.xlfile = xlfile;
	}

	@Column(name = "REPSTEPID", precision = 22, scale = 0)
	public Long getRepstepid() {
		return repstepid;
	}

	public void setRepstepid(Long repstepid) {
		this.repstepid = repstepid;
	}

	@Column(name = "SUBMISSIONDATE", length = 30)
	public String getSubmissiondate() {
		return this.submissiondate;
	}

	public void setSubmissiondate(String submissiondate) {
		this.submissiondate = submissiondate;
	}

	@Column(name = "PERSONID", precision = 22, scale = 0)
	public Long getPersonid() {
		return this.personid;
	}

	public void setPersonid(Long personid) {
		this.personid = personid;
	}

	@Column(name = "OFFICERID", precision = 22, scale = 0)
	public Long getOfficerid() {
		return this.officerid;
	}

	public void setOfficerid(Long officerid) {
		this.officerid = officerid;
	}

	@Column(name = "APPROVEDDATE", length = 200)
	public String getApproveddate() {
		return this.approveddate;
	}

	public void setApproveddate(String approveddate) {
		this.approveddate = approveddate;
	}
	
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "OFFICERID", nullable=false,insertable=false,updatable=false)
	@JsonBackReference
	public LutUsers getLutUsers() {
		return lutUsers;
	}

	public void setLutUsers(LutUsers lutUsers) {
		this.lutUsers = lutUsers;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "annualRegistration")
	@JsonBackReference
	public List<LnkPlanAttachedFiles> getLnkPlanAttachedFileses() {
		return this.lnkPlanAttachedFileses;
	}

	public void setLnkPlanAttachedFileses(List<LnkPlanAttachedFiles> lnkPlanAttachedFileses) {
		this.lnkPlanAttachedFileses = lnkPlanAttachedFileses;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "annualRegistration")
	@JsonBackReference
	public List<LnkPlanNotes> getLnkPlanNoteses() {
		return this.lnkPlanNoteses;
	}

	public void setLnkPlanNoteses(List<LnkPlanNotes> lnkPlanNoteses) {
		this.lnkPlanNoteses = lnkPlanNoteses;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "annualRegistration")
	@JsonBackReference
	public List<LnkCommentMain> getLnkCommentMains() {
		return this.lnkCommentMains;
	}

	public void setLnkCommentMains(List<LnkCommentMain> lnkCommentMains) {
		this.lnkCommentMains = lnkCommentMains;
	}


	@OneToMany(fetch = FetchType.LAZY, mappedBy = "annualRegistration")
	@JsonBackReference
	public List<LnkPlanTab> getLnkPlanTabs() {
		return this.lnkPlanTabs;
	}

	public void setLnkPlanTabs(List<LnkPlanTab> lnkPlanTabs) {
		this.lnkPlanTabs = lnkPlanTabs;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "annualRegistration")
	@JsonBackReference
	public List<LnkPlanTransition> getLnkPlanTransitions() {
		return this.lnkPlanTransitions;
	}

	public void setLnkPlanTransitions(List<LnkPlanTransition> lnkPlanTransitions) {
		this.lnkPlanTransitions = lnkPlanTransitions;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "annualRegistration")
	@JsonBackReference
	public List<LnkComment> getLnkComments() {
		return this.lnkComments;
	}

	public void setLnkComments(List<LnkComment> lnkComments) {
		this.lnkComments = lnkComments;
	}


	
	@Column(name = "REQID")
	public Long getReqid() {
		return reqid;
	}

	public void setReqid(Long reqid) {
		this.reqid = reqid;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "REQID", nullable=false,insertable=false,updatable=false)
	@JsonManagedReference
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	public RegReportReq getRegReportReq() {
		return regReportReq;
	}

	public void setRegReportReq(RegReportReq regReportReq) {
		this.regReportReq = regReportReq;
	}

	@Column(name = "XTYPE", length = 200)
	public Integer getXtype() {
		return xtype;
	}

	public void setXtype(Integer xtype) {
		this.xtype = xtype;
	}

	@Column(name = "DIVISIONID", length = 200)
	public Long getDivisionid() {
		return divisionid;
	}

	public void setDivisionid(Long divisionid) {
		this.divisionid = divisionid;
	}

	@Column(name = "MINID", length = 200)
	public Long getMinid() {
		return minid;
	}

	public void setMinid(Long minid) {
		this.minid = minid;
	}

	
	
	@Column(name = "GROUPID", length = 200)
	public Integer getGroupid() {
		return groupid;
	}

	public void setGroupid(Integer groupid) {
		this.groupid = groupid;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "LP_REG", referencedColumnName="LP_REG", nullable=false,insertable=false,updatable=false)
	@JsonBackReference
	public SubLegalpersons getSubLegalpersons() {
		return this.subLegalpersons;
	}

	public void setSubLegalpersons(SubLegalpersons subLegalpersons) {
		this.subLegalpersons = subLegalpersons;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "annualRegistration")
	@JsonBackReference
	public Set<DataGeoForm10> getDataGeoForm10s() {
		return this.dataGeoForm10s;
	}

	public void setDataGeoForm10s(Set<DataGeoForm10> dataGeoForm10s) {
		this.dataGeoForm10s = dataGeoForm10s;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "annualRegistration")
	@JsonBackReference
	public Set<DataForm16> getDataForm16s() {
		return this.dataForm16s;
	}

	public void setDataForm16s(Set<DataForm16> dataForm16s) {
		this.dataForm16s = dataForm16s;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "annualRegistration")
	@JsonBackReference
	public Set<DataGeoForm2> getDataGeoForm2s() {
		return this.dataGeoForm2s;
	}

	public void setDataGeoForm2s(Set<DataGeoForm2> dataGeoForm2s) {
		this.dataGeoForm2s = dataGeoForm2s;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "annualRegistration")
	@JsonBackReference
	public Set<DataGeoForm1> getDataGeoForm1s() {
		return this.dataGeoForm1s;
	}

	public void setDataGeoForm1s(Set<DataGeoForm1> dataGeoForm1s) {
		this.dataGeoForm1s = dataGeoForm1s;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "annualRegistration")
	@JsonBackReference
	public Set<DataGeoForm6> getDataGeoForm6s() {
		return this.dataGeoForm6s;
	}

	public void setDataGeoForm6s(Set<DataGeoForm6> dataGeoForm6s) {
		this.dataGeoForm6s = dataGeoForm6s;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "annualRegistration")
	@JsonBackReference
	public Set<DataGeoForm4> getDataGeoForm4s() {
		return this.dataGeoForm4s;
	}

	public void setDataGeoForm4s(Set<DataGeoForm4> dataGeoForm4s) {
		this.dataGeoForm4s = dataGeoForm4s;
	}


	@OneToMany(fetch = FetchType.LAZY, mappedBy = "annualRegistration")
	@JsonBackReference
	public Set<DataGeoForm5> getDataGeoForm5s() {
		return this.dataGeoForm5s;
	}

	public void setDataGeoForm5s(Set<DataGeoForm5> dataGeoForm5s) {
		this.dataGeoForm5s = dataGeoForm5s;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "annualRegistration")
	@JsonBackReference
	public Set<DataGeoForm8> getDataGeoForm8s() {
		return this.dataGeoForm8s;
	}

	public void setDataGeoForm8s(Set<DataGeoForm8> dataGeoForm8s) {
		this.dataGeoForm8s = dataGeoForm8s;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "annualRegistration")
	@JsonBackReference
	public Set<DataGeoForm9> getDataGeoForm9s() {
		return this.dataGeoForm9s;
	}

	public void setDataGeoForm9s(Set<DataGeoForm9> dataGeoForm9s) {
		this.dataGeoForm9s = dataGeoForm9s;
	}



	@OneToMany(fetch = FetchType.LAZY, mappedBy = "annualRegistration")
	@JsonBackReference
	public Set<DataForm14> getDataForm14s() {
		return this.dataForm14s;
	}

	public void setDataForm14s(Set<DataForm14> dataForm14s) {
		this.dataForm14s = dataForm14s;
	}


	@OneToMany(fetch = FetchType.LAZY, mappedBy = "annualRegistration")
	@JsonBackReference
	public Set<DataForm15> getDataForm15s() {
		return this.dataForm15s;
	}

	public void setDataForm15s(Set<DataForm15> dataForm15s) {
		this.dataForm15s = dataForm15s;
	}


	@OneToMany(fetch = FetchType.LAZY, mappedBy = "annualRegistration")
	@JsonBackReference
	public Set<DataGeoReport9> getDataGeoReport9s() {
		return this.dataGeoReport9s;
	}

	public void setDataGeoReport9s(Set<DataGeoReport9> dataGeoReport9s) {
		this.dataGeoReport9s = dataGeoReport9s;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "annualRegistration")
	@JsonBackReference
	public Set<DataForm11> getDataForm11s() {
		return this.dataForm11s;
	}

	public void setDataForm11s(Set<DataForm11> dataForm11s) {
		this.dataForm11s = dataForm11s;
	}



	@OneToMany(fetch = FetchType.LAZY, mappedBy = "annualRegistration")
	@JsonBackReference
	public Set<DataGeoPlan1> getDataGeoReport1s() {
		return this.dataGeoReport1s;
	}

	public void setDataGeoReport1s(Set<DataGeoPlan1> dataGeoReport1s) {
		this.dataGeoReport1s = dataGeoReport1s;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "annualRegistration")
	@JsonBackReference
	public Set<DataGeoReport2> getDataGeoReport2s() {
		return this.dataGeoReport2s;
	}

	public void setDataGeoReport2s(Set<DataGeoReport2> dataGeoReport2s) {
		this.dataGeoReport2s = dataGeoReport2s;
	}



	@OneToMany(fetch = FetchType.LAZY, mappedBy = "annualRegistration")
	@JsonBackReference
	public Set<DataGeoReport8> getDataGeoReport8s() {
		return this.dataGeoReport8s;
	}

	public void setDataGeoReport8s(Set<DataGeoReport8> dataGeoReport8s) {
		this.dataGeoReport8s = dataGeoReport8s;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "annualRegistration")
	@JsonBackReference
	public Set<DataGeoReport4> getDataGeoReport4s() {
		return this.dataGeoReport4s;
	}

	public void setDataGeoReport4s(Set<DataGeoReport4> dataGeoReport4s) {
		this.dataGeoReport4s = dataGeoReport4s;
	}

	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "annualRegistration")
	@JsonBackReference
	public List<DataMinPlan1> getDataMinPlan1s() {
		return dataMinPlan1s;
	}

	public void setDataMinPlan1s(List<DataMinPlan1> dataMinPlan1s) {
		this.dataMinPlan1s = dataMinPlan1s;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "annualRegistration")
	@JsonBackReference
	public Set<DataGeoReport6> getDataGeoReport6s() {
		return this.dataGeoReport6s;
	}

	public void setDataGeoReport6s(Set<DataGeoReport6> dataGeoReport6s) {
		this.dataGeoReport6s = dataGeoReport6s;
	}

	public Boolean getIstodotgol() {
		return istodotgol;
	}

	public void setIstodotgol(Boolean istodotgol) {
		this.istodotgol = istodotgol;
	}

}
