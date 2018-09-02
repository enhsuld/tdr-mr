package com.peace.users.model.mram;
// Generated Feb 1, 2016 5:00:30 PM by Hibernate Tools 4.3.1.Final

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * LutWeeks generated by hbm2java
 */
@Entity
@Table(name = "LUT_WEEKS")
public class LutWeeks implements java.io.Serializable {

	private Integer id;
	private String startdate;
	private String enddate;
	private String year;
	private Integer month;
	private Integer week;

	public LutWeeks() {
	}

	public LutWeeks(Integer id) {
		this.id = id;
	}

	public LutWeeks(Integer id, String startdate, String enddate, Integer month, Integer week) {
		this.id = id;
		this.startdate = startdate;
		this.enddate = enddate;
		this.month = month;
		this.week = week;
	}

	@Id

	@Column(name = "ID", unique = true, nullable = false, precision = 22, scale = 0)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "STARTDATE", length = 20)
	public String getStartdate() {
		return this.startdate;
	}

	public void setStartdate(String startdate) {
		this.startdate = startdate;
	}

	@Column(name = "ENDDATE", length = 20)
	public String getEnddate() {
		return this.enddate;
	}

	public void setEnddate(String enddate) {
		this.enddate = enddate;
	}

	@Column(name = "MONTH", precision = 22, scale = 0)
	public Integer getMonth() {
		return this.month;
	}

	public void setMonth(Integer month) {
		this.month = month;
	}

	@Column(name = "WEEK", precision = 22, scale = 0)
	public Integer getWeek() {
		return this.week;
	}

	public void setWeek(Integer week) {
		this.week = week;
	}

	@Column(name = "YEAR")
	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

}
