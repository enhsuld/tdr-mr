package com.peace.users.model.mram;

import java.io.Serializable;
import java.lang.reflect.Field;

import javax.persistence.*;

import java.util.List;

@Entity
@Table(name="DATA_EXCEL_GEOREP1")
@NamedQuery(name="DataExcelGeorep1.findAll", query="SELECT d FROM DataExcelGeorep1 d")
public class DataExcelGeorep1 implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="DATA_EXCEL_GEOREP1_SEQ", sequenceName="DATA_EXCEL_GEOREP1_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE ,generator="DATA_EXCEL_GEOREP1_SEQ")
	@Column(name = "ID", unique = true, nullable = false, precision = 22, scale = 0)
	private Long id;
	private Long planid;
	private Long noteid;
	private String data1;
	private String data2;
	private String data3;
	private String data4;
	private Double data5;
	private Double data6;
	private Double data7;
	private Double data8;
	private Double data9;
	private Long ordernumber;
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

	public DataExcelGeorep1() {
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

	public Double getData5() {
		return data5;
	}


	public void setData5(Double data5) {
		this.data5 = data5;
	}

	public Long getNoteid() {
		return noteid;
	}


	public void setNoteid(Long noteid) {
		this.noteid = noteid;
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


	public Double getData8() {
		return data8;
	}


	public void setData8(Double data8) {
		this.data8 = data8;
	}


	public Double getData9() {
		return data9;
	}


	public void setData9(Double data9) {
		this.data9 = data9;
	}


	public Long getOrdernumber() {
		return ordernumber;
	}


	public void setOrdernumber(Long ordernumber) {
		this.ordernumber = ordernumber;
	}

}
