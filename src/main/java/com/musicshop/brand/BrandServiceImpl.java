package com.musicshop.brand;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class BrandServiceImpl implements BrandService {

	private BrandDao brandDao;

	@Autowired
	public BrandServiceImpl(BrandDao brandDao) {

		this.brandDao = brandDao;
	}

	@Override
	public Integer create(Brand brand) {

		return brandDao.create(brand);
	}

	@Override
	public List<Brand> read(Integer familyId, Integer typeId, Integer propertyId, Double priceMin, Double priceMax, boolean havingInstruments) {
		return brandDao.read(familyId, typeId, propertyId, priceMin, priceMax, havingInstruments);
	}

	@Override
	public Optional<Brand> readById(Integer id) {
		return Optional.ofNullable(brandDao.readById(id));
	}
}
