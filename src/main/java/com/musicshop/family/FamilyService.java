package com.musicshop.family;

import java.util.List;
import java.util.Optional;

public interface FamilyService {

	List<Family> read(Integer brandId, Integer priceMin, Integer priceMax);
	Optional<Family> readById(Integer id);
	Integer create(Family family);
}
