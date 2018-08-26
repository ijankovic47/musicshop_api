package com.musicshop.family;

import java.util.List;
import java.util.Optional;

public interface FamilyService {

	List<Family> read(Integer brandId, Double priceMin, Double priceMax, boolean havingInstruments);
	Optional<Family> readById(Integer id);
	Integer create(Family family);
	void edit(Integer id, Family family);
	void delete(Integer id);
}
