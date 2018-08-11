package com.musicshop.instrument;

import java.util.List;
import java.util.Optional;

public interface InstrumentService {

	Integer create(InstrumentDto instrument);
	List<InstrumentDto> read(Integer familyId, Integer typeId, Integer propertyId, Integer brandId, Integer pageSize, Integer pageNumber, Integer priceMin, Integer priceMax);
	Optional<InstrumentDto> readById(Integer id);
	List<Double> prices(Integer familyId, Integer typeId, Integer propertyId, Integer brandId, Integer priceMin, Integer priceMax);
}
