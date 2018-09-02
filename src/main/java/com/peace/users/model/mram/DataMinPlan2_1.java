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
@Table(name="DATA_MIN_PLAN_2_1")
@NamedQuery(name="DataMinPlan2_1.findAll", query="SELECT d FROM DataMinPlan2_1 d")
public class DataMinPlan2_1 implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="DATA_MIN_PLAN_2_1_SEQ", sequenceName="DATA_MIN_PLAN_2_1_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE ,generator="DATA_MIN_PLAN_2_1_SEQ")
	@Column(name = "ID", unique = true, nullable = false, precision = 22, scale = 0)
	private long id;

	@Override
	public String toString() {
		return "DATA_MIN_PLAN_2_1 [id=" + id + ", data1=" + data1 + ", data10=" + data10 + ", data11=" + data11
				+ ", data12=" + data12 + ", data13=" + data13 + ", data14=" + data14 + ", data15=" + data15
				+ ", data16=" + data16 + ", data17=" + data17 + ", data18=" + data18 + ", data2=" + data2 + ", data3="
				+ data3 + ", data4=" + data4 + ", data5=" + data5 + ", data6=" + data6 + ", data7=" + data7 + ", data8="
				+ data8 + ", data9=" + data9 + ", noteid=" + noteid + ", planid=" + planid + ", type=" + type
				+ ", dataIndex=" + dataIndex + ", annualRegistration=" + annualRegistration + "]";
	}

	private String data1;

	private String data10;

	private String data11;

	private String data12;

	private String data13;

	private String data14;

	private Double data15;

	private Double data16;

	private String data17;

	private Double data18;

	private String data19, data21, data23;
	
	private Double data20, data22, data24;

	private Double data2;

	private String data3;

	private String data4;

	private String data5;

	private String data6;

	private String data7;

	private String data8;

	private String data9;

	private Long noteid;
	
	private Long planid;
	
	private Long type;
	
	private Long dataIndex;
	
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

	public DataMinPlan2_1() {
	}

	public long getId() {
		return this.id;
	}

	public Long getType() {
		return type;
	}

	public void setType(Long type) {
		this.type = type;
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

	public String getData10() {
		return this.data10;
	}

	public void setData10(String data10) {
		this.data10 = data10;
	}

	public String getData11() {
		return this.data11;
	}

	public void setData11(String data11) {
		this.data11 = data11;
	}

	public String getData12() {
		return this.data12;
	}

	public void setData12(String data12) {
		this.data12 = data12;
	}

	public String getData13() {
		return this.data13;
	}

	public void setData13(String data13) {
		this.data13 = data13;
	}

	public String getData14() {
		return this.data14;
	}

	public void setData14(String data14) {
		this.data14 = data14;
	}

	public Double getData15() {
		return this.data15;
	}

	public void setData15(Double data15) {
		this.data15 = data15;
	}

	public Double getData16() {
		return this.data16;
	}

	public void setData16(Double data16) {
		this.data16 = data16;
	}

	public String getData17() {
		return this.data17;
	}

	public void setData17(String data17) {
		this.data17 = data17;
	}

	public Double getData18() {
		return this.data18;
	}

	public void setData18(Double data18) {
		this.data18 = data18;
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

	public String getData6() {
		return this.data6;
	}

	public void setData6(String data6) {
		this.data6 = data6;
	}

	public String getData7() {
		return this.data7;
	}

	public void setData7(String data7) {
		this.data7 = data7;
	}

	public String getData8() {
		return this.data8;
	}

	public void setData8(String data8) {
		this.data8 = data8;
	}

	public String getData9() {
		return this.data9;
	}

	public void setData9(String data9) {
		this.data9 = data9;
	}

	public Long getNoteid() {
		return this.noteid;
	}

	public void setNoteid(Long long1) {
		this.noteid = long1;
	}

	public Double getData2() {
		return data2;
	}

	public void setData2(Double data2) {
		this.data2 = data2;
	}

	public String getData3() {
		return data3;
	}

	public void setData3(String data3) {
		this.data3 = data3;
	}

	public Long getDataIndex() {
		return dataIndex;
	}

	public void setDataIndex(Long dataIndex) {
		this.dataIndex = dataIndex;
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
	
	public String getData19() {
		return data19;
	}

	public void setData19(String data19) {
		this.data19 = data19;
	}

	public Double getData20() {
		return data20;
	}

	public void setData20(Double data20) {
		this.data20 = data20;
	}

	public String getData21() {
		return data21;
	}

	public void setData21(String data21) {
		this.data21 = data21;
	}

	public Double getData22() {
		return data22;
	}

	public void setData22(Double data22) {
		this.data22 = data22;
	}

	public String getData23() {
		return data23;
	}

	public void setData23(String data23) {
		this.data23 = data23;
	}

	public Double getData24() {
		return data24;
	}

	public void setData24(Double data24) {
		this.data24 = data24;
	}

}