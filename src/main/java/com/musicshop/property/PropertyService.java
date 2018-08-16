package com.musicshop.property;

import java.util.List;

public interface PropertyService {

	List<PropertyDto> read(Integer typeId, Integer brandId, Double priceMin, Double priceMax, boolean havingInstruments);
	Integer create(PropertyDto property);
}
