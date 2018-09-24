package com.peace.users.model.mram;
// Generated Nov 17, 2015 5:32:33 PM by Hibernate Tools 4.3.1.Final

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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

/**
 * RegReportReq generated by hbm2java
 */
@Entity
@Table(name = "REG_REPORT_REQ")
public class RegReportReq implements java.io.Serializable {

	private Integer bundledLicenseNum;
	private Long id;
	private String lpReg;
	private String addBunLicenseNum;
	private String areaName;
	private Long reportTypeId;
	private Long divisionId;
	private String licenseXB;
	private Integer lictype;
	private String isRedemtion;
	private String latestChangeDateTime;
	private Integer wk;
	private Integer mv;
	private Integer groupid;
	private String mineralid;
	private Integer lnkAnns;
	private Integer lnkPvs;
	private Integer cyear;
	private Integer cplan;
	private Integer creport;
	private Integer xplan;
	private Integer xreport;
	
	private Boolean isactive;
	private List<RegWeeklyMontly> regWeeklyMontlies = new ArrayList<RegWeeklyMontly>();
	private Set<LnkWeeklyProduct> lnkWeeklyProducts = new HashSet<LnkWeeklyProduct>(0);
	private List<LnkReportRegBunl> lnkReportRegBunl = new ArrayList<LnkReportRegBunl>();
	
	private Set<WeeklyRegistration> weeklyRegistrations = new HashSet<WeeklyRegistration>(0);	
	private Set<LnkReportWeekly> lnkReportWeeklies = new HashSet<LnkReportWeekly>(0);
	private Set<LnkWeeklyCountry> lnkWeeklyCountries = new HashSet<LnkWeeklyCountry>(0);

	private List<AnnualRegistration> annualRegistrations = new ArrayList<AnnualRegistration>();
	private List<LnkReqAnn> lnkReqAnns = new ArrayList<LnkReqAnn>();
	private List<LnkReqPv> lnkReqPvs = new ArrayList<LnkReqPv>();
	
	private long isconfiged;
	//private LnkReportRegBunl lnkReportRegBunl;
	
	
	private long plan;
	
	private long report;

	public RegReportReq() {
	}

	public RegReportReq(Integer bundledLicenseNum, Long id, String lpReg, String addBunLicenseNum, String areaName,
			Long reportTypeId, String isRedemtion, String latestChangeDateTime) {
		this.bundledLicenseNum = bundledLicenseNum;
		this.id = id;
		this.lpReg = lpReg;
		this.addBunLicenseNum = addBunLicenseNum;
		this.areaName = areaName;
		this.reportTypeId = reportTypeId;
		this.isRedemtion = isRedemtion;
		this.latestChangeDateTime = latestChangeDateTime;
	}

	public RegReportReq(Integer bundledLicenseNum, Long id, String lpReg, String addBunLicenseNum, String areaName,
			Long reportTypeId, String isRedemtion, String latestChangeDateTime,
			List<RegWeeklyMontly> regWeeklyMontlies, List<LnkReportRegBunl> lnkReportRegBunl) {
		this.bundledLicenseNum = bundledLicenseNum;
		this.id = id;
		this.lpReg = lpReg;
		this.addBunLicenseNum = addBunLicenseNum;
		this.areaName = areaName;
		this.reportTypeId = reportTypeId;
		this.isRedemtion = isRedemtion;
		this.latestChangeDateTime = latestChangeDateTime;
		this.regWeeklyMontlies = regWeeklyMontlies;
		this.lnkReportRegBunl = lnkReportRegBunl;
	}

	@Id
	@SequenceGenerator(name="REG_REQ_REPORT_SEQ", sequenceName="REG_REQ_REPORT_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE ,generator="REG_REQ_REPORT_SEQ")	
	@Column(name = "ID", unique = true,  nullable = false, precision = 22, scale = 0)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	
	@Column(name = "BundledLicenseNum", nullable = false, length = 10)
	public Integer getBundledLicenseNum() {
		return this.bundledLicenseNum;
	}

	public void setBundledLicenseNum(Integer bundledLicenseNum) {
		this.bundledLicenseNum = bundledLicenseNum;
	}
	

	@Column(name = "CYEAR")
	public Integer getCyear() {
		return cyear;
	}

	public void setCyear(Integer cyear) {
		this.cyear = cyear;
	}

	@Column(name = "CPLAN")
	public Integer getCplan() {
		return cplan;
	}

	public void setCplan(Integer cplan) {
		this.cplan = cplan;
	}
	
	@Column(name = "XPLAN")
	public Integer getXplan() {
		return xplan;
	}

	public void setXplan(Integer xplan) {
		this.xplan = xplan;
	}

	@Column(name = "XREPORT")
	public Integer getXreport() {
		return xreport;
	}

	public void setXreport(Integer xreport) {
		this.xreport = xreport;
	}

	@Column(name = "CREPORT")
	public Integer getCreport() {
		return creport;
	}

	public void setCreport(Integer creport) {
		this.creport = creport;
	}
	
	@Column(name = "MINERALID")
	public String getMineralid() {
		return mineralid;
	}

	public void setMineralid(String mineralid) {
		this.mineralid = mineralid;
	}

	@Column(name = "LICENSEXB")
	public String getLicenseXB() {
		return licenseXB;
	}

	public void setLicenseXB(String licenseXB) {
		this.licenseXB = licenseXB;
	}

	@Column(name = "LP_Reg", nullable = false)
	public String getLpReg() {
		return this.lpReg;
	}

	public void setLpReg(String lpReg) {
		this.lpReg = lpReg;
	}

	@Column(name = "ADD_BunLicenseNum", nullable = false)
	public String getAddBunLicenseNum() {
		return this.addBunLicenseNum;
	}

	public void setAddBunLicenseNum(String addBunLicenseNum) {
		this.addBunLicenseNum = addBunLicenseNum;
	}

	@Column(name = "AreaName", nullable = false, length = 100)
	public String getAreaName() {
		return this.areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	@Column(name = "ReportTypeID",  precision = 22, scale = 0)
	public Long getReportTypeId() {
		return this.reportTypeId;
	}

	public void setReportTypeId(Long reportTypeId) {
		this.reportTypeId = reportTypeId;
	}

	@Column(name = "IsRedemtion")
	public String getIsRedemtion() {
		return this.isRedemtion;
	}

	public void setIsRedemtion(String isRedemtion) {
		this.isRedemtion = isRedemtion;
	}

	@Column(name = "LatestChangeDateTime", nullable = false, length = 20)
	public String getLatestChangeDateTime() {
		return this.latestChangeDateTime;
	}

	public void setLatestChangeDateTime(String latestChangeDateTime) {
		this.latestChangeDateTime = latestChangeDateTime;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "regReportReq")
	@JsonBackReference
	public List<RegWeeklyMontly> getRegWeeklyMontlies() {
		return this.regWeeklyMontlies;
	}

	public void setRegWeeklyMontlies(List<RegWeeklyMontly> regWeeklyMontlies) {
		this.regWeeklyMontlies = regWeeklyMontlies;
	}


	
	@Column(name = "DIVISIONID")
	public Long getDivisionId() {
		return divisionId;
	}

	public void setDivisionId(Long divisionId) {
		this.divisionId = divisionId;
	}

	@Column(name = "ISACTIVE")
	public Boolean getIsactive() {
		return isactive;
	}

	public void setIsactive(Boolean isactive) {
		this.isactive = isactive;
	}

	@Column(name = "LICTYPE")
	public Integer getLictype() {
		return lictype;
	}

	public void setLictype(Integer lictype) {
		this.lictype = lictype;
	}
	
	
	
	@Column(name = "GROUPID")
	public Integer getGroupid() {
		return groupid;
	}

	public void setGroupid(Integer groupid) {
		this.groupid = groupid;
	}

	@Column(name = "MV")
	public Integer getMv() {
		return mv;
	}

	public void setMv(Integer mv) {
		this.mv = mv;
	}

	@Column(name = "WK")
	public Integer getWk() {
		return wk;
	}

	public void setWk(Integer wk) {
		this.wk = wk;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "regReportReq")
	@JsonBackReference
	public Set<WeeklyRegistration> getWeeklyRegistrations() {
		return this.weeklyRegistrations;
	}

	public void setWeeklyRegistrations(Set<WeeklyRegistration> weeklyRegistrations) {
		this.weeklyRegistrations = weeklyRegistrations;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "regReportReq")
	@JsonBackReference
	public Set<LnkWeeklyProduct> getLnkWeeklyProducts() {
		return this.lnkWeeklyProducts;
	}

	public void setLnkWeeklyProducts(Set<LnkWeeklyProduct> lnkWeeklyProducts) {
		this.lnkWeeklyProducts = lnkWeeklyProducts;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "regReportReq")
	@JsonBackReference
	public Set<LnkReportWeekly> getLnkReportWeeklies() {
		return this.lnkReportWeeklies;
	}

	public void setLnkReportWeeklies(Set<LnkReportWeekly> lnkReportWeeklies) {
		this.lnkReportWeeklies = lnkReportWeeklies;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "regReportReq")
	@JsonBackReference
	public Set<LnkWeeklyCountry> getLnkWeeklyCountries() {
		return this.lnkWeeklyCountries;
	}

	public void setLnkWeeklyCountries(Set<LnkWeeklyCountry> lnkWeeklyCountries) {
		this.lnkWeeklyCountries = lnkWeeklyCountries;
	}
	

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "regReportReq")
	@JsonBackReference
	public List<LnkReportRegBunl> getLnkReportRegBunl() {
		return lnkReportRegBunl;
	}

	public void setLnkReportRegBunl(List<LnkReportRegBunl> lnkReportRegBunl) {
		this.lnkReportRegBunl = lnkReportRegBunl;
	}


	@OneToMany(fetch = FetchType.LAZY, mappedBy = "regReportReq")
	@JsonBackReference
	public List<LnkReqAnn> getLnkReqAnns() {
		return this.lnkReqAnns;
	}

	public void setLnkReqAnns(List<LnkReqAnn> lnkReqAnns) {
		this.lnkReqAnns = lnkReqAnns;
	}
	

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "regReportReq")
	@JsonBackReference
	public List<LnkReqPv> getLnkReqPvs() {
		return this.lnkReqPvs;
	}

	public void setLnkReqPvs(List<LnkReqPv> lnkReqPvs) {
		this.lnkReqPvs = lnkReqPvs;
	}

	public long getIsconfiged() {
		return isconfiged;
	}

	public void setIsconfiged(long isconfiged) {
		this.isconfiged = isconfiged;
	}
		
	@Transient
	public Integer getLnkAnns() {
		return lnkAnns;
	}

	public void setLnkAnns(Integer lnkAnns) {
		this.lnkAnns = lnkAnns;
	}
	
	@Transient
	public Integer getLnkPvs() {
		return lnkPvs;
	}

	public void setLnkPvs(Integer lnkPvs) {
		this.lnkPvs = lnkPvs;
	}

	@Transient
	public long getPlan() {
		return plan;
	}

	public void setPlan(long plan) {
		this.plan = plan;
	}
	@Transient
	public long getReport() {
		return report;
	}

	public void setReport(long report) {
		this.report = report;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "regReportReq")
	@JsonBackReference
	public List<AnnualRegistration> getAnnualRegistrations() {
		return annualRegistrations;
	}

	public void setAnnualRegistrations(List<AnnualRegistration> annualRegistrations) {
		this.annualRegistrations = annualRegistrations;
	}

	
}