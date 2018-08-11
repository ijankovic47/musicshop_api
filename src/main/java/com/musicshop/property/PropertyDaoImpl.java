package com.musicshop.property;

import java.util.List;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import com.musicshop.persistence.GenericDaoImpl;

@Repository
public class PropertyDaoImpl extends GenericDaoImpl<Property, Integer> implements PropertyDao {

	public PropertyDaoImpl() {
		super(Property.class);
	}

	public List<Property> read(Integer typeId, Integer brandId) {

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
				+ "inner join instrument_property ip on ip.property_id=p.id "
				+ "inner join instrument i on i.id=ip.instrument_id "
				+ "where p.type_id="+typeId
		        + (brandId==null?"":" and i.brand_id="+brandId)
		        + " group by p.id, p.name, p.type_id", "PropertyMapping");
		
		List<Property> properties=q.list();
		return properties;
	}
}
