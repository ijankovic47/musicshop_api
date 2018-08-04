package com.musicshop.instrument;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.musicshop.brand.Brand;
import com.musicshop.brand.BrandDao;
import com.musicshop.type.Type;
import com.musicshop.type.TypeDao;

@Service
@Transactional
public class InstrumentServiceImpl implements InstrumentService {

	private InstrumentDao instrumentDao;
	private TypeDao typeDao;
	private BrandDao brandDao;

	@Autowired
	public InstrumentServiceImpl(InstrumentDao instrumentDao, TypeDao typeDao, BrandDao brandDao) {
		this.instrumentDao = instrumentDao;
		this.typeDao = typeDao;
		this.brandDao = brandDao;
	}

	@Override
	public Integer create(InstrumentDto instrument) {

		Optional<Type> type = Optional.ofNullable(typeDao.readById(instrument.getTypeId()));
		Optional<Brand> brand = Optional.ofNullable(brandDao.readById(instrument.getBrandId()));

		Instrument i = convertDtoToJpe(instrument);
		i.setBrand(brand.get());
		i.setType(type.get());

		return instrumentDao.create(i);
	}

	@Override
	public List<InstrumentDto> read(Integer typeId, Integer brandId) {

		return instrumentDao.readAll().stream().map(instrument -> convertJpeToDto(instrument))
				.collect(Collectors.toList());
	}

	@Override
	public Optional<InstrumentDto> readById(Integer id) {

		return Optional.ofNullable(convertJpeToDto(instrumentDao.readById(id)));
	}

	private InstrumentDto convertJpeToDto(Instrument jpe) {

		if (jpe == null) {
			return null;
		}
		InstrumentDto dto = new InstrumentDto();
		dto.setId(jpe.getId());
		dto.setName(jpe.getName());
		dto.setPrice(jpe.getPrice());
		dto.setVideo(jpe.getVideo());
		dto.setDescription(jpe.getDescription());
		dto.setImages(jpe.getImages());
		dto.setBrandId(jpe.getBrand().getId());
		dto.setTypeId(jpe.getType().getId());

		return dto;
	}

	private Instrument convertDtoToJpe(InstrumentDto dto) {

		Instrument i = new Instrument();
		i.setId(dto.getId());
		i.setName(dto.getName());
		i.setPrice(dto.getPrice());
		i.setDescription(dto.getDescription());
		i.setImages(dto.getImages());
		i.setVideo(dto.getVideo());

		return i;
	}

}
