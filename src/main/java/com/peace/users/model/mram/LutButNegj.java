package com.peace.users.model.mram;

import javax.persistence.*;
import java.io.Serializable;
import java.lang.reflect.Field;

@Entity
@Table(name="LUT_BUT_NEGJ")
@NamedQuery(name="LutButNegj.findAll", query="SELECT d FROM LutButNegj d")
public class LutButNegj implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="LUT_BUT_NEGJ_SEQ", sequenceName="LUT_BUT_NEGJ_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE ,generator="LUT_BUT_NEGJ_SEQ")
	@Column(name = "ID", unique = true, nullable = false, precision = 22, scale = 0)
	private Long id;
	private String name;
	private String negj;
	private Long type;
	private Long negjid;

	public LutButNegj() {
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNegj() {
        return negj;
    }

    public void setNegj(String negj) {
        this.negj = negj;
    }

    public Long getType() {
        return type;
    }

    public void setType(Long type) {
        this.type = type;
    }

    public Long getNegjid() {
        return negjid;
    }

    public void setNegjid(Long negjid) {
        this.negjid = negjid;
    }
}
