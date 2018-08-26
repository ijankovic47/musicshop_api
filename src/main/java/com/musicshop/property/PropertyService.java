package com.musicshop.property;

import java.util.List;
import java.util.Optional;

public interface PropertyService {

	List<PropertyDto> read(Integer typeId, Integer brandId, Double priceMin, Double priceMax, boolean havingInstruments);
	Integer create(PropertyDto property);
	void edit(Integer id, PropertyDto propertyDto);
	void delete(Integer id);
	Optional<PropertyDto> readById(Integer id);
}
