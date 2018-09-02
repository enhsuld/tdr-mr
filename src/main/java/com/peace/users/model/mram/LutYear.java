package com.peace.users.model.mram;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "LUT_YEARS")
public class LutYear implements java.io.Serializable {

	private Long id;
	private int value;
	private Boolean isactive;
	private Long type;
	private Long divisionid;

	public LutYear() {
	}

	@Id
	@SequenceGenerator(name="LUT_YEARS_SEQ", sequenceName="LUT_YEARS_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE ,generator="LUT_YEARS_SEQ")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public Boolean getIsactive() {
		return isactive;
	}

	public void setIsactive(Boolean isactive) {
		this.isactive = isactive;
	}

	public Long getType() {
		return type;
	}

	public void setType(Long type) {
		this.type = type;
	}

	public Long getDivisionid() {
		return divisionid;
	}

	public void setDivisionid(Long divisionid) {
		this.divisionid = divisionid;
	}

	
}
