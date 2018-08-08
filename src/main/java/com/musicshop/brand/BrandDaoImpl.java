package com.musicshop.brand;

import java.util.List;

import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.musicshop.persistence.GenericDaoImpl;

@Repository
public class BrandDaoImpl extends GenericDaoImpl<Brand, Integer> implements BrandDao {

	public BrandDaoImpl() {
		super(Brand.class);
	}

	@Override
	public List<Brand> read(Integer familyId, Integer typeId, Integer propertyId) {

		Query q = sessionFactory.getCurrentSession()
				.createNativeQuery("select b.id, b.name, b.image, count(i.id) as instrumentCount " + "from brand b "
						+ "left join instrument i on i.brand_id=b.id "
						+ (propertyId != null
								? "left join instrument_property ip on ip.instrument_id=i.id left join property p on p.id=ip.property_id where p.id="
										+ propertyId
								: "")
						+ (typeId != null && propertyId == null
								? "left join type t on t.id=i.type_id where t.id=" + typeId
								: "")
						+ (familyId != null && typeId == null && propertyId == null
								? "left join type t on t.id=i.type_id where t.family_id=" + familyId
								: "")
						+ " group by b.id, b.name, b.image", Brand.class);
		List<Brand> brands = q.list();

		return brands;
	}

}
