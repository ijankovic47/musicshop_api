package com.musicshop.property;

public class PropertyDto {

	private Integer id;
	private String name;
	private Long instrumentCount;
	private Integer typeId;
	
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
	public Integer getTypeId() {
		return typeId;
	}
	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}
	public Long getInstrumentCount() {
		return instrumentCount;
	}
	public void setInstrumentCount(Long instrumentCount) {
		this.instrumentCount = instrumentCount;
	}
	
}
