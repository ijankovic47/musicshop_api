package com.musicshop.property;

import java.util.List;

public interface PropertyService {

	List<PropertyDto> read(Integer typeId, Integer brandId, Integer priceMin, Integer priceMax, boolean havingInstruments);
	Integer create(PropertyDto property);
}
