package com.peace.users.model.mram;

import javax.persistence.*;
import java.io.Serializable;
import java.lang.reflect.Field;

@Entity
@Table(name="LNK_BUT_DEPOSIT")
@NamedQuery(name="LnkButDeposit.findAll", query="SELECT d FROM LnkButDeposit d")
public class LnkButDeposit implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="LNK_BUT_DEPOSIT_SEQ", sequenceName="LNK_BUT_DEPOSIT_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE ,generator="LNK_BUT_DEPOSIT_SEQ")
	@Column(name = "ID", unique = true, nullable = false, precision = 22, scale = 0)
	private Long id;
	private Long depositid;
	private Long butid;

	public LnkButDeposit() {
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

    public Long getDepositid() {
        return depositid;
    }

    public void setDepositid(Long depositid) {
        this.depositid = depositid;
    }

    public Long getButid() {
        return butid;
    }

    public void setButid(Long butid) {
        this.butid = butid;
    }
}
