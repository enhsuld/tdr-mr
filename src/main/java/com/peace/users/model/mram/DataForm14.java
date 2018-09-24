package com.peace.users.model.mram;
// Generated Feb 25, 2016 4:45:04 PM by Hibernate Tools 4.3.1.Final

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
 * DataForm14 generated by hbm2java
 */
@Entity
@Table(name = "DATA_FORM_14")
public class DataForm14 implements java.io.Serializable {

	private Long id;
	private Long planid;
	private AnnualRegistration annualRegistration;
	private String data1;
	private String data2;
	private String data3;
	private Integer data4;
	private String data5;

	public DataForm14() {
	}

	public DataForm14(Long id) {
		this.id = id;
	}

	public DataForm14(Long id, AnnualRegistration annualRegistration, String data1, String data2, String data3,
			Integer data4, String data5) {
		this.id = id;
		this.annualRegistration = annualRegistration;
		this.data1 = data1;
		this.data2 = data2;
		this.data3 = data3;
		this.data4 = data4;
		this.data5 = data5;
	}

	@Id 
	@SequenceGenerator(name="DATA_FORM_14_SEQ", sequenceName="DATA_FORM_14_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE ,generator="DATA_FORM_14_SEQ")
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

	@Column(name = "DATA3")
	public String getData3() {
		return this.data3;
	}

	public void setData3(String data3) {
		this.data3 = data3;
	}

	@Column(name = "DATA4", precision = 20, scale = 0)
	public Integer getData4() {
		return this.data4;
	}

	public void setData4(Integer data4) {
		this.data4 = data4;
	}

	@Column(name = "DATA5", length = 4000)
	public String getData5() {
		return this.data5;
	}

	public void setData5(String data5) {
		this.data5 = data5;
	}

}