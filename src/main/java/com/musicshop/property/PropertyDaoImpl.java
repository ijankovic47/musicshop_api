package com.musicshop.property;

import java.util.List;

import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.musicshop.persistence.GenericDaoImpl;

@Repository
public class PropertyDaoImpl extends GenericDaoImpl<Property, Integer> implements PropertyDao{

	public PropertyDaoImpl() {
		super(Property.class);
	}

	public List<Property> read(Integer typeId, Integer brandId){
		
		Query q=sessionFactory.getCurrentSession().createNativeQuery("select p.id, p.name, p.type_id as typeId, count(i.id) as instrumentCount from property p "
				+ "left join instrument_property ip on ip.property_id=p.id "
				+ "left join instrument i on i.id=ip.instrument_id "
				+ "where p.type_id="+typeId
		        + (brandId==null?"":" and i.brand_id="+brandId)
		        + " group by p.id, p.name, p.type_id", "PropertyMapping");
		
		List<Property> properties=q.list();
		return properties;
	}
}
