package com.musicshop.instrument;

import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import com.musicshop.brand.Brand;
import com.musicshop.family.Family;
import com.musicshop.persistence.GenericDaoImpl;
import com.musicshop.property.Property;
import com.musicshop.type.Type;

@Repository
public class InstrumentDaoImpl extends GenericDaoImpl<Instrument, Integer> implements InstrumentDao {

	public InstrumentDaoImpl() {
		super(Instrument.class);
	}
	
	public List<Instrument> read(Integer familyId, Integer typeId, Integer propertyId, Integer brandId,
			Integer pageSize, Integer pageNumber, Double priceMin, Double priceMax, InstrumentSort sort) {

		CriteriaBuilder builder = sessionFactory.getCurrentSession().getCriteriaBuilder();
		CriteriaQuery<Instrument> criteria = builder.createQuery(Instrument.class);
		Root<Instrument> root = criteria.from(Instrument.class);
		Predicate predicate = builder.conjunction();
		if (propertyId != null) {
			Join<Instrument, Property> withProperty = root.join("properties");
			predicate = builder.and(predicate, builder.equal(withProperty.get("id"), propertyId));
		} else if (typeId != null) {
			Join<Instrument, Type> withType = root.join("type");
			predicate = builder.and(predicate, builder.equal(withType.get("id"), typeId));
		} else if (familyId != null) {
			Join<Instrument, Type> withType = root.join("type");
			Join<Type, Family> withFamily = withType.join("family");
			predicate = builder.and(predicate, builder.equal(withFamily.get("id"), familyId));
		}
		if (brandId != null) {
			Join<Instrument, Brand> withBrand = root.join("brand");
			predicate = builder.and(predicate, builder.equal(withBrand.get("id"), brandId));
		}
		if (priceMin != null) {
			predicate = builder.and(predicate, builder.greaterThanOrEqualTo(root.get("price"), priceMin));
		}
		if (priceMax != null) {
			predicate = builder.and(predicate, builder.lessThan(root.get("price"), priceMax));
		}
		if(sort!=null) {
			switch (sort){
			case nameASC: criteria.orderBy(builder.asc(root.get("name")));
			break;
			case nameDESC: criteria.orderBy(builder.desc(root.get("name")));
			break;
			case priceASC: criteria.orderBy(builder.asc(root.get("price")));
			break;
			case priceDESC: criteria.orderBy(builder.desc(root.get("price")));
			break;
			}
		}
		criteria.select(root).where(predicate);
		Session s = sessionFactory.getCurrentSession();
		if (pageSize != null) {
			return s.createQuery(criteria).setFirstResult(pageNumber != null ? (pageNumber - 1) * pageSize : 0)
					.setMaxResults(pageSize).getResultList();
		} else {
			return s.createQuery(criteria).getResultList();
		}

	}

	@Override
	public List<Double> prices(Integer familyId, Integer typeId, Integer propertyId, Integer brandId, Double priceMin,
			Double priceMax) {

		CriteriaBuilder builder = sessionFactory.getCurrentSession().getCriteriaBuilder();
		CriteriaQuery<Double> criteria = builder.createQuery(Double.class);
		Root<Instrument> root = criteria.from(type);
		Predicate predicate = builder.conjunction();

		if (propertyId != null) {
			Join<Instrument, Property> withProperty = root.join("properties");
			predicate = builder.and(predicate, builder.equal(withProperty.get("id"), propertyId));
		} else if (typeId != null) {
			Join<Instrument, Type> withType = root.join("type");
			predicate = builder.and(predicate, builder.equal(withType.get("id"), typeId));
		} else if (familyId != null) {
			Join<Instrument, Type> withType = root.join("type");
			Join<Type, Family> withFamily = withType.join("family");
			predicate = builder.and(predicate, builder.equal(withFamily.get("id"), familyId));
		}
		if (brandId != null) {
			Join<Instrument, Brand> withBrand = root.join("brand");
			predicate = builder.and(predicate, builder.equal(withBrand.get("id"), brandId));
		}
		if (priceMin != null && priceMax != null) {
			predicate = builder.and(predicate, builder.between(root.get("price"), priceMin, priceMax));
		}
		if (priceMin != null) {
			predicate = builder.and(predicate, builder.greaterThanOrEqualTo(root.get("price"), priceMin));
		}
		if (priceMax != null) {
			predicate = builder.and(predicate, builder.lessThan(root.get("price"), priceMax));
		}
		criteria.select(root.get("price")).where(predicate);
		return sessionFactory.getCurrentSession().createQuery(criteria).getResultList();
	}

	@Override
	public List<Instrument> readByIds(List<Integer> ids) {
		
		CriteriaBuilder builder = sessionFactory.getCurrentSession().getCriteriaBuilder();
		CriteriaQuery<Instrument> criteria = builder.createQuery(type);
		Root<Instrument> instrument = criteria.from(type);
		Predicate predicate = builder.conjunction();
		
		predicate=builder.and(predicate, instrument.get("id").in(ids));
		
		criteria.select(instrument).where(predicate);
		return sessionFactory.getCurrentSession().createQuery(criteria).getResultList();
	}

}
