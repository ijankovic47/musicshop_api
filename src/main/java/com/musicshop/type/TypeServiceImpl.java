package com.musicshop.type;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TypeServiceImpl implements TypeService{

	private TypeDao typeDao;
	
	@Autowired
	public TypeServiceImpl(TypeDao typeDao) {
		this.typeDao=typeDao;
	}
	
	@Override
	public Integer create(Type type) {
		
		return typeDao.create(type);
	}

	@Override
	public Optional<Type> readById(Integer id) {
		
		return Optional.ofNullable(typeDao.readById(id));
	}

	@Override
	public List<Type> readAll() {
		
		return typeDao.readAll();
	}

}
