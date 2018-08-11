package com.musicshop.brand;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;

@Entity
public class Brand {

	@Id
	@GeneratedValue (strategy=GenerationType.SEQUENCE, generator="BRAND_SEQ")
	@SequenceGenerator(name="BRAND_SEQ", sequenceName="BRAND_SEQ", allocationSize=1)
	private Integer id;
	private String name;
	private String image;
	@Transient
	private Long instrumentCount;
	
	public Brand() {
	}
	public Brand(Integer id, String name, String image, Long instrumentCount) {
		this.id=id;
		this.name=name;
		this.image=image;
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
	public Long getInstrumentCount() {
		return instrumentCount;
	}
	public void setInstrumentCount(Long instrumentCount) {
		this.instrumentCount = instrumentCount;
	}
	
	
}
