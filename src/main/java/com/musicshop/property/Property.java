package com.musicshop.property;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EntityResult;
import javax.persistence.FetchType;
import javax.persistence.FieldResult;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SqlResultSetMapping;

import com.musicshop.instrument.Instrument;
import com.musicshop.type.Type;

@Entity
@SqlResultSetMapping(name="PropertyMapping",
entities={
      @EntityResult(entityClass=Property.class, fields={ 
     @FieldResult(name="id", column="id"),
     @FieldResult(name="name", column="name"),
     @FieldResult(name="instrumentCount", column="instrumentCount"),
     @FieldResult(name="type", column="typeId")})
})
public class Property {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	private String name;
	private Integer instrumentCount;
	@ManyToMany(fetch=FetchType.LAZY, mappedBy="properties")
	private List<Instrument> instruments;
	@ManyToOne(fetch=FetchType.EAGER, targetEntity=Type.class)
	private Type type;
	
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
	public Integer getInstrumentCount() {
		return instrumentCount;
	}
	public void setInstrumentCount(Integer instrumentCount) {
		this.instrumentCount = instrumentCount;
	}
	public List<Instrument> getInstruments() {
		return instruments;
	}
	public void setInstruments(List<Instrument> instruments) {
		this.instruments = instruments;
	}
	public Type getType() {
		return type;
	}
	public void setType(Type type) {
		this.type = type;
	}
}
