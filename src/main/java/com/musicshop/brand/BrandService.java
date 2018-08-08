package com.musicshop.brand;

import java.util.List;
import java.util.Optional;

public interface BrandService {

	Integer create(Brand brand);
	List<Brand> read(Integer familyId, Integer typeId);
	Optional<Brand> readById(Integer id);
}
