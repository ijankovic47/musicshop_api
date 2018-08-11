package com.musicshop.family;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;

import com.musicshop.type.Type;

@Entity
public class Family {

	@Id
	@GeneratedValue (strategy=GenerationType.SEQUENCE, generator="FAMILY_SEQ")
	@SequenceGenerator(name="FAMILY_SEQ", sequenceName="FAMILY_SEQ", allocationSize=1)
	private Integer id;
	private String name;
	@OneToMany(fetch=FetchType.LAZY, mappedBy="family" )
	private List<Type> types;
	@Transient
	private Long instrumentCount;
	
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
	public List<Type> getTypes() {
		return types;
	}
	public void setTypes(List<Type> types) {
		this.types = types;
	}
	public Long getInstrumentCount() {
		return instrumentCount;
	}
	public void setInstrumentCount(Long instrumentCount) {
		this.instrumentCount = instrumentCount;
	}
}
