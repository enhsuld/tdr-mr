package com.peace.users.model.mram;
// Generated Nov 17, 2015 5:32:33 PM by Hibernate Tools 4.3.1.Final

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.gs.collections.impl.list.mutable.ArrayListAdapter;

/**
 * SubLegalpersons generated by hbm2java
 */
@Entity
@Table(name = "SUB_LEGALPERSONS")
public class SubLegalpersons implements java.io.Serializable {

	private String lpReg;
	private LutCountries lutCountries;
	
	private LutLptype lutLptype;
	
	private Long id;
	private String countryName;
	private Integer lpType;
	private Integer aimagid;
	private Integer sumid;
	private String steteReg;
	private String lpName;
	private String lpNameL1;
	private String phone;
	private String famName;
	private String famNameL1;
	private String givName;
	private String givNameL1;
	
	private String city;
	private String cityL1;
	private String disctrict;
	private String disctrictL1;
	private String khoroo;
	private String khorooL1;
	private String street;
	private String streetL1;
	private String postbox;
	
	
	private String GENGINEER;
	private String GENGINEERPHONE;
	private String GENGINEERMAIL;
	private String GEOLOGIST;
	private String GEOLOGISTPHONE;
	private String GEOLOGISTMAIL;
	private String MSURVEYOR;
	private String MSURVEYORPHONE;
	private String MSURVEYOREMAIL;
	private String ACCOUNTANT;
	private String ACCOUNTANTPHONE;
	private String ACCOUNTANTEMAIL;
	private String ECONOMIST;
	private String ECONOMISTPHONE;
	private String ECONOMISTEMAIL;
	
	
	private String minehead;
	private String minephone;
	private String mineemail;
	
	private String KEYMAN;
	private String KEYMANPHONE;
	private String KEYMANEMAIL;
	
	
	private String mobile;
	private String email;
	private String personName;
	private Boolean confirmed;
	private Integer ispublic;
	@Temporal(TemporalType.TIMESTAMP)
	private Date updateddate;
	private Set<SubAddLpInfo> subAddLpInfos = new HashSet<SubAddLpInfo>(0);
	private Set<RegWeeklyMontly> regWeeklyMontlies = new HashSet<RegWeeklyMontly>(0);
	private Set<SubReportRegistration> subReportRegistrations = new HashSet<SubReportRegistration>(0);
	private Set<LutDivision> lutDivisions = new HashSet<LutDivision>(0);
	private Set<SubLicenses> subLicenseses = new HashSet<SubLicenses>(0);
	private Set<AnnualRegistration> annualRegistration = new HashSet<AnnualRegistration>(0);
	
	private List<LutUsers> LutUsers = new ArrayList<LutUsers>();

	public SubLegalpersons() {
	}

	public SubLegalpersons(String lpReg, Long id) {
		this.lpReg = lpReg;
		this.id = id;
	}

	public SubLegalpersons(String lpReg, LutCountries lutCountries, Long id, Integer lpType, String lpName,
			String lpNameL1, String phone, String famName, String famNameL1, String givName, String givNameL1,
			String mobile, String email,   Boolean confirmed, 
			Set<RegWeeklyMontly> regWeeklyMontlies, Set<SubReportRegistration> subReportRegistrations,
			Set<LutDivision> lutDivisions, Set<SubLicenses> subLicenseses) {
		this.lpReg = lpReg;
		this.lutCountries = lutCountries;
		this.id = id;
		this.lpType = lpType;
		this.lpName = lpName;
		this.lpNameL1 = lpNameL1;
		this.phone = phone;
		this.famName = famName;
		this.famNameL1 = famNameL1;
		this.givName = givName;
		this.givNameL1 = givNameL1;
		this.mobile = mobile;
		this.email = email;
		this.confirmed = confirmed;
		this.regWeeklyMontlies = regWeeklyMontlies;
		this.subReportRegistrations = subReportRegistrations;
		this.lutDivisions = lutDivisions;
		this.subLicenseses = subLicenseses;
	}

	@Id
	@SequenceGenerator(name="SUB_LEGALPERSONS_SEQ", sequenceName="SUB_LEGALPERSONS_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE ,generator="SUB_LEGALPERSONS_SEQ")
	@Column(name = "HolderID")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "LP_Type" , nullable = false, insertable=false,updatable=false)
	@JsonBackReference
	public LutLptype getLutLptype() {
		return this.lutLptype;
	}

	public void setLutLptype(LutLptype lutLptype) {
		this.lutLptype = lutLptype;
	}
	
	@Column(name = "LP_Reg", unique = true, nullable = false, length = 20)
	public String getLpReg() {
		return this.lpReg;
	}
	public void setLpReg(String lpReg) {
		this.lpReg = lpReg;
	}

/*	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "COUNTRYID",nullable = false,insertable=false,updatable=false)
	@JsonBackReference
	public LutCountries getLutCountries() {
		return this.lutCountries;
	}

	public void setLutCountries(LutCountries lutCountries) {
		this.lutCountries = lutCountries;
	}*/
	
	
	@Column(name = "STATEREG")
	public String getSteteReg() {
		return steteReg;
	}

	public void setSteteReg(String steteReg) {
		this.steteReg = steteReg;
	}

	
	@Column(name = "AIMAGID")
	public Integer getAimagid() {
		return aimagid;
	}

	public void setAimagid(Integer aimagid) {
		this.aimagid = aimagid;
	}

	@Column(name = "SUMID")
	public Integer getSumid() {
		return sumid;
	}

	public void setSumid(Integer sumid) {
		this.sumid = sumid;
	}
	
	
	@Column(name = "COUNTRYNAME")

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
	

	@Column(name = "LP_Type")
	public Integer getLpType() {
		return this.lpType;
	}

	public void setLpType(Integer lpType) {
		this.lpType = lpType;
	}

	@Column(name = "LP_NameMon")
	public String getLpName() {
		return this.lpName;
	}

	public void setLpName(String lpName) {
		this.lpName = lpName;
	}

	@Column(name = "LP_NameEng")
	public String getLpNameL1() {
		return this.lpNameL1;
	}

	public void setLpNameL1(String lpNameL1) {
		this.lpNameL1 = lpNameL1;
	}

	@Column(name = "Phone")
	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Column(name = "FamNameMon")
	public String getFamName() {
		return this.famName;
	}

	public void setFamName(String famName) {
		this.famName = famName;
	}

	@Column(name = "FamNameEng")
	public String getFamNameL1() {
		return this.famNameL1;
	}

	public void setFamNameL1(String famNameL1) {
		this.famNameL1 = famNameL1;
	}

	@Column(name = "GivNameMon")
	public String getGivName() {
		return this.givName;
	}

	public void setGivName(String givName) {
		this.givName = givName;
	}

	@Column(name = "GivNameEng")
	public String getGivNameL1() {
		return this.givNameL1;
	}

	public void setGivNameL1(String givNameL1) {
		this.givNameL1 = givNameL1;
	}

	@Column(name = "Mobile", length = 20)
	public String getMobile() {
		return this.mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	@Column(name = "Email")
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "PersonName")
	public String getPersonName() {
		return personName;
	}

	public void setPersonName(String personName) {
		this.personName = personName;
	}
	
	
	
	@Column(name = "confirmed")
	public Boolean getConfirmed() {
		return confirmed;
	}

	public void setConfirmed(Boolean confirmed) {
		this.confirmed = confirmed;
	}

	
	@Column(name = "ISPUBLIC")
	public Integer getIspublic() {
		return ispublic;
	}

	public void setIspublic(Integer ispublic) {
		this.ispublic = ispublic;
	}
	

	@Column(name = "CITY")
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@Column(name = "CITY_L1")
	public String getCityL1() {
		return cityL1;
	}

	public void setCityL1(String cityL1) {
		this.cityL1 = cityL1;
	}

	@Column(name = "DISTRICT")
	public String getDisctrict() {
		return disctrict;
	}

	public void setDisctrict(String disctrict) {
		this.disctrict = disctrict;
	}

	@Column(name = "DISTRICT_L1")
	public String getDisctrictL1() {
		return disctrictL1;
	}

	public void setDisctrictL1(String disctrictL1) {
		this.disctrictL1 = disctrictL1;
	}

	@Column(name = "KHOROO")
	public String getKhoroo() {
		return khoroo;
	}

	public void setKhoroo(String khoroo) {
		this.khoroo = khoroo;
	}

	
	@Column(name = "KHOROO_L1")
	public String getKhorooL1() {
		return khorooL1;
	}

	public void setKhorooL1(String khorooL1) {
		this.khorooL1 = khorooL1;
	}

	@Column(name = "STREET")
	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}
	
	@Column(name = "STREET_L1")
	public String getStreetL1() {
		return streetL1;
	}

	public void setStreetL1(String streetL1) {
		this.streetL1 = streetL1;
	}

	@Column(name = "POSTBOX")
	public String getPostbox() {
		return postbox;
	}

	public void setPostbox(String postbox) {
		this.postbox = postbox;
	}

	@Column(name = "GENGINEER")
	public String getGENGINEER() {
		return GENGINEER;
	}

	public void setGENGINEER(String gENGINEER) {
		GENGINEER = gENGINEER;
	}

	@Column(name = "GENGINEERPHONE")
	public String getGENGINEERPHONE() {
		return GENGINEERPHONE;
	}

	public void setGENGINEERPHONE(String gENGINEERPHONE) {
		GENGINEERPHONE = gENGINEERPHONE;
	}

	@Column(name = "GENGINEERMAIL")
	public String getGENGINEERMAIL() {
		return GENGINEERMAIL;
	}

	public void setGENGINEERMAIL(String gENGINEERMAIL) {
		GENGINEERMAIL = gENGINEERMAIL;
	}

	@Column(name = "GEOLOGIST")
	public String getGEOLOGIST() {
		return GEOLOGIST;
	}

	public void setGEOLOGIST(String gEOLOGIST) {
		GEOLOGIST = gEOLOGIST;
	}

	@Column(name = "GEOLOGISTPHONE")
	public String getGEOLOGISTPHONE() {
		return GEOLOGISTPHONE;
	}

	public void setGEOLOGISTPHONE(String gEOLOGISTPHONE) {
		GEOLOGISTPHONE = gEOLOGISTPHONE;
	}

	@Column(name = "GEOLOGISTMAIL")
	public String getGEOLOGISTMAIL() {
		return GEOLOGISTMAIL;
	}

	public void setGEOLOGISTMAIL(String gEOLOGISTMAIL) {
		GEOLOGISTMAIL = gEOLOGISTMAIL;
	}

	@Column(name = "MSURVEYOR")
	public String getMSURVEYOR() {
		return MSURVEYOR;
	}

	public void setMSURVEYOR(String mSURVEYOR) {
		MSURVEYOR = mSURVEYOR;
	}

	@Column(name = "MSURVEYORPHONE")
	public String getMSURVEYORPHONE() {
		return MSURVEYORPHONE;
	}

	public void setMSURVEYORPHONE(String mSURVEYORPHONE) {
		MSURVEYORPHONE = mSURVEYORPHONE;
	}

	@Column(name = "MSURVEYOREMAIL")
	public String getMSURVEYOREMAIL() {
		return MSURVEYOREMAIL;
	}

	public void setMSURVEYOREMAIL(String mSURVEYOREMAIL) {
		MSURVEYOREMAIL = mSURVEYOREMAIL;
	}

	@Column(name = "ACCOUNTANT")
	public String getACCOUNTANT() {
		return ACCOUNTANT;
	}

	public void setACCOUNTANT(String aCCOUNTANT) {
		ACCOUNTANT = aCCOUNTANT;
	}

	@Column(name = "ACCOUNTANTPHONE")
	public String getACCOUNTANTPHONE() {
		return ACCOUNTANTPHONE;
	}

	public void setACCOUNTANTPHONE(String aCCOUNTANTPHONE) {
		ACCOUNTANTPHONE = aCCOUNTANTPHONE;
	}

	@Column(name = "ACCOUNTANTEMAIL")
	public String getACCOUNTANTEMAIL() {
		return ACCOUNTANTEMAIL;
	}

	public void setACCOUNTANTEMAIL(String aCCOUNTANTEMAIL) {
		ACCOUNTANTEMAIL = aCCOUNTANTEMAIL;
	}

	@Column(name = "ECONOMIST")
	public String getECONOMIST() {
		return ECONOMIST;
	}

	public void setECONOMIST(String eCONOMIST) {
		ECONOMIST = eCONOMIST;
	}

	@Column(name = "ECONOMISTPHONE")
	public String getECONOMISTPHONE() {
		return ECONOMISTPHONE;
	}

	public void setECONOMISTPHONE(String eCONOMISTPHONE) {
		ECONOMISTPHONE = eCONOMISTPHONE;
	}

	@Column(name = "ECONOMISTEMAIL")
	public String getECONOMISTEMAIL() {
		return ECONOMISTEMAIL;
	}

	public void setECONOMISTEMAIL(String eCONOMISTEMAIL) {
		ECONOMISTEMAIL = eCONOMISTEMAIL;
	}



	@Column(name = "KEYMAN")
	public String getKEYMAN() {
		return KEYMAN;
	}

	public void setKEYMAN(String kEYMAN) {
		KEYMAN = kEYMAN;
	}

	@Column(name = "KEYMANPHONE")
	public String getKEYMANPHONE() {
		return KEYMANPHONE;
	}

	public void setKEYMANPHONE(String kEYMANPHONE) {
		KEYMANPHONE = kEYMANPHONE;
	}

	@Column(name = "KEYMANEMAIL")
	public String getKEYMANEMAIL() {
		return KEYMANEMAIL;
	}

	public void setKEYMANEMAIL(String kEYMANEMAIL) {
		KEYMANEMAIL = kEYMANEMAIL;
	}
	
	
	@Column(name = "MINEHEAD")
	public String getMinehead() {
		return minehead;
	}

	public void setMinehead(String minehead) {
		this.minehead = minehead;
	}

	@Column(name = "MINEPHONE")
	public String getMinephone() {
		return minephone;
	}

	public void setMinephone(String minephone) {
		this.minephone = minephone;
	}

	@Column(name = "MINEEMAIL")
	public String getMineemail() {
		return mineemail;
	}

	public void setMineemail(String mineemail) {
		this.mineemail = mineemail;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "subLegalpersons")
	@JsonBackReference
	public Set<SubLicenses> getSubLicenseses() {
		return subLicenseses;
	}

	public void setSubLicenseses(Set<SubLicenses> subLicenseses) {
		this.subLicenseses = subLicenseses;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "subLegalpersons")
	@JsonBackReference
	public Set<SubAddLpInfo> getSubAddLpInfos() {
		return this.subAddLpInfos;
	}



	public void setSubAddLpInfos(Set<SubAddLpInfo> subAddLpInfos) {
		this.subAddLpInfos = subAddLpInfos;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "subLegalpersons",cascade=CascadeType.ALL,orphanRemoval=true)
	@JsonBackReference
	public Set<RegWeeklyMontly> getRegWeeklyMontlies() {
		return this.regWeeklyMontlies;
	}

	public void setRegWeeklyMontlies(Set<RegWeeklyMontly> regWeeklyMontlies) {
		this.regWeeklyMontlies = regWeeklyMontlies;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "subLegalpersons",cascade=CascadeType.ALL,orphanRemoval=true)
	@JsonBackReference
	public Set<SubReportRegistration> getSubReportRegistrations() {
		return this.subReportRegistrations;
	}

	public void setSubReportRegistrations(Set<SubReportRegistration> subReportRegistrations) {
		this.subReportRegistrations = subReportRegistrations;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "subLegalpersons")
	@JsonBackReference
	public Set<LutDivision> getLutDivisions() {
		return this.lutDivisions;
	}

	public void setLutDivisions(Set<LutDivision> lutDivisions) {
		this.lutDivisions = lutDivisions;
	}

/*	@OneToMany(fetch = FetchType.LAZY, mappedBy = "subLegalpersons")
	@JsonBackReference
	public Set<SubLicenses> getSubLicenseses() {
		return this.subLicenseses;
	}

	public void setSubLicenseses(Set<SubLicenses> subLicenseses) {
		this.subLicenseses = subLicenseses;
	}*/
	

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "subLegalpersons",cascade=CascadeType.ALL,orphanRemoval=true)  
	@JsonBackReference
	public List<LutUsers> getLutUsers() {
		return LutUsers;
	}

	public void setLutUsers(List<LutUsers> lutUsers) {
		LutUsers = lutUsers;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "subLegalpersons",cascade=CascadeType.ALL,orphanRemoval=true)
	@JsonBackReference
	public Set<AnnualRegistration> getAnnualRegistration() {
		return annualRegistration;
	}

	public void setAnnualRegistration(Set<AnnualRegistration> annualRegistration) {
		this.annualRegistration = annualRegistration;
	}
	
	@JsonFormat(pattern="yyyy-MM-dd")
	public Date getUpdateddate() {
		return updateddate;
	}

	public void setUpdateddate(Date updateddate) {
		this.updateddate = updateddate;
	}
}