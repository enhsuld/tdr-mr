package com.peace.users.model.mram;
// Generated Nov 17, 2015 5:32:33 PM by Hibernate Tools 4.3.1.Final

import java.math.BigDecimal;
import java.util.Date;

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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * SubLicenses generated by hbm2java
 */
@Entity
@Table(name = "SUB_LICENSES")
public class SubLicenses implements java.io.Serializable {

	private Long id;
	private LutLictype lutLictype;
	private LutLicstatus lutLicstatus;
	private LutMinerals lutMinerals;
	private SubLegalpersons subLegalpersons;
	private Integer licTypeId;
	private Integer licStatusId;
	private Integer licenseNum;
	private Long mintype;
	private String licenseXB;
	private String lpReg;
	private String lpName;
	private String grantDate;
	private String expDate;
	private float areaSize;
	private String locationAimag;
	private String locationSoum;
	private String areaNameMon;
	private String areaNameEng;
	private Integer radioactiveFlagId;
	private Long divisionId;
	private Long aimagConstractionId;
	private Integer configured;
	

	private Boolean monthly;
	private Boolean plan;
	private Boolean redemptionplan;
	private Boolean redemptionreport;
	private Boolean report;
	private Boolean weekly;
	private Boolean haiguulreport;
	private Boolean ftime;
	private Boolean lplan;
	private Boolean lreport;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date configureddate;

	public SubLicenses() {
	}

	public SubLicenses(Long id, LutLictype lutLictype, LutLicstatus lutLicstatus, LutMinerals lutMinerals,
			SubLegalpersons subLegalpersons, Integer licenseNum, String grantDate, String expDate, Long areaSize,
			String locationAimag, String locationSoum, String areaNameMon, String areaNameEng,
			Integer radioactiveFlagId, Long aimagConstractionId, Integer configured) {
		this.id = id;
		this.lutLictype = lutLictype;
		this.lutLicstatus = lutLicstatus;
		this.lutMinerals = lutMinerals;
		this.subLegalpersons = subLegalpersons;
		this.licenseNum = licenseNum;
		this.grantDate = grantDate;
		this.expDate = expDate;
		this.areaSize = areaSize;
		this.locationAimag = locationAimag;
		this.locationSoum = locationSoum;
		this.areaNameMon = areaNameMon;
		this.areaNameEng = areaNameEng;
		this.radioactiveFlagId = radioactiveFlagId;
		this.aimagConstractionId = aimagConstractionId;
		this.configured = configured;
	}

	@Id
	@SequenceGenerator(name="SUB_LICENSE_SEQ", sequenceName="SUB_LICENSE_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE ,generator="SUB_LICENSE_SEQ")		
	@Column(name = "ID", unique = true, nullable = false, precision = 22, scale = 0)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(name = "LicStatusID")	
	public Integer getLicStatusId() {
		return licStatusId;
	}

	public void setLicStatusId(Integer licStatusId) {
		this.licStatusId = licStatusId;
	}

	@Column(name = "LicTypeID", nullable = false, length = 10)
	public Integer getLicTypeId() {
		return licTypeId;
	}

	public void setLicTypeId(Integer licTypeId) {
		this.licTypeId = licTypeId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "LicTypeID", nullable = false, insertable=false,updatable=false)
	@JsonBackReference
	public LutLictype getLutLictype() {
		return this.lutLictype;
	}

	public void setLutLictype(LutLictype lutLictype) {
		this.lutLictype = lutLictype;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "LicStatusID", nullable = false,insertable=false,updatable=false)
	@JsonBackReference
	public LutLicstatus getLutLicstatus() {
		return this.lutLicstatus;
	}

	public void setLutLicstatus(LutLicstatus lutLicstatus) {
		this.lutLicstatus = lutLicstatus;
	}
		
	public Boolean getFtime() {
		return ftime;
	}

	public void setFtime(Boolean ftime) {
		this.ftime = ftime;
	}

	public Boolean getLplan() {
		return lplan;
	}

	public void setLplan(Boolean lplan) {
		this.lplan = lplan;
	}

	public Boolean getLreport() {
		return lreport;
	}

	public void setLreport(Boolean lreport) {
		this.lreport = lreport;
	}

	@Column(name = "LP_NAMEMON")
	public String getLpName() {
		return lpName;
	}

	public void setLpName(String lpName) {
		this.lpName = lpName;
	}

	@Column(name = "MineralID",  length = 10)
	public Long getMintype() {
		return mintype;
	}

	public void setMintype(Long mintype) {
		this.mintype = mintype;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "MineralID", nullable = false, insertable=false,updatable=false)
	@JsonBackReference
	public LutMinerals getLutMinerals() {
		return this.lutMinerals;
	}

	public void setLutMinerals(LutMinerals lutMinerals) {
		this.lutMinerals = lutMinerals;
	}

	
	
	@Column(name = "LP_Reg", nullable = false, length = 10)
	public String getLpReg() {
		return lpReg;
	}

	public void setLpReg(String lpReg) {
		this.lpReg = lpReg;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "LP_Reg", referencedColumnName="LP_Reg", nullable = false,insertable=false,updatable=false)
	@JsonBackReference
	public SubLegalpersons getSubLegalpersons() {
		return this.subLegalpersons;
	}

	public void setSubLegalpersons(SubLegalpersons subLegalpersons) {
		this.subLegalpersons = subLegalpersons;
	}

	@Column(name = "LicenseNum", nullable = false, length = 10)
	public Integer getLicenseNum() {
		return this.licenseNum;
	}

	public void setLicenseNum(Integer licenseNum) {
		this.licenseNum = licenseNum;
	}
	
	
	@Column(name = "MONTHLY", nullable = false, length = 10)
	public Boolean getMonthly() {
		return monthly;
	}

	public void setMonthly(Boolean monthly) {
		this.monthly = monthly;
	}

	@Column(name = "PLAN", nullable = false, length = 10)
	public Boolean getPlan() {
		return plan;
	}

	public void setPlan(Boolean plan) {
		this.plan = plan;
	}

	@Column(name = "REDEMPTIONPLAN", nullable = false, length = 10)
	public Boolean getRedemptionplan() {
		return redemptionplan;
	}

	public void setRedemptionplan(Boolean redemptionplan) {
		this.redemptionplan = redemptionplan;
	}

	@Column(name = "REDEMPTIONREPORT", nullable = false, length = 10)
	public Boolean getRedemptionreport() {
		return redemptionreport;
	}

	public void setRedemptionreport(Boolean redemptionreport) {
		this.redemptionreport = redemptionreport;
	}

	@Column(name = "REPORT", nullable = false, length = 10)
	public Boolean getReport() {
		return report;
	}

	public void setReport(Boolean report) {
		this.report = report;
	}
	

	@Column(name = "WEEKLY", nullable = false, length = 10)
	public Boolean getWeekly() {
		return weekly;
	}
	public void setWeekly(Boolean weekly) {
		this.weekly = weekly;
	}

	@Column(name = "LicenseXM", nullable = false, length = 10)
	public String getLicenseXB() {
		return licenseXB;
	}

	public void setLicenseXB(String licenseXB) {
		this.licenseXB = licenseXB;
	}

	@Column(name = "GrantDate")
	public String getGrantDate() {
		return this.grantDate;
	}

	public void setGrantDate(String grantDate) {
		this.grantDate = grantDate;
	}

	@Column(name = "ExpDate")
	public String getExpDate() {
		return this.expDate;
	}

	public void setExpDate(String expDate) {
		this.expDate = expDate;
	}

	@Column(name = "AreaSize", nullable = false, precision = 22, scale = 0)
	public float getAreaSize() {
		return this.areaSize;
	}

	public void setAreaSize(float areaSize) {
		this.areaSize = areaSize;
	}
	
	
	@Column(name = "DIVISIONID",  length = 50)
	public Long getDivisionId() {
		return divisionId;
	}

	public void setDivisionId(Long divisionId) {
		this.divisionId = divisionId;
	}

	@Column(name = "LocationAimag")
	public String getLocationAimag() {
		return this.locationAimag;
	}

	public void setLocationAimag(String locationAimag) {
		this.locationAimag = locationAimag;
	}

	@Column(name = "LocationSoum")
	public String getLocationSoum() {
		return this.locationSoum;
	}

	public void setLocationSoum(String locationSoum) {
		this.locationSoum = locationSoum;
	}

	@Column(name = "AreaNameMon", nullable = false)
	public String getAreaNameMon() {
		return this.areaNameMon;
	}

	public void setAreaNameMon(String areaNameMon) {
		this.areaNameMon = areaNameMon;
	}

	@Column(name = "AreaNameEng", nullable = false)
	public String getAreaNameEng() {
		return this.areaNameEng;
	}

	public void setAreaNameEng(String areaNameEng) {
		this.areaNameEng = areaNameEng;
	}

	@Column(name = "RadioactiveFlagID", precision = 22, scale = 0)
	public Integer getRadioactiveFlagId() {
		return this.radioactiveFlagId;
	}

	public void setRadioactiveFlagId(Integer radioactiveFlagId) {
		this.radioactiveFlagId = radioactiveFlagId;
	}

	@Column(name = "AIMAGCONSTRUCTIONID",  precision = 22, scale = 0)
	public Long getAimagConstractionId() {
		return this.aimagConstractionId;
	}

	public void setAimagConstractionId(Long aimagConstractionId) {
		this.aimagConstractionId = aimagConstractionId;
	}

	@Column(name = "CONFIGURED")
	public Integer getConfigured() {
		return configured;
	}

	public void setConfigured(Integer configured) {
		this.configured = configured;
	}
	
	@JsonFormat(pattern="yyyy-MM-dd")
	public Date getConfigureddate() {
		return configureddate;
	}

	public void setConfigureddate(Date configureddate) {
		this.configureddate = configureddate;
	}

	public Boolean getHaiguulreport() {
		return haiguulreport;
	}

	public void setHaiguulreport(Boolean haiguulreport) {
		this.haiguulreport = haiguulreport;
	}
	
}
