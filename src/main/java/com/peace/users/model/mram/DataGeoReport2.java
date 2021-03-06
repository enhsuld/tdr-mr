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
 * DataGeoReport2 generated by hbm2java
 */
@Entity
@Table(name = "DATA_GEO_REPORT_2")
public class DataGeoReport2 implements java.io.Serializable {

	private Long id;
	private Long planid;
	private Long noteid;
	private AnnualRegistration annualRegistration;
	private String data1;
	private String data2;
	private Double data3;
	private Double data4;
	private Double data5;
	private Double data6;
	private Double data7;
	private Double data8;
	private Double data9;
	private Double data10;
	private Double data11;
	private Double data12;
	private Double data13;
	private Double data14;
	private Double data15;
	private Double data16;
	private Double data17;
	private Double data18;
	private Boolean istodotgol;

	public Boolean getIstodotgol() {
		return istodotgol;
	}


	public void setIstodotgol(Boolean istodotgol) {
		this.istodotgol = istodotgol;
	}

	public DataGeoReport2() {
	}

	public DataGeoReport2(Long id) {
		this.id = id;
	}
	
	@Id
	@SequenceGenerator(name="DATA_GEO_REPORT_2_SEQ", sequenceName="DATA_GEO_REPORT_2_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE ,generator="DATA_GEO_REPORT_2_SEQ")
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
	
	@Column(name = "NOTEID")
	public Long getNoteid() {
		return noteid;
	}

	public void setNoteid(Long noteid) {
		this.noteid = noteid;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PLANID",insertable=false,updatable=false)
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

	@Column(name = "DATA3", precision = 20, scale = 0)
	public Double getData3() {
		return this.data3;
	}

	public void setData3(Double data3) {
		this.data3 = data3;
	}

	@Column(name = "DATA4", precision = 20, scale = 0)
	public Double getData4() {
		return this.data4;
	}

	public void setData4(Double data4) {
		this.data4 = data4;
	}

	@Column(name = "DATA5", precision = 20, scale = 0)
	public Double getData5() {
		return this.data5;
	}

	public void setData5(Double data5) {
		this.data5 = data5;
	}

	@Column(name = "DATA6", precision = 20, scale = 0)
	public Double getData6() {
		return this.data6;
	}

	public void setData6(Double data6) {
		this.data6 = data6;
	}

	@Column(name = "DATA7", precision = 20, scale = 0)
	public Double getData7() {
		return this.data7;
	}

	public void setData7(Double data7) {
		this.data7 = data7;
	}
	
	public void setField(String aFieldName, Object aValue) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		Field aField = getClass().getDeclaredField(aFieldName);
		aField.set(this, aValue);
    }

	@Column(name = "DATA8", precision = 20, scale = 0)
	public Double getData8() {
		return this.data8;
	}

	public void setData8(Double data8) {
		this.data8 = data8;
	}

	@Column(name = "DATA9", precision = 20, scale = 0)
	public Double getData9() {
		return this.data9;
	}

	public void setData9(Double data9) {
		this.data9 = data9;
	}

	@Column(name = "DATA10", precision = 20, scale = 0)
	public Double getData10() {
		return this.data10;
	}

	public void setData10(Double data10) {
		this.data10 = data10;
	}

	@Column(name = "DATA11", precision = 20, scale = 0)
	public Double getData11() {
		return this.data11;
	}

	public void setData11(Double data11) {
		this.data11 = data11;
	}

	@Column(name = "DATA12", precision = 20, scale = 0)
	public Double getData12() {
		return this.data12;
	}

	public void setData12(Double data12) {
		this.data12 = data12;
	}

	@Column(name = "DATA13", precision = 20, scale = 0)
	public Double getData13() {
		return this.data13;
	}

	public void setData13(Double data13) {
		this.data13 = data13;
	}

	@Column(name = "DATA14", precision = 20, scale = 0)
	public Double getData14() {
		return this.data14;
	}

	public void setData14(Double data14) {
		this.data14 = data14;
	}

	@Column(name = "DATA15", precision = 20, scale = 0)
	public Double getData15() {
		return this.data15;
	}

	public void setData15(Double data15) {
		this.data15 = data15;
	}

	@Column(name = "DATA16", precision = 20, scale = 0)
	public Double getData16() {
		return this.data16;
	}

	public void setData16(Double data16) {
		this.data16 = data16;
	}

	@Column(name = "DATA17", precision = 20, scale = 0)
	public Double getData17() {
		return this.data17;
	}

	public void setData17(Double data17) {
		this.data17 = data17;
	}

	@Column(name = "DATA18", precision = 20, scale = 0)
	public Double getData18() {
		return this.data18;
	}

	public void setData18(Double data18) {
		this.data18 = data18;
	}

}
