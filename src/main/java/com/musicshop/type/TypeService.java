package com.musicshop.type;

import java.util.List;
import java.util.Optional;

public interface TypeService {

	Integer create(TypeDto type);
	Optional<TypeDto> readById(Integer id);
	List<TypeDto> read(Integer familyId, Integer brandId, Double priceMin, Double priceMax, boolean havingInstruments);
}
