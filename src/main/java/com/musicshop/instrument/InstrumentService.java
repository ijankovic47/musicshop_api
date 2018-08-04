package com.musicshop.instrument;

import java.util.List;
import java.util.Optional;

public interface InstrumentService {

	Integer create(InstrumentDto instrument);
	List<InstrumentDto> read(Integer typeId, Integer brandId);
	Optional<InstrumentDto> readById(Integer id);
}
