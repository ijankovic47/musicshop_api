package com.musicshop.instrument;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.musicshop.brand.Brand;
import com.musicshop.brand.BrandDao;
import com.musicshop.property.Property;
import com.musicshop.property.PropertyDao;
import com.musicshop.type.Type;
import com.musicshop.type.TypeDao;

@Service
@Transactional
public class InstrumentServiceImpl implements InstrumentService {

	private InstrumentDao instrumentDao;
	private TypeDao typeDao;
	private BrandDao brandDao;
	private PropertyDao propertyDao;

	@Autowired
	public InstrumentServiceImpl(InstrumentDao instrumentDao, TypeDao typeDao, BrandDao brandDao, PropertyDao propertyDao) {
		this.instrumentDao = instrumentDao;
		this.typeDao = typeDao;
		this.brandDao = brandDao;
		this.propertyDao=propertyDao;
	}

	@Override
	public Integer create(InstrumentDto instrument) {

		Optional<Type> type = Optional.ofNullable(typeDao.readById(instrument.getTypeId()));
		Optional<Brand> brand = Optional.ofNullable(brandDao.readById(instrument.getBrandId()));
		List<Property> properties=propertyDao.readByIds(instrument.getProperties());
	
		Instrument i = convertDtoToJpe(instrument);
		i.setBrand(brand.get());
		i.setType(type.get());
		i.setProperties(properties);

		return instrumentDao.create(i);
	}

	@Override
	public List<InstrumentDto> read(Integer familyId, Integer typeId, Integer propertyId, Integer brandId, Integer pageSize, Integer pageNumber, Integer priceMin, Integer priceMax) {

		return instrumentDao.read(familyId,typeId, propertyId, brandId, pageSize, pageNumber, priceMin, priceMax).stream().map(instrument -> convertJpeToDto(instrument))
				.collect(Collectors.toList());
	}

	@Override
	public Optional<InstrumentDto> readById(Integer id) {

		return Optional.ofNullable(convertJpeToDto(instrumentDao.readById(id)));
	}
	@Override
	public List<Double> prices(Integer familyId, Integer typeId, Integer propertyId, Integer brandId, Integer priceMin, Integer priceMax) {
		return instrumentDao.prices(familyId, typeId, propertyId, brandId, priceMin, priceMax);
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

	@Override
	public List<InstrumentDto> readByIds(List<Integer> ids) {
		return instrumentDao.readByIds(ids).stream().map(instrument -> convertJpeToDto(instrument))
				.collect(Collectors.toList());
	}
}
