package com.peace.users.model.mram;

import java.io.Serializable;
import java.lang.reflect.Field;

import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the WEEKLY_MAIN_DATA database table.
 * 
 */
@Entity
@Table(name="WEEKLY_MAIN_DATA")
@NamedQuery(name="WeeklyMainData.findAll", query="SELECT w FROM WeeklyMainData w")
public class WeeklyMainData implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name="WEEKLY_REGISTRATION_SEQ", sequenceName="WEEKLY_REGISTRATION_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE ,generator="WEEKLY_REGISTRATION_SEQ")	
	@Column(name = "ID", unique = true,  nullable = false)
	private long id;

	private double data1;

	private double data10;

	private double data11;

	private double data12;

	private double data13;

	private double data14;

	private double data15;

	private double data16;

	private double data17;

	private double data18;

	private double data19;

	private double data2;

	private double data20;

	private double data21;

	private double data22;

	private double data23;

	private double data24;

	private double data25;

	private double data26;

	private double data27;

	private double data28;

	private double data29;

	private double data3;

	private double data30;

	private double data31;

	private double data32;

	private double data33;

	private double data34;

	private double data35;

	private double data36;

	private double data37;

	private double data38;

	private double data39;

	private double data4;

	private double data40;

	private double data41;

	private double data42;

	private double data43;

	private double data44;

	private double data45;

	private double data46;

	private double data47;

	private double data48;

	private double data49;

	private double data5;

	private double data50;

	private double data51;

	private double data52;

	private double data53;

	private double data54;

	private double data55;

	private double data56;

	private double data57;

	private double data58;

	private double data59;

	private double data6;

	private double data60;

	private double data61;

	private double data62;

	private double data63;

	private double data64;

	private double data65;

	private double data66;

	private double data67;

	private double data68;

	private double data69;

	private double data7;

	private double data70;

	private double data71;

	private double data72;

	private double data73;

	private double data74;

	private double data75;

	private double data8;

	private double data9;

	private double execution;

	private long groupid;

	@Column(name="\"INDICATOR\"")
	private String indicator;

	private String measurement;

	private Long planid;

	private double wpercentage;

	public WeeklyMainData() {
	}

	
	public void setField(String aFieldName, Object aValue) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		Field aField = getClass().getDeclaredField(aFieldName);
		aField.set(this, aValue);
    }
	
	public double getField(String aFieldName, Object aValue) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		Field aField = getClass().getDeclaredField(aFieldName);
		return aField.getDouble(aValue);
    }
	
	
	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public double getData1() {
		return this.data1;
	}

	public void setData1(double data1) {
		this.data1 = data1;
	}

	public double getData10() {
		return this.data10;
	}

	public void setData10(double data10) {
		this.data10 = data10;
	}

	public double getData11() {
		return this.data11;
	}

	public void setData11(double data11) {
		this.data11 = data11;
	}

	public double getData12() {
		return this.data12;
	}

	public void setData12(double data12) {
		this.data12 = data12;
	}

	public double getData13() {
		return this.data13;
	}

	public void setData13(double data13) {
		this.data13 = data13;
	}

	public double getData14() {
		return this.data14;
	}

	public void setData14(double data14) {
		this.data14 = data14;
	}

	public double getData15() {
		return this.data15;
	}

	public void setData15(double data15) {
		this.data15 = data15;
	}

	public double getData16() {
		return this.data16;
	}

	public void setData16(double data16) {
		this.data16 = data16;
	}

	public double getData17() {
		return this.data17;
	}

	public void setData17(double data17) {
		this.data17 = data17;
	}

	public double getData18() {
		return this.data18;
	}

	public void setData18(double data18) {
		this.data18 = data18;
	}

	public double getData19() {
		return this.data19;
	}

	public void setData19(double data19) {
		this.data19 = data19;
	}

	public double getData2() {
		return this.data2;
	}

	public void setData2(double data2) {
		this.data2 = data2;
	}

	public double getData20() {
		return this.data20;
	}

	public void setData20(double data20) {
		this.data20 = data20;
	}

	public double getData21() {
		return this.data21;
	}

	public void setData21(double data21) {
		this.data21 = data21;
	}

	public double getData22() {
		return this.data22;
	}

	public void setData22(double data22) {
		this.data22 = data22;
	}

	public double getData23() {
		return this.data23;
	}

	public void setData23(double data23) {
		this.data23 = data23;
	}

	public double getData24() {
		return this.data24;
	}

	public void setData24(double data24) {
		this.data24 = data24;
	}

	public double getData25() {
		return this.data25;
	}

	public void setData25(double data25) {
		this.data25 = data25;
	}

	public double getData26() {
		return this.data26;
	}

	public void setData26(double data26) {
		this.data26 = data26;
	}

	public double getData27() {
		return this.data27;
	}

	public void setData27(double data27) {
		this.data27 = data27;
	}

	public double getData28() {
		return this.data28;
	}

	public void setData28(double data28) {
		this.data28 = data28;
	}

	public double getData29() {
		return this.data29;
	}

	public void setData29(double data29) {
		this.data29 = data29;
	}

	public double getData3() {
		return this.data3;
	}

	public void setData3(double data3) {
		this.data3 = data3;
	}

	public double getData30() {
		return this.data30;
	}

	public void setData30(double data30) {
		this.data30 = data30;
	}

	public double getData31() {
		return this.data31;
	}

	public void setData31(double data31) {
		this.data31 = data31;
	}

	public double getData32() {
		return this.data32;
	}

	public void setData32(double data32) {
		this.data32 = data32;
	}

	public double getData33() {
		return this.data33;
	}

	public void setData33(double data33) {
		this.data33 = data33;
	}

	public double getData34() {
		return this.data34;
	}

	public void setData34(double data34) {
		this.data34 = data34;
	}

	public double getData35() {
		return this.data35;
	}

	public void setData35(double data35) {
		this.data35 = data35;
	}

	public double getData36() {
		return this.data36;
	}

	public void setData36(double data36) {
		this.data36 = data36;
	}

	public double getData37() {
		return this.data37;
	}

	public void setData37(double data37) {
		this.data37 = data37;
	}

	public double getData38() {
		return this.data38;
	}

	public void setData38(double data38) {
		this.data38 = data38;
	}

	public double getData39() {
		return this.data39;
	}

	public void setData39(double data39) {
		this.data39 = data39;
	}

	public double getData4() {
		return this.data4;
	}

	public void setData4(double data4) {
		this.data4 = data4;
	}

	public double getData40() {
		return this.data40;
	}

	public void setData40(double data40) {
		this.data40 = data40;
	}

	public double getData41() {
		return this.data41;
	}

	public void setData41(double data41) {
		this.data41 = data41;
	}

	public double getData42() {
		return this.data42;
	}

	public void setData42(double data42) {
		this.data42 = data42;
	}

	public double getData43() {
		return this.data43;
	}

	public void setData43(double data43) {
		this.data43 = data43;
	}

	public double getData44() {
		return this.data44;
	}

	public void setData44(double data44) {
		this.data44 = data44;
	}

	public double getData45() {
		return this.data45;
	}

	public void setData45(double data45) {
		this.data45 = data45;
	}

	public double getData46() {
		return this.data46;
	}

	public void setData46(double data46) {
		this.data46 = data46;
	}

	public double getData47() {
		return this.data47;
	}

	public void setData47(double data47) {
		this.data47 = data47;
	}

	public double getData48() {
		return this.data48;
	}

	public void setData48(double data48) {
		this.data48 = data48;
	}

	public double getData49() {
		return this.data49;
	}

	public void setData49(double data49) {
		this.data49 = data49;
	}

	public double getData5() {
		return this.data5;
	}

	public void setData5(double data5) {
		this.data5 = data5;
	}

	public double getData50() {
		return this.data50;
	}

	public void setData50(double data50) {
		this.data50 = data50;
	}

	public double getData51() {
		return this.data51;
	}

	public void setData51(double data51) {
		this.data51 = data51;
	}

	public double getData52() {
		return this.data52;
	}

	public void setData52(double data52) {
		this.data52 = data52;
	}

	public double getData53() {
		return this.data53;
	}

	public void setData53(double data53) {
		this.data53 = data53;
	}

	public double getData54() {
		return this.data54;
	}

	public void setData54(double data54) {
		this.data54 = data54;
	}

	public double getData55() {
		return this.data55;
	}

	public void setData55(double data55) {
		this.data55 = data55;
	}

	public double getData56() {
		return this.data56;
	}

	public void setData56(double data56) {
		this.data56 = data56;
	}

	public double getData57() {
		return this.data57;
	}

	public void setData57(double data57) {
		this.data57 = data57;
	}

	public double getData58() {
		return this.data58;
	}

	public void setData58(double data58) {
		this.data58 = data58;
	}

	public double getData59() {
		return this.data59;
	}

	public void setData59(double data59) {
		this.data59 = data59;
	}

	public double getData6() {
		return this.data6;
	}

	public void setData6(double data6) {
		this.data6 = data6;
	}

	public double getData60() {
		return this.data60;
	}

	public void setData60(double data60) {
		this.data60 = data60;
	}

	public double getData61() {
		return this.data61;
	}

	public void setData61(double data61) {
		this.data61 = data61;
	}

	public double getData62() {
		return this.data62;
	}

	public void setData62(double data62) {
		this.data62 = data62;
	}

	public double getData63() {
		return this.data63;
	}

	public void setData63(double data63) {
		this.data63 = data63;
	}

	public double getData64() {
		return this.data64;
	}

	public void setData64(double data64) {
		this.data64 = data64;
	}

	public double getData65() {
		return this.data65;
	}

	public void setData65(double data65) {
		this.data65 = data65;
	}

	public double getData66() {
		return this.data66;
	}

	public void setData66(double data66) {
		this.data66 = data66;
	}

	public double getData67() {
		return this.data67;
	}

	public void setData67(double data67) {
		this.data67 = data67;
	}

	public double getData68() {
		return this.data68;
	}

	public void setData68(double data68) {
		this.data68 = data68;
	}

	public double getData69() {
		return this.data69;
	}

	public void setData69(double data69) {
		this.data69 = data69;
	}

	public double getData7() {
		return this.data7;
	}

	public void setData7(double data7) {
		this.data7 = data7;
	}

	public double getData70() {
		return this.data70;
	}

	public void setData70(double data70) {
		this.data70 = data70;
	}

	public double getData71() {
		return this.data71;
	}

	public void setData71(double data71) {
		this.data71 = data71;
	}

	public double getData72() {
		return this.data72;
	}

	public void setData72(double data72) {
		this.data72 = data72;
	}

	public double getData73() {
		return this.data73;
	}

	public void setData73(double data73) {
		this.data73 = data73;
	}

	public double getData74() {
		return this.data74;
	}

	public void setData74(double data74) {
		this.data74 = data74;
	}

	public double getData75() {
		return this.data75;
	}

	public void setData75(double data75) {
		this.data75 = data75;
	}

	public double getData8() {
		return this.data8;
	}

	public void setData8(double data8) {
		this.data8 = data8;
	}

	public double getData9() {
		return this.data9;
	}

	public void setData9(double data9) {
		this.data9 = data9;
	}

	public double getExecution() {
		return this.execution;
	}

	public void setExecution(double execution) {
		this.execution = execution;
	}

	public long getGroupid() {
		return this.groupid;
	}

	public void setGroupid(long groupid) {
		this.groupid = groupid;
	}

	public String getIndicator() {
		return this.indicator;
	}

	public void setIndicator(String indicator) {
		this.indicator = indicator;
	}

	public String getMeasurement() {
		return this.measurement;
	}

	public void setMeasurement(String measurement) {
		this.measurement = measurement;
	}

	public Long getPlanid() {
		return this.planid;
	}

	public void setPlanid(Long planid) {
		this.planid = planid;
	}

	public double getWpercentage() {
		return this.wpercentage;
	}

	public void setWpercentage(double wpercentage) {
		this.wpercentage = wpercentage;
	}

}