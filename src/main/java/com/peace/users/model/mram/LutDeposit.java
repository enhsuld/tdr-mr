package com.peace.users.model.mram;
// Generated Feb 3, 2016 7:11:36 PM by Hibernate Tools 4.3.1.Final

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

/**
 * LutDeposit generated by hbm2java
 */
@Entity
@Table(name = "LUT_DEPOSIT")
public class LutDeposit implements java.io.Serializable {

	private Long depositid;
	private LutMinerals lutMinerals;
	private LutMeasurements lutMeasurements;
	private String depositnamemon;
	private String depositnameeng;
	private Integer mineralsid;
	private Long mineraltype;	
	private int ismatter;
	private int pnum;
	
	private Set<LutProducts> lutProductses = new HashSet<LutProducts>(0);

	public LutDeposit() {
	}

	public LutDeposit(Long depositid, String depositnamemon, String depositnameeng) {
		this.depositid = depositid;
		this.depositnamemon = depositnamemon;
		this.depositnameeng = depositnameeng;
	}

	public LutDeposit(Long depositid, LutMinerals lutMinerals, LutMeasurements lutMeasurements,
			String depositnamemon, String depositnameeng, Long mineraltype, Set<LutProducts> lutProductses) {
		this.depositid = depositid;
		this.lutMinerals = lutMinerals;
		this.lutMeasurements = lutMeasurements;
		this.depositnamemon = depositnamemon;
		this.depositnameeng = depositnameeng;
		this.mineraltype = mineraltype;
		this.lutProductses = lutProductses;
	}

	@Id

	@Column(name = "DEPOSITID", unique = true, nullable = false, precision = 22, scale = 0)
	public Long getDepositid() {
		return this.depositid;
	}

	public void setDepositid(Long depositid) {
		this.depositid = depositid;
	}

	@Column(name = "ISMATTER")	
	public int getIsmatter() {
		return ismatter;
	}

	public void setIsmatter(int ismatter) {
		this.ismatter = ismatter;
	}
	
	
	
	@Column(name = "PNUM")	
	public int getPnum() {
		return pnum;
	}

	public void setPnum(int pnum) {
		this.pnum = pnum;
	}

	@Column(name = "MINERALSID")
	public Integer getMineralsid() {
		return mineralsid;
	}

	public void setMineralsid(Integer mineralsid) {
		this.mineralsid = mineralsid;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "MINERALSID",nullable = false,insertable=false,updatable=false)
	@JsonBackReference
	public LutMinerals getLutMinerals() {
		return this.lutMinerals;
	}

	public void setLutMinerals(LutMinerals lutMinerals) {
		this.lutMinerals = lutMinerals;
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

	@Column(name = "DEPOSITNAMEMON", nullable = false, length = 100)
	public String getDepositnamemon() {
		return this.depositnamemon;
	}

	public void setDepositnamemon(String depositnamemon) {
		this.depositnamemon = depositnamemon;
	}

	@Column(name = "DEPOSITNAMEENG", nullable = false, length = 100)
	public String getDepositnameeng() {
		return this.depositnameeng;
	}

	public void setDepositnameeng(String depositnameeng) {
		this.depositnameeng = depositnameeng;
	}

	@Column(name = "MINERALTYPE", precision = 22, scale = 0)
	public Long getMineraltype() {
		return this.mineraltype;
	}

	public void setMineraltype(Long mineraltype) {
		this.mineraltype = mineraltype;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "lutDeposit")
	@JsonBackReference
	public Set<LutProducts> getLutProductses() {
		return this.lutProductses;
	}

	public void setLutProductses(Set<LutProducts> lutProductses) {
		this.lutProductses = lutProductses;
	}

}
