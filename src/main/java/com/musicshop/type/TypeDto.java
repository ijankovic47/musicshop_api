package com.musicshop.type;

public class TypeDto {

	private Integer id;
	private String name;
	private String image;
	private Integer familyId;
	private Long instrumentCount;

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

	public Integer getFamilyId() {
		return familyId;
	}

	public void setFamilyId(Integer familyId) {
		this.familyId = familyId;
	}

	public Long getInstrumentCount() {
		return instrumentCount;
	}

	public void setInstrumentCount(Long instrumentCount) {
		this.instrumentCount = instrumentCount;
	}

}
