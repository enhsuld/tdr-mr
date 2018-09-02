package com.peace.users.model.mram;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.sql.Timestamp;

import javax.persistence.*;

import java.util.Date;
import java.util.List;


/**
 * The persistent class for the DATA_MIN_PLAN_3 database table.
 * 
 */
@Entity
@Table(name="DATA_NOTES_INFOS")
@NamedQuery(name="DataNotesInfo.findAll", query="SELECT d FROM DataNotesInfo d")
public class DataNotesInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="DATA_NOTES_INFOS_SEQ", sequenceName="DATA_NOTES_INFOS_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE ,generator="DATA_NOTES_INFOS_SEQ")
	@Column(name = "ID", unique = true, nullable = false, precision = 22, scale = 0)
	private long id;

	private Long noteid;
	
	private Long planid;
	
	private String mintype;
	private String licnum;
	private String year;
	private String formid;
	private String regid;
	private String holdername;
	private String givname;
	private String geologyst;
	private String accountant;
	private String engineer;
	private String economist;
	private String btech;
	private String minehead;
	private String msurveyor;
	private String engineering1;
	private String engineering2;
	private String orderyear;
	private String manager;
	private String hrmanager, dansname, companyname, description;
	public String getHrmanager() {
		return hrmanager;
	}

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setHrmanager(String hrmanager) {
		this.hrmanager = hrmanager;
	}

	public String getDansname() {
		return dansname;
	}

	public void setDansname(String dansname) {
		this.dansname = dansname;
	}

	public String getCompanyname() {
		return companyname;
	}

	public void setCompanyname(String companyname) {
		this.companyname = companyname;
	}

	public String getEngineering1() {
		return engineering1;
	}

	public void setEngineering1(String engineering1) {
		this.engineering1 = engineering1;
	}

	public String getEngineering2() {
		return engineering2;
	}

	public void setEngineering2(String engineering2) {
		this.engineering2 = engineering2;
	}

	public String getOfficer() {
		return officer;
	}

	public void setOfficer(String officer) {
		this.officer = officer;
	}

	private String officer;
	private Timestamp createdtime;
	
	//bi-directional many-to-one association to AnnualRegistration
	@ManyToOne
	@JoinColumn(name="planid", insertable=false, updatable=false)
	private AnnualRegistration annualRegistration;

	public DataNotesInfo() {
		Date date = new Date();
		this.createdtime = new Timestamp(date.getTime());
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}
		
	public Long getPlanid() {
		return planid;
	}

	public void setPlanid(Long planid) {
		this.planid = planid;
	}

	public Long getNoteid() {
		return this.noteid;
	}

	public void setNoteid(Long long1) {
		this.noteid = long1;
	}

	public AnnualRegistration getAnnualRegistration() {
		return this.annualRegistration;
	}

	public void setAnnualRegistration(AnnualRegistration annualRegistration) {
		this.annualRegistration = annualRegistration;
	}
	
	public void setField(String aFieldName, Object aValue) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		Field aField = getClass().getDeclaredField(aFieldName);
		aField.set(this, aValue);
    }

	public String getMintype() {
		return mintype;
	}

	public void setMintype(String mintype) {
		this.mintype = mintype;
	}

	public String getLicnum() {
		return licnum;
	}

	public void setLicnum(String licnum) {
		this.licnum = licnum;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getFormid() {
		return formid;
	}

	public void setFormid(String formid) {
		this.formid = formid;
	}

	public String getRegid() {
		return regid;
	}

	public void setRegid(String regid) {
		this.regid = regid;
	}

	public String getHoldername() {
		return holdername;
	}

	public void setHoldername(String holdername) {
		this.holdername = holdername;
	}

	public String getGivname() {
		return givname;
	}

	public void setGivname(String givname) {
		this.givname = givname;
	}

	public String getGeologyst() {
		return geologyst;
	}

	public void setGeologyst(String geologyst) {
		this.geologyst = geologyst;
	}

	public String getAccountant() {
		return accountant;
	}

	public void setAccountant(String accountant) {
		this.accountant = accountant;
	}

	public String getEngineer() {
		return engineer;
	}

	public void setEngineer(String engineer) {
		this.engineer = engineer;
	}

	public String getEconomist() {
		return economist;
	}

	public void setEconomist(String economist) {
		this.economist = economist;
	}

	public String getBtech() {
		return btech;
	}

	public void setBtech(String btech) {
		this.btech = btech;
	}

	public String getMinehead() {
		return minehead;
	}

	public void setMinehead(String minehead) {
		this.minehead = minehead;
	}

	public String getMsurveyor() {
		return msurveyor;
	}

	public void setMsurveyor(String msurveyor) {
		this.msurveyor = msurveyor;
	}

	public Timestamp getCreatedtime() {
		return createdtime;
	}

	public void setCreatedtime(Timestamp createdtime) {
		this.createdtime = createdtime;
	}

	public String getOrderyear() {
		return orderyear;
	}

	public void setOrderyear(String orderyear) {
		this.orderyear = orderyear;
	}

	public String getManager() {
		return manager;
	}

	public void setManager(String manager) {
		this.manager = manager;
	}

}