package com.musicshop.type;

import java.util.List;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import com.musicshop.persistence.GenericDaoImpl;

@Repository
public class TypeDaoImpl extends GenericDaoImpl<Type, Integer> implements TypeDao {

	public TypeDaoImpl() {
		super(Type.class);
	}

	@Override
	public List<Type> read(Integer familyId, Integer brandId) {

		Query q = sessionFactory.getCurrentSession()
				.createNativeQuery("select t.id, t.name, t.image, t.family_id as familyId, count(i.id) as instrumentCount "
						+ "from type t " + "left join instrument i on i.type_id=t.id where t.family_id=" + familyId
						+ (brandId!=null?" and i.brand_id="+brandId:"")
						+ " " + "group by t.id, t.name, t.image, t.family_id","TypeMapping");
		
		List<Type> types=q.list();
		return types;
		
	}

}
