package com.musicshop.property;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import com.musicshop.persistence.GenericDaoImpl;

@Repository
public class PropertyDaoImpl extends GenericDaoImpl<Property, Integer> implements PropertyDao {

	public PropertyDaoImpl() {
		super(Property.class);
	}

	public List<Property> read(Integer typeId, Integer brandId, Integer priceMin, Integer priceMax, boolean havingInstruments) {

//		CriteriaBuilder builder = sessionFactory.getCurrentSession().getCriteriaBuilder();
//		CriteriaQuery<Property> cq = builder.createQuery(Property.class);
//
//		Root<Property> property = cq.from(Property.class);
//		
//		Subquery<Long> subCount=cq.subquery(Long.class);
//		Root<Instrument> instrument=subCount.from(Instrument.class);
//
//		Predicate predicate = builder.conjunction();
//		Predicate subPredicate=builder.conjunction();
//
//		if (typeId != null) {
//			predicate = builder.and(predicate, builder.equal(property.get("type").get("id"), typeId));
//		}
//		if (brandId != null) {
//			//Join<Property, Instrument> withInstruments = property.join("instruments");
//			//predicate = builder.and(predicate, builder.equal(withInstruments.get("brand").get("id"), brandId));
//			subPredicate=builder.and(subPredicate, builder.equal(instrument.get("brand").get("id"), brandId));
//		}
//		predicate = builder.and(predicate, builder.greaterThan(builder.toInteger(subCount.select(builder.count(instrument)).where(subPredicate)), 0));
//		cq.select(property).where(predicate);
//		return sessionFactory.getCurrentSession().createQuery(cq).getResultList();

		Query q=sessionFactory.getCurrentSession().createNativeQuery("select p.id, p.name, p.type_id as typeId, count(i.id) as instrumentCount from property p "
				+ (havingInstruments?"inner ":"left ")+"join instrument_property ip on ip.property_id=p.id "
				+ (havingInstruments?"inner ":"left ")+"join instrument i on i.id=ip.instrument_id "
				+ "where p.type_id="+typeId
		        + (brandId==null?"":" and i.brand_id="+brandId)
		        + (priceMin==null?"":" and i.price>="+priceMin)
		        + (priceMax==null?"":" and i.price<"+priceMax)
		        + " group by p.id, p.name, p.type_id", "PropertyMapping");
		
		List<Property> properties=q.list();
		return properties;
	}

	@Override
	public List<Property> readByIds(List<Integer> ids) {
		
		if(ids==null||ids.isEmpty()) {
			return new ArrayList<>();
		}
		CriteriaBuilder cb=sessionFactory.getCurrentSession().getCriteriaBuilder();
		CriteriaQuery<Property> cq=cb.createQuery(Property.class);
		
		Root<Property> property=cq.from(Property.class);
		
		Predicate predicate = cb.conjunction();
		
		predicate=cb.and(predicate, property.get("id").in(ids));
		
		cq.select(property).where(predicate);
		return sessionFactory.getCurrentSession().createQuery(cq).getResultList();
	}
}
