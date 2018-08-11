package com.musicshop.type;

import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
import org.springframework.stereotype.Repository;
import com.musicshop.instrument.Instrument;
import com.musicshop.persistence.GenericDaoImpl;

@Repository
public class TypeDaoImpl extends GenericDaoImpl<Type, Integer> implements TypeDao {

	public TypeDaoImpl() {
		super(Type.class);
	}

	@Override
	public List<Type> read(Integer familyId, Integer brandId) {

		
		CriteriaBuilder builder=sessionFactory.getCurrentSession().getCriteriaBuilder();
		CriteriaQuery<Type> cq=builder.createQuery(Type.class);
		
		Root<Type> type=cq.from(Type.class);
		Subquery<Long> subCount=cq.subquery(Long.class);
		Root<Instrument> instrument=subCount.from(Instrument.class);
		
		Predicate predicate=builder.conjunction();
		
		if(familyId!=null) {
			predicate=builder.and(predicate, builder.equal(type.get("family").get("id"), familyId));
		}
		if(brandId!=null) {
			Join<Type, Instrument> withInstruments=type.join("instruments");
			predicate=builder.and(predicate, builder.equal(withInstruments.get("brand").get("id"), brandId));
		}
		
		subCount.select(builder.count(instrument)).where(builder.equal(instrument.get("type").get("id"),type.get("id")));
		
		cq.select(
			    builder.construct(
			            Type.class,
			            type.get("id"),
			            type.get("name"),
			            type.get("image"),
			            type.get("family").get("id"),
			            subCount.getSelection()
			    )
			).where(predicate, builder.greaterThan(builder.toInteger(subCount.getSelection()), 0));
			return sessionFactory.getCurrentSession().createQuery(cq).getResultList();
		
		
//		Query q = sessionFactory.getCurrentSession()
//				.createNativeQuery("select t.id, t.name, t.image, t.family_id as familyId, count(i.id) as instrumentCount "
//						+ "from type t " + "left join instrument i on i.type_id=t.id where t.family_id=" + familyId
//						+ (brandId!=null?" and i.brand_id="+brandId:"")
//						+ " " + "group by t.id, t.name, t.image, t.family_id","TypeMapping");
//		
//		List<Type> types=q.list();
//		return types;
		
	}

}
