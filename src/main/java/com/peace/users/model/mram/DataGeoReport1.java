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
@Table(name="DATA_GEO_REPORT_1")
@NamedQuery(name="DataGeoReport1.findAll", query="SELECT d FROM DataGeoReport1 d")
public class DataGeoReport1 implements Serializable {

	@Override
	public String toString() {
		return "DataGeoReport1 [id=" + id + ", planid=" + planid + ", dataIndex=" + dataIndex + ", data1=" + data1
				+ ", data2=" + data2 + ", data3=" + data3 + ", data4=" + data4 + ", data5=" + data5 + ", data6=" + data6
				+ ", data7=" + data7 + ", noteid=" + noteid + ", annualRegistration=" + annualRegistration + "]";
	}


	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="DATA_GEO_REPORT_1_SEQ", sequenceName="DATA_GEO_REPORT_1_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE ,generator="DATA_GEO_REPORT_1_SEQ")
	@Column(name = "ID", unique = true, nullable = false, precision = 22, scale = 0)
	private Long id;
	private Long planid;
	private Integer dataIndex;
	private String data1;
	private String data2;
	private Double data3;
	private Double data4;
	private Double data5;
	private Double data6;
	private Double data7;
	private Long noteid;
	private String type;
	
	private Boolean istodotgol;

	public Boolean getIstodotgol() {
		return istodotgol;
	}


	public void setIstodotgol(Boolean istodotgol) {
		this.istodotgol = istodotgol;
	}

	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	//bi-directional many-to-one association to AnnualRegistration
	@ManyToOne
	@JoinColumn(name="planid", insertable=false, updatable=false)
	private AnnualRegistration annualRegistration;

	public DataGeoReport1() {
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


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public Long getPlanid() {
		return planid;
	}


	public void setPlanid(Long planid) {
		this.planid = planid;
	}


	public Integer getDataIndex() {
		return dataIndex;
	}


	public void setDataIndex(Integer dataIndex) {
		this.dataIndex = dataIndex;
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


	public Double getData3() {
		return data3;
	}


	public void setData3(Double data3) {
		this.data3 = data3;
	}


	public Double getData4() {
		return data4;
	}


	public void setData4(Double data4) {
		this.data4 = data4;
	}


	public Double getData5() {
		return data5;
	}


	public void setData5(Double data5) {
		this.data5 = data5;
	}


	public Double getData6() {
		return data6;
	}


	public void setData6(Double data6) {
		this.data6 = data6;
	}


	public Double getData7() {
		return data7;
	}


	public void setData7(Double data7) {
		this.data7 = data7;
	}


	public Long getNoteid() {
		return noteid;
	}


	public void setNoteid(Long noteid) {
		this.noteid = noteid;
	}

}