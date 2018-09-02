package com.peace.users.model.mram;

import java.io.Serializable;
import java.lang.reflect.Field;

import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the DATA_MIN_PLAN_3 database table.
 * 
 */
@Entity
@Table(name="DATA_MIN_PLAN_1")
@NamedQuery(name="DataMinPlan1.findAll", query="SELECT d FROM DataMinPlan1 d")
public class DataMinPlan1 implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="DATA_MIN_PLAN_1_SEQ", sequenceName="DATA_MIN_PLAN_1_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE ,generator="DATA_MIN_PLAN_1_SEQ")
	@Column(name = "ID", unique = true, nullable = false, precision = 22, scale = 0)
	private long id;

	private String data1;

	private String data2;

	private Double data3;

	private Long data4;

	private String data5;
	
	private String data6;
	
	private Double data7;
	
	private String data8;
	
	private Double data9;

	private Long noteid;
	
	private Long planid;
	
	private Long dataIndex;
	
	private Boolean istodotgol;
	
	@Transient
	private String horde;
	@Transient
	private String aimagid;
	@Transient
	private String sumid;
	@Transient
	private String mineralid;
	@Transient
	private String deposidid;
	@Transient
	private String countries;
	@Transient
	private String appdate;
	@Transient
	private String yearcapacity;
	@Transient
	private String workyear;
	@Transient
	private String minetypeid;
	@Transient
	private String statebudgetid;
	@Transient
	private String concetrate;
	@Transient
	private String komissid;
	@Transient
	private String komissdate;
	@Transient
	private String komissakt;
	@Transient
	private String startdate;
	@Transient
	private String bombtype;
	
	
	@Transient
	private String bombid;	
	@Transient
	private String lpName;	
	@Transient
	private String licenseXB;


	public Boolean getIstodotgol() {
		return istodotgol;
	}


	public void setIstodotgol(Boolean istodotgol) {
		this.istodotgol = istodotgol;
	}

	//bi-directional many-to-one association to AnnualRegistration
	@ManyToOne
	@JoinColumn(name="planid", insertable=false, updatable=false)
	private AnnualRegistration annualRegistration;

	public DataMinPlan1() {
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


	public Long getData4() {
		return this.data4;
	}

	public void setData4(Long data4) {
		this.data4 = data4;
	}

	public String getData5() {
		return this.data5;
	}

	public void setData5(String data5) {
		this.data5 = data5;
	}

	public Long getNoteid() {
		return this.noteid;
	}

	public void setNoteid(Long long1) {
		this.noteid = long1;
	}

	public String getData2() {
		return data2;
	}

	public void setData2(String data2) {
		this.data2 = data2;
	}

	public String getData1() {
		return data1;
	}

	public void setData1(String data1) {
		this.data1 = data1;
	}

	public Double getData3() {
		return data3;
	}

	public void setData3(Double data3) {
		this.data3 = data3;
	}

	public Long getDataIndex() {
		return dataIndex;
	}

	public void setDataIndex(Long dataIndex) {
		this.dataIndex = dataIndex;
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

	public String getData6() {
		return data6;
	}

	public void setData6(String data6) {
		this.data6 = data6;
	}

	public Double getData7() {
		return data7;
	}

	public void setData7(Double data7) {
		this.data7 = data7;
	}

	public String getData8() {
		return data8;
	}

	public void setData8(String data8) {
		this.data8 = data8;
	}

	public Double getData9() {
		return data9;
	}

	public void setData9(Double data9) {
		this.data9 = data9;
	}
	
	
	

	public String getHorde() {
		return horde;
	}


	public void setHorde(String horde) {
		this.horde = horde;
	}


	public String getAimagid() {
		return aimagid;
	}


	public void setAimagid(String aimagid) {
		this.aimagid = aimagid;
	}


	public String getSumid() {
		return sumid;
	}


	public void setSumid(String sumid) {
		this.sumid = sumid;
	}


	public String getMineralid() {
		return mineralid;
	}


	public void setMineralid(String mineralid) {
		this.mineralid = mineralid;
	}


	public String getDeposidid() {
		return deposidid;
	}


	public void setDeposidid(String deposidid) {
		this.deposidid = deposidid;
	}


	public String getCountries() {
		return countries;
	}


	public void setCountries(String countries) {
		this.countries = countries;
	}


	public String getAppdate() {
		return appdate;
	}


	public void setAppdate(String appdate) {
		this.appdate = appdate;
	}


	public String getYearcapacity() {
		return yearcapacity;
	}


	public void setYearcapacity(String yearcapacity) {
		this.yearcapacity = yearcapacity;
	}


	public String getWorkyear() {
		return workyear;
	}


	public void setWorkyear(String workyear) {
		this.workyear = workyear;
	}


	public String getMinetypeid() {
		return minetypeid;
	}


	public void setMinetypeid(String minetypeid) {
		this.minetypeid = minetypeid;
	}


	public String getKomissid() {
		return komissid;
	}


	public void setKomissid(String komissid) {
		this.komissid = komissid;
	}


	public String getKomissdate() {
		return komissdate;
	}


	public void setKomissdate(String komissdate) {
		this.komissdate = komissdate;
	}


	public String getKomissakt() {
		return komissakt;
	}


	public void setKomissakt(String komissakt) {
		this.komissakt = komissakt;
	}


	public String getBombid() {
		return bombid;
	}


	public void setBombid(String bombid) {
		this.bombid = bombid;
	}

	public String getLpName() {
		return lpName;
	}


	public void setLpName(String lpName) {
		this.lpName = lpName;
	}


	public String getLicenseXB() {
		return licenseXB;
	}


	public void setLicenseXB(String licenseXB) {
		this.licenseXB = licenseXB;
	}

		
	public String getStatebudgetid() {
		return statebudgetid;
	}


	public void setStatebudgetid(String statebudgetid) {
		this.statebudgetid = statebudgetid;
	}


	public String getConcetrate() {
		return concetrate;
	}


	public void setConcetrate(String concetrate) {
		this.concetrate = concetrate;
	}


	public String getStartdate() {
		return startdate;
	}


	public void setStartdate(String startdate) {
		this.startdate = startdate;
	}


	public String getBombtype() {
		return bombtype;
	}


	public void setBombtype(String bombtype) {
		this.bombtype = bombtype;
	}


	@Override
	public String toString() {
		return "DataMinPlan1 [id=" + id + ", data1=" + data1 + ", data2=" + data2 + ", data3=" + data3 + ", data4="
				+ data4 + ", data5=" + data5 + ", data6=" + data6 + ", data7=" + data7 + ", data8=" + data8 + ", data9="
				+ data9 + ", noteid=" + noteid + ", planid=" + planid + ", dataIndex=" + dataIndex + "]";
	}

}
