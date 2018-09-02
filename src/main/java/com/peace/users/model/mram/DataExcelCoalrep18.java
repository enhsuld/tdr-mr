package com.peace.users.model.mram;

import java.io.Serializable;
import java.lang.reflect.Field;

import javax.persistence.*;

import java.util.List;

@Entity
@Table(name="DATA_EXCEL_COALREP18")
@NamedQuery(name="DataExcelCoalrep18.findAll", query="SELECT d FROM DataExcelCoalrep18 d")
public class DataExcelCoalrep18 implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="DATA_EXCEL_COALREP18_SEQ", sequenceName="DATA_EXCEL_COALREP18_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE ,generator="DATA_EXCEL_COALREP18_SEQ")
	@Column(name = "ID", unique = true, nullable = false, precision = 22, scale = 0)
	private Long id;
	private Long planid;
	private Long noteid;
	private String data1;
	private String data2;
	private String data3;
	private String data4;
	private String data5;
	private String data6;
	private String data7;
	private Double data8;
	private String data9;
	private String data10;
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

	public DataExcelCoalrep18() {
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

	public Long getOrdernumber() {
		return ordernumber;
	}


	public void setOrdernumber(Long ordernumber) {
		this.ordernumber = ordernumber;
	}

	public String getData4() {
		return data4;
	}


	public void setData4(String data4) {
		this.data4 = data4;
	}


	public String getData7() {
		return data7;
	}


	public void setData7(String data7) {
		this.data7 = data7;
	}


	public String getData5() {
		return data5;
	}


	public void setData5(String data5) {
		this.data5 = data5;
	}


	public String getData6() {
		return data6;
	}


	public void setData6(String data6) {
		this.data6 = data6;
	}


	public Double getData8() {
		return data8;
	}


	public void setData8(Double data8) {
		this.data8 = data8;
	}


	public String getData9() {
		return data9;
	}


	public void setData9(String data9) {
		this.data9 = data9;
	}


	public String getData10() {
		return data10;
	}


	public void setData10(String data10) {
		this.data10 = data10;
	}


}
