package com.peace.users.model.mram;

import java.io.Serializable;
import java.lang.reflect.Field;

import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the DataGeoReport05 database table.
 * 
 */
@Entity
@Table(name="DATA_GEO_REPORT_05")
@NamedQuery(name="DataGeoReport05.findAll", query="SELECT d FROM DataGeoReport05 d")
public class DataGeoReport05 implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="DATA_GEO_REPORT_05_SEQ", sequenceName="DATA_GEO_REPORT_05_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE ,generator="DATA_GEO_REPORT_05_SEQ")
	@Column(name = "ID", unique = true, nullable = false, precision = 22, scale = 0)
	private long id;

	private String data1;

	private Double data10;

	private Double data11;

	private Double data12;
	
	private Boolean istodotgol;

	public Boolean getIstodotgol() {
		return istodotgol;
	}


	public void setIstodotgol(Boolean istodotgol) {
		this.istodotgol = istodotgol;
	}

	@Override
	public String toString() {
		return "DataGeoReport05 [id=" + id + ", data1=" + data1 + ", data10=" + data10 + ", data11=" + data11
				+ ", data12=" + data12 + ", data13=" + data13 + ", data14=" + data14 + ", data15=" + data15 + ", data2="
				+ data2 + ", data3=" + data3 + ", data4=" + data4 + ", data5=" + data5 + ", data6=" + data6 + ", data7="
				+ data7 + ", data8=" + data8 + ", data9=" + data9 + ", noteid=" + noteid + ", planid=" + planid
				+ ", type=" + type + ", dataIndex=" + dataIndex + ", annualRegistration=" + annualRegistration + "]";
	}

	private Double data13;

	private String data14;

	private String data15;

	private String data2;

	private Double data3;

	private Double data4;

	private Double data5;

	private Double data6;

	private Double data7;

	private Double data8;

	private Double data9;

	private Long noteid;
	
	private Long planid;
	
	private String type;
	
	private Long dataIndex;

	//bi-directional many-to-one association to AnnualRegistration
	@ManyToOne
	@JoinColumn(name="planid", insertable=false, updatable=false)
	private AnnualRegistration annualRegistration;

	public DataGeoReport05() {
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

	public Double getData10() {
		return this.data10;
	}

	public void setData10(Double data10) {
		this.data10 = data10;
	}

	public Double getData11() {
		return this.data11;
	}

	public void setData11(Double data11) {
		this.data11 = data11;
	}

	public Double getData12() {
		return this.data12;
	}

	public void setData12(Double data12) {
		this.data12 = data12;
	}

	public Double getData13() {
		return this.data13;
	}

	public void setData13(Double data13) {
		this.data13 = data13;
	}

	public String getData2() {
		return this.data2;
	}

	public void setData2(String data2) {
		this.data2 = data2;
	}

	public Double getData3() {
		return this.data3;
	}

	public void setData3(Double data3) {
		this.data3 = data3;
	}

	public Double getData4() {
		return this.data4;
	}

	public void setData4(Double data4) {
		this.data4 = data4;
	}

	public Double getData5() {
		return this.data5;
	}

	public void setData5(Double data5) {
		this.data5 = data5;
	}

	public Double getData6() {
		return this.data6;
	}

	public void setData6(Double data6) {
		this.data6 = data6;
	}

	public Double getData7() {
		return this.data7;
	}

	public void setData7(Double data7) {
		this.data7 = data7;
	}

	public Double getData8() {
		return this.data8;
	}

	public void setData8(Double data8) {
		this.data8 = data8;
	}

	public Double getData9() {
		return this.data9;
	}

	public void setData9(Double data9) {
		this.data9 = data9;
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

	public String getData14() {
		return data14;
	}

	public void setData14(String data14) {
		this.data14 = data14;
	}

	public String getData15() {
		return data15;
	}

	public void setData15(String data15) {
		this.data15 = data15;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Long getDataIndex() {
		return dataIndex;
	}

	public void setDataIndex(Long dataIndex) {
		this.dataIndex = dataIndex;
	}

}