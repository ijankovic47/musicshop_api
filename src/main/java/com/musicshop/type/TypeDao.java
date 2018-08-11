package com.musicshop.type;

import java.util.List;

import com.musicshop.persistence.GenericDao;

public interface TypeDao extends GenericDao<Type, Integer>{

	List<Type> read(Integer familyId, Integer brandId, Integer priceMin, Integer priceMax);
}
