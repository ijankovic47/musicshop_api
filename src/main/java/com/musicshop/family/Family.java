package com.musicshop.family;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;
import org.hibernate.annotations.Formula;

@Entity
public class Family {

	@Id
	@GeneratedValue (strategy=GenerationType.SEQUENCE, generator="FAMILY_SEQ")
	@SequenceGenerator(name="FAMILY_SEQ", sequenceName="FAMILY_SEQ", allocationSize=1)
	private Integer id;
	private String name;
	@Transient
	private Long instrumentCount;
	@Formula("(select count(t.id) from Type t where t.family_id=id)")
	private Long typeCount;
	
	public Family() {
	}
	public Family(Integer id, String name, Long instrumentCount) {
		this.id=id;
		this.name=name;
		this.instrumentCount=instrumentCount;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getInstrumentCount() {
		return instrumentCount;
	}
	public void setInstrumentCount(Long instrumentCount) {
		this.instrumentCount = instrumentCount;
	}
	public Long getTypeCount() {
		return typeCount;
	}
	public void setTypeCount(Long typeCount) {
		this.typeCount = typeCount;
	}
}
