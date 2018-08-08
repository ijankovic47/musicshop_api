package com.musicshop.instrument;

import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import com.musicshop.brand.Brand;
import com.musicshop.family.Family;
import com.musicshop.persistence.GenericDaoImpl;
import com.musicshop.property.Property;
import com.musicshop.type.Type;

@Repository
public class InstrumentDaoImpl extends GenericDaoImpl<Instrument, Integer> implements InstrumentDao{

	public InstrumentDaoImpl() {
		super(Instrument.class);
	}

	@Override
	public List<Instrument> read(Integer familyId, Integer typeId, Integer propertyId, Integer brandId) {
		
	        CriteriaBuilder builder = sessionFactory.getCurrentSession().getCriteriaBuilder();
	        CriteriaQuery<Instrument> criteria = builder.createQuery(type);
	        Root<Instrument> root = criteria.from(type);
	        Predicate predicate = builder.conjunction();
	        
	        if(propertyId!=null) {
	        	Join<Instrument, Property> withProperty=root.join("properties");
	        	predicate=builder.and(predicate, builder.equal(withProperty.get("id"), propertyId));
	        }
	        else if(typeId!=null) {
	        	Join<Instrument, Type> withType=root.join("type");
	        	predicate=builder.and(predicate, builder.equal(withType.get("id"), typeId));
	        }
	        else if(familyId!=null) {
	        	Join<Instrument, Type> withType=root.join("type");
	        	Join<Type, Family> withFamily=withType.join("family");
	        	predicate=builder.and(predicate, builder.equal(withFamily.get("id"), familyId));
	        }
	        if(brandId!=null) {
	        	Join<Instrument, Brand> withBrand=root.join("brand");
	        	predicate=builder.and(predicate, builder.equal(withBrand.get("id"), brandId));
	        }
	        criteria.select(root).where(predicate);
	        return sessionFactory.getCurrentSession().createQuery(criteria).getResultList();
	}

}
