package com.peace.users.model.mram;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.sql.Timestamp;

import javax.persistence.*;

import java.util.Date;
import java.util.List;


/**
 * The persistent class for the DATA_MIN_PLAN_3 database table.
 * 
 */
@Entity
@Table(name="LUT_NEWS")
@NamedQuery(name="LutNews.findAll", query="SELECT d FROM LutNews d")
public class LutNews implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="LUT_NEWS_SEQ", sequenceName="LUT_NEWS_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE ,generator="LUT_NEWS_SEQ")
	@Column(name = "newsid", unique = true, nullable = false, precision = 22, scale = 0)
	private long id;
	
	private String title;
	private String description;
	private Date createdat; 
	
	private int status;
	
	public LutNews() {
		this.createdat = new Date();
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}
			
	public void setField(String aFieldName, Object aValue) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		Field aField = getClass().getDeclaredField(aFieldName);
		aField.set(this, aValue);
    }

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getCreatedat() {
		return createdat;
	}

	public void setCreatedat(Date createdat) {
		this.createdat = createdat;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
}