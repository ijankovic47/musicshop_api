package com.musicshop.instrument;

import org.springframework.stereotype.Repository;

import com.musicshop.persistence.GenericDaoImpl;

@Repository
public class InstrumentDaoImpl extends GenericDaoImpl<Instrument, Integer> implements InstrumentDao{

	public InstrumentDaoImpl() {
		super(Instrument.class);
	}

}
