package com.musicshop.type;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EntityResult;
import javax.persistence.FetchType;
import javax.persistence.FieldResult;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.Transient;

import com.musicshop.family.Family;
import com.musicshop.instrument.Instrument;
import com.musicshop.property.Property;

@Entity
@SqlResultSetMapping(name="TypeMapping",
   entities={
         @EntityResult(entityClass=Type.class, fields={ 
        @FieldResult(name="id", column="id"),
        @FieldResult(name="name", column="name"),
        @FieldResult(name="image", column="image"),
        @FieldResult(name="instrumentCount", column="instrumentCount"),
        @FieldResult(name="family", column="familyId")})
   })
public class Type {

	@Id
	@GeneratedValue (strategy=GenerationType.SEQUENCE, generator="TYPE_SEQ")
	@SequenceGenerator(name="TYPE_SEQ", sequenceName="TYPE_SEQ", allocationSize=1)
	private Integer id;
	private String name;
	private String image;
	@ManyToOne(fetch=FetchType.EAGER)
	private Family family;
	@OneToMany(fetch=FetchType.LAZY, mappedBy="type")
	private List<Instrument> instruments;
	@OneToMany(fetch=FetchType.LAZY, mappedBy="type")
	private List<Property> properties;
	@Transient
	private Long instrumentCount;
	
	public Type() {
	}
	public Type(Integer id, String name, String image, Integer familyId, Long instrumentCount) {
		this.id=id;
		this.name=name;
		this.image=image;
		Family f=new Family();
		f.setId(familyId);
		this.family=f;
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
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public Family getFamily() {
		return family;
	}
	public void setFamily(Family family) {
		this.family = family;
	}
	public List<Instrument> getInstruments() {
		return instruments;
	}
	public void setInstruments(List<Instrument> instruments) {
		this.instruments = instruments;
	}
	public Long getInstrumentCount() {
		return instrumentCount;
	}
	public void setInstrumentCount(Long instrumentCount) {
		this.instrumentCount = instrumentCount;
	}
	public List<Property> getProperties() {
		return properties;
	}
	public void setProperties(List<Property> properties) {
		this.properties = properties;
	}
}
