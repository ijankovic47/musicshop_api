package com.musicshop.family;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class FamiyServiceImpl implements FamilyService {

	private FamilyDao familyDao;

	@Autowired
	public FamiyServiceImpl(FamilyDao familyDao) {

		this.familyDao = familyDao;
	}

	@Override
	public List<Family> read(Integer brandId, Double priceMin, Double priceMax, boolean havingInstruments) {
		
		return familyDao.read(brandId, priceMin, priceMax, havingInstruments);
	}

	@Override
	public Optional<Family> readById(Integer id) {
		
		return Optional.ofNullable(familyDao.readById(id));
	}

	@Override
	public Optional<Family> readFamilyByTypeId(Integer typeId) {
		return Optional.ofNullable(familyDao.readByTypeId(typeId));
	}
	@Override
	public Integer create(Family family) {
		
		return familyDao.create(family);
	}

	@Override
	public void edit(Integer id, Family family) {
		
		Family jpe=familyDao.readById(id);
		jpe.setName(family.getName());
		
	}

	@Override
	public void delete(Integer id) {
		familyDao.delete(id);
	}
}
