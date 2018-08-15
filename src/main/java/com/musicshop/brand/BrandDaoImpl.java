package com.musicshop.brand;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
import org.springframework.stereotype.Repository;
import com.musicshop.family.Family;
import com.musicshop.instrument.Instrument;
import com.musicshop.persistence.GenericDaoImpl;
import com.musicshop.property.Property;
import com.musicshop.type.Type;

@Repository
public class BrandDaoImpl extends GenericDaoImpl<Brand, Integer> implements BrandDao {

	public BrandDaoImpl() {
		super(Brand.class);
	}

	@Override
	public List<Brand> read(Integer familyId, Integer typeId, Integer propertyId, Integer priceMin, Integer priceMax,
			boolean havingInstruments) {

		CriteriaBuilder builder = sessionFactory.getCurrentSession().getCriteriaBuilder();
		CriteriaQuery<Brand> cq = builder.createQuery(Brand.class);
		Root<Brand> root = cq.from(Brand.class);

		Subquery<Long> subCount = cq.subquery(Long.class);
		Root<Instrument> instrument = subCount.from(Instrument.class);

		Predicate predicate = builder.conjunction();
		if (propertyId != null) {
			Join<Instrument, Property> withProperty = instrument.join("properties");
			predicate = builder.and(predicate, builder.equal(withProperty.get("id"), propertyId));
		} else if (typeId != null) {
			Join<Instrument, Type> withType = instrument.join("type");
			predicate = builder.and(predicate, builder.equal(withType.get("id"), typeId));
		} else if (familyId != null) {
			Join<Instrument, Type> withType = instrument.join("type");
			Join<Type, Family> withFamily = withType.join("family");
			predicate = builder.and(predicate, builder.equal(withFamily.get("id"), familyId));
		}
		if (priceMin != null) {
			predicate = builder.and(predicate, builder.greaterThanOrEqualTo(instrument.get("price"), priceMin));
		}
		if (priceMax != null) {
			predicate = builder.and(predicate, builder.lessThan(instrument.get("price"), priceMax));
		}
		predicate = builder.and(predicate, builder.equal(instrument.get("brand").get("id"), root.get("id")));

		subCount.select(builder.count(instrument)).where(predicate);

		cq.select(builder.construct(Brand.class, root.get("id"), root.get("name"), root.get("image"),
				subCount.getSelection()))
				.where(havingInstruments ? builder.greaterThan(builder.toInteger(subCount.getSelection()), 0)
						: builder.conjunction());
		return sessionFactory.getCurrentSession().createQuery(cq).getResultList();

//		Query q = sessionFactory.getCurrentSession()
//				.createNativeQuery("select b.id, b.name, b.image, count(i.id) as instrumentCount " + "from brand b "
//						+ "left join instrument i on i.brand_id=b.id "
//						+ (propertyId != null
//								? "left join instrument_property ip on ip.instrument_id=i.id left join property p on p.id=ip.property_id where p.id="
//										+ propertyId
//								: "")
//						+ (typeId != null && propertyId == null
//								? "left join type t on t.id=i.type_id where t.id=" + typeId
//								: "")
//						+ (familyId != null && typeId == null && propertyId == null
//								? "left join type t on t.id=i.type_id where t.family_id=" + familyId
//								: "")
//						+ " group by b.id, b.name, b.image", Brand.class);
//		List<Brand> brands = q.list();

		// return brands;
	}

}
