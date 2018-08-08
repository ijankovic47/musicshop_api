package com.musicshop.instrument;

import java.util.List;

import com.musicshop.persistence.GenericDao;

public interface InstrumentDao extends GenericDao<Instrument, Integer>{

	List<Instrument> read(Integer familyId, Integer typeId, Integer propertyId, Integer brandId);
}
