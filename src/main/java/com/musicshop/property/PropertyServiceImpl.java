package com.musicshop.property;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.musicshop.type.Type;
import com.musicshop.type.TypeDao;

@Service
@Transactional
public class PropertyServiceImpl implements PropertyService{

	private PropertyDao propertyDao;
	private TypeDao typeDao;
	
	@Autowired
	public PropertyServiceImpl(PropertyDao propertyDao, TypeDao typeDao) {
		this.propertyDao=propertyDao;
		this.typeDao=typeDao;
	}
	
	@Override
	public List<PropertyDto> read(Integer typeId, Integer brandId) {
		
		return propertyDao.read(typeId, brandId).stream().map(property->convertJpeToDto(property)).collect(Collectors.toList());
	}
	
	@Override
	public Integer create(PropertyDto property) {
		
		Optional<Type> type=Optional.ofNullable(typeDao.readById(property.getTypeId()));
		
		Property p=convertDtoToJpe(property);
		p.setType(type.get());
		return propertyDao.create(p);
	}
	
	private PropertyDto convertJpeToDto(Property property) {
		
		PropertyDto dto=new PropertyDto();
		dto.setId(property.getId());
		dto.setName(property.getName());
		dto.setTypeId(property.getType().getId());
		dto.setInstrumentCount(property.getInstrumentCount());
		
		return dto;
	}
	private Property convertDtoToJpe(PropertyDto property) {
		
		Property jpe=new Property();
		jpe.setName(property.getName());
		
		return jpe;
	}
}
