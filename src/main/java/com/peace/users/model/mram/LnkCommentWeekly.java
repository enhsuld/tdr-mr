package com.peace.users.model.mram;
// Generated Feb 17, 2016 7:05:43 AM by Hibernate Tools 4.3.1.Final
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * LnkCommentWeekly generated by hbm2java
 */
@Entity
@Table(name = "LNK_COMMENT_WEEKLY")
public class LnkCommentWeekly implements java.io.Serializable {

	private Long id;
	private WeeklyRegistration weeklyRegistration;
	private String mcomment;
	private Long userid;
	private String username;
	private String comdate;
	private Long desid;
	private Long wrid;
	public LnkCommentWeekly() {
	}

	public LnkCommentWeekly(Long id) {
		this.id = id;
	}

	public LnkCommentWeekly(Long id, WeeklyRegistration weeklyRegistration, String mcomment, Long userid,
			String username, Long desid) {
		this.id = id;
		this.weeklyRegistration = weeklyRegistration;
		this.mcomment = mcomment;
		this.userid = userid;
		this.username = username;
		this.desid = desid;
	}

	@Id
	@SequenceGenerator(name="LNK_COMMENT_WEEKLY_SEQ", sequenceName="LNK_COMMENT_WEEKLY_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE ,generator="LNK_COMMENT_WEEKLY_SEQ")
	@Column(name = "ID", unique = true, nullable = false)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	
	@Column(name = "WRID")
	public Long getWrid() {
		return wrid;
	}

	public void setWrid(Long wrid) {
		this.wrid = wrid;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "WRID", nullable=false,insertable=false,updatable=false)
	public WeeklyRegistration getWeeklyRegistration() {
		return this.weeklyRegistration;
	}

	public void setWeeklyRegistration(WeeklyRegistration weeklyRegistration) {
		this.weeklyRegistration = weeklyRegistration;
	}

	@Column(name = "MCOMMENT", length = 4000)
	public String getMcomment() {
		return this.mcomment;
	}

	public void setMcomment(String mcomment) {
		this.mcomment = mcomment;
	}

	@Column(name = "USERID")
	public Long getUserid() {
		return this.userid;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
	}
	
	
	@Column(name = "COMDATE", length = 400)
	public String getComdate() {
		return comdate;
	}

	public void setComdate(String comdate) {
		this.comdate = comdate;
	}

	@Column(name = "USERNAME", length = 400)
	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Column(name = "DESID")
	public Long getDesid() {
		return this.desid;
	}

	public void setDesid(Long desid) {
		this.desid = desid;
	}

}