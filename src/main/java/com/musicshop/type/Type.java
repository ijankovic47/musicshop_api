package com.musicshop.type;

import javax.persistence.Entity;
import javax.persistence.EntityResult;
import javax.persistence.FetchType;
import javax.persistence.FieldResult;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SqlResultSetMapping;
import com.musicshop.family.Family;

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
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	private String name;
	private String image;
	@ManyToOne(fetch=FetchType.EAGER)
	private Family family;
	private Integer instrumentCount;
	
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
	public Integer getInstrumentCount() {
		return instrumentCount;
	}
	public void setInstrumentCount(Integer instrumentCount) {
		this.instrumentCount = instrumentCount;
	}
}
