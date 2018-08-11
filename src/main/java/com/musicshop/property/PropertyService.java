package com.musicshop.property;

import java.util.List;

public interface PropertyService {

	List<PropertyDto> read(Integer typeId, Integer brandId, Integer priceMin, Integer priceMax);
	Integer create(PropertyDto property);
}
