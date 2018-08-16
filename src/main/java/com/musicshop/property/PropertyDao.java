package com.musicshop.property;

import java.util.List;

import com.musicshop.persistence.GenericDao;

public interface PropertyDao extends GenericDao<Property, Integer>{

	List<Property> read(Integer typeId, Integer brandId, Double priceMin, Double priceMax, boolean havingInstruments);
	List<Property> readByIds(List<Integer> ids);
}
