package com.peace.users.model.mram;
// Generated Nov 25, 2015 5:34:04 PM by Hibernate Tools 4.3.1.Final

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * LutReserveLevel generated by hbm2java
 */
@Entity
@Table(name = "LUT_RESERVE_LEVEL")
public class LutReserveLevel implements java.io.Serializable {

	private Long id;
	private String levelName;

	public LutReserveLevel() {
	}

	public LutReserveLevel(Long id) {
		this.id = id;
	}

	public LutReserveLevel(Long id, String levelName) {
		this.id = id;
		this.levelName = levelName;
	}

	@Id
	@SequenceGenerator(name="LUT_RESERVE_LEVEL_SEQ", sequenceName="LUT_RESERVE_LEVEL_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE ,generator="LUT_RESERVE_LEVEL_SEQ")
	@Column(name = "LevelID", unique = true, nullable = false, precision = 22, scale = 0)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	@Column(name = "LevelName", length = 50)
	public String getLevelName() {
		return this.levelName;
	}
	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}

}
