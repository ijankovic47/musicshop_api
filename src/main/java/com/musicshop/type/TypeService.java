package com.musicshop.type;

import java.util.List;
import java.util.Optional;

public interface TypeService {

	Integer create(Type type);
	Optional<Type> readById(Integer id);
	List<Type> readAll();
}
