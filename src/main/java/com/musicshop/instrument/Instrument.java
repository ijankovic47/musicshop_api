package com.musicshop.instrument;

import java.util.List;
import java.util.Set;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.Formula;

import com.musicshop.brand.Brand;
import com.musicshop.property.Property;
import com.musicshop.type.Type;

@Entity
public class Instrument {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "INSTRUMENT_SEQ")
	@SequenceGenerator(name = "INSTRUMENT_SEQ", sequenceName = "INSTRUMENT_SEQ", allocationSize = 1)
	private Integer id;
	private String name;
	private String description;
	@ManyToOne(fetch = FetchType.EAGER)
	private Brand brand;
	@ManyToOne(fetch = FetchType.EAGER)
	private Type type;
	@Formula("(select f.id from Family f join Type t on t.family_id=f.id join Instrument i on i.type_id=t.id where i.id=id)")
	private Long familyId;
	@ElementCollection(fetch = FetchType.EAGER)
	private Set<String> images;
	private String video;
	private double price;
//	@Formula("(select p.id from property p join instrument_property ip on ip.property_id=p.id join instrument i on i.id=ip.instrument_id where i.id=id;)")
//	private List<Integer> props;
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "Instrument_Property", joinColumns = {
			@JoinColumn(name = "INSTRUMENT_ID") }, inverseJoinColumns = { @JoinColumn(name = "PROPERTY_ID") })
	private List<Property> properties;

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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Brand getBrand() {
		return brand;
	}

	public void setBrand(Brand brand) {
		this.brand = brand;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public String getVideo() {
		return video;
	}

	public void setVideo(String video) {
		this.video = video;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public List<Property> getProperties() {
		return properties;
	}

	public void setProperties(List<Property> properties) {
		this.properties = properties;
	}

	public Long getFamilyId() {
		return familyId;
	}

	public void setFamilyId(Long familyId) {
		this.familyId = familyId;
	}

	public Set<String> getImages() {
		return images;
	}

	public void setImages(Set<String> images) {
		this.images = images;
	}

//	public List<Integer> getProps() {
//		return props;
//	}
//
//	public void setProps(List<Integer> props) {
//		this.props = props;
//	}

}
