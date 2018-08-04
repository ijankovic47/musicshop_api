package com.musicshop.type;

import org.springframework.stereotype.Repository;

import com.musicshop.persistence.GenericDaoImpl;

@Repository
public class TypeDaoImpl extends GenericDaoImpl<Type, Integer> implements TypeDao{

	public TypeDaoImpl() {
		super(Type.class);
	}

}
