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
@Table(name="DATA_MIN_PLAN_8")
@NamedQuery(name="DataMinPlan8.findAll", query="SELECT d FROM DataMinPlan8 d")
public class DataMinPlan8 implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="DATA_MIN_PLAN_8_SEQ", sequenceName="DATA_MIN_PLAN_8_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE ,generator="DATA_MIN_PLAN_8_SEQ")
	@Column(name = "ID", unique = true, nullable = false, precision = 22, scale = 0)
	private long id;

	private String data1;

	private String data2;

	private String data3;

	private String data4;

	private String data5;

	private Double data6;

	private String data7;
	private Long data8;

	private Double data9;

	private Double data10;

	private String data11;

	private Double data12;
	private Double data13;

	private Double data14;
	private String data15;

	private String data16;

	private Double total;

	private Double dataIndex;

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

	public DataMinPlan8() {
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
	
	public String getData1() {
		return data1;
	}

	public void setData1(String data1) {
		this.data1 = data1;
	}

	public String getData2() {
		return data2;
	}

	public void setData2(String data2) {
		this.data2 = data2;
	}

	public String getData3() {
		return data3;
	}

	public void setData3(String data3) {
		this.data3 = data3;
	}

	public String getData4() {
		return data4;
	}

	public void setData4(String data4) {
		this.data4 = data4;
	}

	public String getData5() {
		return data5;
	}

	public void setData5(String data5) {
		this.data5 = data5;
	}

	public Double getData6() {
		return data6;
	}

	public void setData6(Double data6) {
		this.data6 = data6;
	}

	public String getData7() {
		return data7;
	}

	public void setData7(String data7) {
		this.data7 = data7;
	}

	public Long getData8() {
		return data8;
	}

	public void setData8(Long data8) {
		this.data8 = data8;
	}

	public Double getData9() {
		return data9;
	}

	public void setData9(Double data9) {
		this.data9 = data9;
	}

	public Double getData10() {
		return data10;
	}

	public void setData10(Double data10) {
		this.data10 = data10;
	}

	public String getData11() {
		return data11;
	}

	public void setData11(String data11) {
		this.data11 = data11;
	}

	public Double getData12() {
		return data12;
	}

	public void setData12(Double data12) {
		this.data12 = data12;
	}

	public Double getData13() {
		return data13;
	}

	public void setData13(Double data13) {
		this.data13 = data13;
	}

	public Double getData14() {
		return data14;
	}

	public void setData14(Double data14) {
		this.data14 = data14;
	}

	public String getData15() {
		return data15;
	}

	public void setData15(String data15) {
		this.data15 = data15;
	}

	public String getData16() {
		return data16;
	}

	public void setData16(String data16) {
		this.data16 = data16;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	public Double getData_index() {
		return dataIndex;
	}

	public void setData_index(Double dataIndex) {
		this.dataIndex = dataIndex;
	}

	public void setField(String aFieldName, Object aValue) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		Field aField = getClass().getDeclaredField(aFieldName);
		aField.set(this, aValue);
    }

}