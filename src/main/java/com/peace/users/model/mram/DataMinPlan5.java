package com.peace.users.model.mram;

import java.io.Serializable;
import java.lang.reflect.Field;

import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the DATA_MIN_PLAN_5 database table.
 * 
 */
@Entity
@Table(name="DATA_MIN_PLAN_5")
@NamedQuery(name="DataMinPlan5.findAll", query="SELECT d FROM DataMinPlan5 d")
public class DataMinPlan5 implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="DATA_MIN_PLAN_5_SEQ", sequenceName="DATA_MIN_PLAN_5_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE ,generator="DATA_MIN_PLAN_5_SEQ")
	@Column(name = "ID", unique = true, nullable = false, precision = 22, scale = 0)
	private long id;

	private String data1;

	private String data2;

	private String data3;

	private String data4;

	private String data5;

	private String data6;

	private String data7;
	
	private String data8;
	
	private String dataindex;

	private Long noteid;
	
	private Long planid;
	
	private Boolean istodotgol;

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

	public DataMinPlan5() {
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

	public String getData1() {
		return this.data1;
	}

	public void setData1(String data1) {
		this.data1 = data1;
	}

	public String getData2() {
		return this.data2;
	}

	public void setData2(String data2) {
		this.data2 = data2;
	}

	public String getData3() {
		return this.data3;
	}

	public void setData3(String data3) {
		this.data3 = data3;
	}

	public String getData4() {
		return this.data4;
	}

	public void setData4(String data4) {
		this.data4 = data4;
	}

	public String getData5() {
		return this.data5;
	}

	public void setData5(String data5) {
		this.data5 = data5;
	}

	public String getData7() {
		return this.data7;
	}

	public void setData7(String data7) {
		this.data7 = data7;
	}

	public String getData6() {
		return data6;
	}

	public void setData6(String data6) {
		this.data6 = data6;
	}

	public String getData8() {
		return data8;
	}

	public void setData8(String data8) {
		this.data8 = data8;
	}

	public String getDataindex() {
		return dataindex;
	}

	public void setDataindex(String dataindex) {
		this.dataindex = dataindex;
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

}