package com.peace.users.model.mram;

import java.io.Serializable;
import java.lang.reflect.Field;

import javax.persistence.*;

import java.util.List;

@Entity
@Table(name="DATA_EXCEL_MINREP10")
@NamedQuery(name="DataExcelMinrep10.findAll", query="SELECT d FROM DataExcelMinrep10 d")
public class DataExcelMinrep10 implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="DATA_EXCEL_MINREP10_SEQ", sequenceName="DATA_EXCEL_MINREP10_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE ,generator="DATA_EXCEL_MINREP10_SEQ")
	@Column(name = "ID", unique = true, nullable = false, precision = 22, scale = 0)
	private Long id;
	private Long planid;
	private Long noteid;
	private String data1;
	private String data2;
	private String data3;
	private Double data4;
	private Double data5;
	private String data6;
	private Double data7;
    private Double data8;
	private String data9;

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

    public Double getData11() {
        return data11;
    }

    public void setData11(Double data11) {
        this.data11 = data11;
    }

    public String getData13() {
        return data13;
    }

    public void setData13(String data13) {
        this.data13 = data13;
    }

    public Double getData21() {
        return data21;
    }

    public void setData21(Double data21) {
        this.data21 = data21;
    }

    public String getData22() {
        return data22;
    }

    public void setData22(String data22) {
        this.data22 = data22;
    }

    private Double data10;
	private Double data11;
	private String data12;
	private String data13;
	private Double data14;
	private Double data15;
	private Double data16;
	private Double data17;
	private Double data18;
	private Double data19;
	private Double data20;
	private Double data21;
	private String data22;
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

	public DataExcelMinrep10() {
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

	public Double getData7() {
		return data7;
	}


	public void setData7(Double data7) {
		this.data7 = data7;
	}


	public Double getData10() {
		return data10;
	}


	public void setData10(Double data10) {
		this.data10 = data10;
	}


	public String getData12() {
		return data12;
	}


	public void setData12(String data12) {
		this.data12 = data12;
	}


	public String getData6() {
		return data6;
	}


	public void setData6(String data6) {
		this.data6 = data6;
	}



	public Double getData14() {
		return data14;
	}


	public void setData14(Double data14) {
		this.data14 = data14;
	}


	public Double getData15() {
		return data15;
	}


	public void setData15(Double data15) {
		this.data15 = data15;
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


	public Double getData16() {
		return data16;
	}


	public void setData16(Double data16) {
		this.data16 = data16;
	}


	public Double getData17() {
		return data17;
	}


	public void setData17(Double data17) {
		this.data17 = data17;
	}


	public Double getData18() {
		return data18;
	}


	public void setData18(Double data18) {
		this.data18 = data18;
	}


	public Double getData19() {
		return data19;
	}


	public void setData19(Double data19) {
		this.data19 = data19;
	}


	public Double getData20() {
		return data20;
	}


	public void setData20(Double data20) {
		this.data20 = data20;
	}


	public void setOrdernumber(Long ordernumber) {
		this.ordernumber = ordernumber;
	}


}
