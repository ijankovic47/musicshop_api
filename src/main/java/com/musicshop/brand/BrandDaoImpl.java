package com.musicshop.brand;

import org.springframework.stereotype.Repository;

import com.musicshop.persistence.GenericDaoImpl;

@Repository
public class BrandDaoImpl extends GenericDaoImpl<Brand, Integer> implements BrandDao{

	public BrandDaoImpl() {
		super(Brand.class);
	}

}
