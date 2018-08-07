package com.musicshop.family;

import java.util.List;

import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import com.musicshop.persistence.GenericDaoImpl;

@Repository
public class FamilyDaoImpl extends GenericDaoImpl<Family, Integer> implements FamilyDao{

	public FamilyDaoImpl() {
		super(Family.class);
	}

	@Override
	public List<Family> read(Integer brandId) {
		
		Query q=sessionFactory.getCurrentSession().createNativeQuery("select f.id, f.name, count(i.id) as instrumentcount "
				+ "from family f "
				+ "left join type t on t.family_id=f.id "
				+ "left join instrument i on i.type_id=t.id "
				+  (brandId==null?"":"where i.brand_id="+brandId+" ")
				+ "group by f.id, f.name", Family.class);
		
		List<Family> families=q.list();
		
		return families;
		
	}
}
