package com.peace.users.model.mram;
// Generated Feb 25, 2016 4:45:04 PM by Hibernate Tools 4.3.1.Final

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * DataGeoForm4 generated by hbm2java
 */
@Entity
@Table(name = "DATA_GEO_FORM_4")
public class DataGeoForm4 implements java.io.Serializable {

	private Long id;
	private Long planid;
	private AnnualRegistration annualRegistration;
	private String data1;
	private String data2;
	private String data3;
	private String data4;
	private String data5;
	private String data6;
	private String data7;

	public DataGeoForm4() {
	}

	public DataGeoForm4(Long id) {
		this.id = id;
	}

	public DataGeoForm4(Long id, AnnualRegistration annualRegistration, String data1, String data2, String data3,
			String data4, String data5, String data6, String data7) {
		this.id = id;
		this.annualRegistration = annualRegistration;
		this.data1 = data1;
		this.data2 = data2;
		this.data3 = data3;
		this.data4 = data4;
		this.data5 = data5;
		this.data6 = data6;
		this.data7 = data7;
	}

	@Id

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
	@JoinColumn(name = "PLANID", updatable=false, insertable=false,nullable=false)
	public AnnualRegistration getAnnualRegistration() {
		return this.annualRegistration;
	}

	public void setAnnualRegistration(AnnualRegistration annualRegistration) {
		this.annualRegistration = annualRegistration;
	}

	@Column(name = "DATA1")
	public String getData1() {
		return this.data1;
	}

	public void setData1(String data1) {
		this.data1 = data1;
	}

	@Column(name = "DATA2", length = 4000)
	public String getData2() {
		return this.data2;
	}

	public void setData2(String data2) {
		this.data2 = data2;
	}

	@Column(name = "DATA3", length = 4000)
	public String getData3() {
		return this.data3;
	}

	public void setData3(String data3) {
		this.data3 = data3;
	}

	@Column(name = "DATA4", length = 4000)
	public String getData4() {
		return this.data4;
	}

	public void setData4(String data4) {
		this.data4 = data4;
	}

	@Column(name = "DATA5", length = 4000)
	public String getData5() {
		return this.data5;
	}

	public void setData5(String data5) {
		this.data5 = data5;
	}

	@Column(name = "DATA6", length = 4000)
	public String getData6() {
		return this.data6;
	}

	public void setData6(String data6) {
		this.data6 = data6;
	}

	@Column(name = "DATA7", length = 4000)
	public String getData7() {
		return this.data7;
	}

	public void setData7(String data7) {
		this.data7 = data7;
	}

}
