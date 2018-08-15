package com.musicshop.family;

import java.util.List;
import java.util.Optional;

public interface FamilyService {

	List<Family> read(Integer brandId, Integer priceMin, Integer priceMax, boolean havingInstruments);
	Optional<Family> readById(Integer id);
	Integer create(Family family);
}
