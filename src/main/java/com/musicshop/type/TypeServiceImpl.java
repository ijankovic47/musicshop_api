package com.musicshop.type;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.musicshop.family.Family;
import com.musicshop.family.FamilyDao;

@Service
@Transactional
public class TypeServiceImpl implements TypeService {

	private TypeDao typeDao;
	private FamilyDao familyDao;

	@Autowired
	public TypeServiceImpl(TypeDao typeDao, FamilyDao familyDao) {
		this.typeDao = typeDao;
		this.familyDao = familyDao;
	}

	@Override
	public Integer create(TypeDto type) {

		Type t=convertDtoToJpa(type);
		Optional<Family> o=Optional.ofNullable(familyDao.readById(type.getFamilyId()));
		
		t.setFamily(o.get());
		
		return typeDao.create(t);
	}

	@Override
	public Optional<TypeDto> readById(Integer id) {

		return Optional.ofNullable(convertJpeToDto(typeDao.readById(id)));
	}

	@Override
	public List<TypeDto> read(Integer familyId, Integer brandId, Double priceMin, Double priceMax, boolean havingInstruments) {

		return typeDao.read(familyId, brandId, priceMin, priceMax, havingInstruments).stream().map(type->convertJpeToDto(type)).collect(Collectors.toList());
	}
	@Override
	public void edit(Integer id, TypeDto type) {
		
		Type t=typeDao.readById(id);
		t.setFamily(familyDao.readById(type.getFamilyId()));
		t.setImage(type.getImage());
		t.setName(type.getName());
	}
	
	@Override
	public void delete(Integer id) {
		typeDao.delete(id);
	}

	private TypeDto convertJpeToDto(Type type) {
		
		TypeDto dto=new TypeDto();
		dto.setId(type.getId());
		dto.setName(type.getName());
		dto.setImage(type.getImage());
		dto.setFamilyId(type.getFamily().getId());
		dto.setInstrumentCount(type.getInstrumentCount());
		
		return dto;
	}
	
	private Type convertDtoToJpa(TypeDto dto) {
		
		Type t=new Type();
		t.setId(dto.getId());
		t.setImage(dto.getImage());
		t.setName(dto.getName());
		
		return t;
	}

	@Override
	public Optional<TypeDto> readByPropertyId(Integer propertyId) {
		
		return Optional.ofNullable(convertJpeToDto(typeDao.readByPropertyId(propertyId)));
	}
}
