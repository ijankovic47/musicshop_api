package com.musicshop.instrument;

import java.util.List;
import java.util.Optional;

public interface InstrumentService {

	Integer create(InstrumentDto instrument);
	List<InstrumentDto> read(Integer familyId, Integer typeId, Integer propertyId, Integer brandId, Integer pageSize, Integer pageNumber, Double priceMin, Double priceMax, InstrumentSort sort);
	Optional<InstrumentDto> readById(Integer id);
	List<Double> prices(Integer familyId, Integer typeId, Integer propertyId, Integer brandId, Double priceMin, Double priceMax);
	List<InstrumentDto> readByIds(List<Integer> ids);
	void edit(Integer id, InstrumentDto instrument);
	void delete(Integer id);
}
