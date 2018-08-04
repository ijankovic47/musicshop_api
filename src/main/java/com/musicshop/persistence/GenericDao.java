package com.musicshop.persistence;

import java.io.Serializable;
import java.util.List;

public interface GenericDao<T, PK extends Serializable> {

	PK create(T entity);
	T readById(PK id);
	List<T> readAll();
}
