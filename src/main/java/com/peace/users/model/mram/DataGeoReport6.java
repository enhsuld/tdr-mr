package com.peace.users.model.mram;
// Generated Feb 28, 2016 2:07:34 PM by Hibernate Tools 4.3.1.Final

import java.lang.reflect.Field;

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

/**
 * DataGeoReport6 generated by hbm2java
 */
@Entity
@Table(name = "DATA_GEO_REPORT_6")
public class DataGeoReport6 implements java.io.Serializable {

	private Long id;
	private Long planid;
	private AnnualRegistration annualRegistration;
	private String data1;
	private String data2;
	private String data3;
	private String data4;
	private String data5;
	private String data6;
	private Long noteid;
	private Boolean istodotgol;

	public Boolean getIstodotgol() {
		return istodotgol;
	}


	public void setIstodotgol(Boolean istodotgol) {
		this.istodotgol = istodotgol;
	}
	@Column(name = "NOTEID")
	public Long getNoteid() {
		return noteid;
	}

	public void setNoteid(Long noteid) {
		this.noteid = noteid;
	}

	public DataGeoReport6() {
	}

	public DataGeoReport6(Long id, AnnualRegistration annualRegistration) {
		this.id = id;
		this.annualRegistration = annualRegistration;
	}

	public DataGeoReport6(Long id, AnnualRegistration annualRegistration, String data1,
			String data2, String data3, String data4, String data5, String data6) {
		this.id = id;
		this.annualRegistration = annualRegistration;
		this.data1 = data1;
		this.data2 = data2;
		this.data3 = data3;
		this.data4 = data4;
		this.data5 = data5;
		this.data6 = data6;
	}
	
	@Id
	@SequenceGenerator(name="DATAGEOREPORT6_SEQ", sequenceName="DATAGEOREPORT6_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE ,generator="DATAGEOREPORT6_SEQ")
	@Column(name = "ID", unique = true, nullable = false, precision = 22, scale = 0)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	@Column(name = "PLANID")
	public Long getPlanid() {
		return planid;
	}

	public void setPlanid(Long planid) {
		this.planid = planid;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PLANID",insertable=false,updatable=false)
	public AnnualRegistration getAnnualRegistration() {
		return this.annualRegistration;
	}

	public void setAnnualRegistration(AnnualRegistration annualRegistration) {
		this.annualRegistration = annualRegistration;
	}

	@Column(name = "DATA1", length = 100)
	public String getData1() {
		return this.data1;
	}

	public void setData1(String data1) {
		this.data1 = data1;
	}

	@Column(name = "DATA2", length = 500)
	public String getData2() {
		return this.data2;
	}

	public void setData2(String data2) {
		this.data2 = data2;
	}

	@Column(name = "DATA3", length = 100)
	public String getData3() {
		return this.data3;
	}

	public void setData3(String data3) {
		this.data3 = data3;
	}

	@Column(name = "DATA4", length = 100)
	public String getData4() {
		return this.data4;
	}

	public void setData4(String data4) {
		this.data4 = data4;
	}

	@Column(name = "DATA5", length = 100)
	public String getData5() {
		return this.data5;
	}

	public void setData5(String data5) {
		this.data5 = data5;
	}

	@Column(name = "DATA6", length = 1000)
	public String getData6() {
		return this.data6;
	}

	public void setData6(String data6) {
		this.data6 = data6;
	}
	
	public void setField(String aFieldName, Object aValue) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		Field aField = getClass().getDeclaredField(aFieldName);
		aField.set(this, aValue);
    }

}