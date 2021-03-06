package com.peace.users.model.mram;
// Generated Feb 4, 2016 2:31:00 PM by Hibernate Tools 4.3.1.Final

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

/**
 * LutSubProduct generated by hbm2java
 */
@Entity
@Table(name = "LUT_SUB_PRODUCT")
public class LutSubProduct implements java.io.Serializable {

	private Long subDepositid;
	private Long parentproductid;
	private LutProducts lutProducts;
	private LutMeasurements lutMeasurements;
	private String subDepositnamemon;
	private String subDepositnameeng;

	public LutSubProduct() {
	}

	public LutSubProduct(Long subDepositid, String subDepositnamemon, String subDepositnameeng) {
		this.subDepositid = subDepositid;
		this.subDepositnamemon = subDepositnamemon;
		this.subDepositnameeng = subDepositnameeng;
	}

	public LutSubProduct(Long subDepositid, LutProducts lutProducts, LutMeasurements lutMeasurements,
			String subDepositnamemon, String subDepositnameeng) {
		this.subDepositid = subDepositid;
		this.lutProducts = lutProducts;
		this.lutMeasurements = lutMeasurements;
		this.subDepositnamemon = subDepositnamemon;
		this.subDepositnameeng = subDepositnameeng;
	}

	@Id

	@Column(name = "SUB_DEPOSITID", unique = true, nullable = false, precision = 22, scale = 0)
	public Long getSubDepositid() {
		return this.subDepositid;
	}

	public void setSubDepositid(Long subDepositid) {
		this.subDepositid = subDepositid;
	}

	
	@Column(name = "PRODUCTPARENTID")
	public Long getParentproductid() {
		return parentproductid;
	}

	public void setParentproductid(Long parentproductid) {
		this.parentproductid = parentproductid;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JsonBackReference
	@JoinColumn(name = "PRODUCTPARENTID", nullable = false,insertable=false,updatable=false)	
	public LutProducts getLutProducts() {
		return this.lutProducts;
	}

	public void setLutProducts(LutProducts lutProducts) {
		this.lutProducts = lutProducts;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "MEASID")
	@JsonBackReference
	public LutMeasurements getLutMeasurements() {
		return this.lutMeasurements;
	}

	public void setLutMeasurements(LutMeasurements lutMeasurements) {
		this.lutMeasurements = lutMeasurements;
	}

	@Column(name = "SUB_DEPOSITNAMEMON", nullable = false, length = 100)
	public String getSubDepositnamemon() {
		return this.subDepositnamemon;
	}

	public void setSubDepositnamemon(String subDepositnamemon) {
		this.subDepositnamemon = subDepositnamemon;
	}

	@Column(name = "SUB_DEPOSITNAMEENG", nullable = false, length = 100)
	public String getSubDepositnameeng() {
		return this.subDepositnameeng;
	}

	public void setSubDepositnameeng(String subDepositnameeng) {
		this.subDepositnameeng = subDepositnameeng;
	}

}
