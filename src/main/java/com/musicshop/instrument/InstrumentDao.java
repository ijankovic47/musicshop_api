package com.musicshop.instrument;

import java.util.List;

import com.musicshop.persistence.GenericDao;

public interface InstrumentDao extends GenericDao<Instrument, Integer>{

	List<Instrument> read(Integer familyId, Integer typeId, Integer propertyId, Integer brandId, Integer pageSize, Integer pageNumber, Double priceMin, Double priceMax, InstrumentSort sort);
	List<Double> prices(Integer familyId, Integer typeId, Integer propertyId, Integer brandId, Double priceMin, Double priceMax);
	List<Instrument> readByIds(List<Integer> ids);
}
