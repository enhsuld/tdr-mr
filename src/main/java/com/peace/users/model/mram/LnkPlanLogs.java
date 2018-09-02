package com.peace.users.model.mram;

import javax.persistence.*;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@Table(name="LNK_PLAN_LOGS")
@NamedQuery(name="LnkPlanLogs.findAll", query="SELECT d FROM LnkPlanLogs d")
public class LnkPlanLogs implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="LNK_PLAN_LOGS_SEQ", sequenceName="LNK_PLAN_LOGS_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE ,generator="LNK_PLAN_LOGS_SEQ")
	@Column(name = "ID", unique = true, nullable = false, precision = 22, scale = 0)
	private Long id;
	private Long planid, repstepid, repstatusid, rejectstep;
	private Integer reject;
	private String username;

	private Date logdate;

	public Date getLogdate() {
		return logdate;
	}

	public void setLogdate(Date logdate) {
		this.logdate = logdate;
	}

	public Long getRepstepid() {
		return repstepid;
	}

	public void setRepstepid(Long repstepid) {
		this.repstepid = repstepid;
	}

	public Long getRepstatusid() {
		return repstatusid;
	}

	public void setRepstatusid(Long repstatusid) {
		this.repstatusid = repstatusid;
	}

	public Integer getReject() {
		return reject;
	}

	public void setReject(Integer reject) {
		this.reject = reject;
	}

	public Long getRejectstep() {
		return rejectstep;
	}

	public void setRejectstep(Long rejectstep) {
		this.rejectstep = rejectstep;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	//bi-directional many-to-one association to AnnualRegistration
	@ManyToOne
	@JoinColumn(name="planid", insertable=false, updatable=false)
	private AnnualRegistration annualRegistration;

	public LnkPlanLogs() {
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

}
