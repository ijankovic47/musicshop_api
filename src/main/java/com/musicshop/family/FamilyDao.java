package com.musicshop.family;

import java.util.List;

import com.musicshop.persistence.GenericDao;

public interface FamilyDao extends GenericDao<Family, Integer>{

	List<Family> read(Integer brandId, Double priceMin, Double priceMax, boolean havingInstrumnts);
}
