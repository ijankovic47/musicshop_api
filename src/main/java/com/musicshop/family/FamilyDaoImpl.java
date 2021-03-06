package com.musicshop.family;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
import org.springframework.stereotype.Repository;
import com.musicshop.instrument.Instrument;
import com.musicshop.persistence.GenericDaoImpl;
import com.musicshop.type.Type;

@Repository
public class FamilyDaoImpl extends GenericDaoImpl<Family, Integer> implements FamilyDao{

	public FamilyDaoImpl() {
		super(Family.class);
	}
	@Override
	public List<Family> read(Integer brandId, Double priceMin, Double priceMax, boolean havingInstruments) {
		CriteriaBuilder builder=sessionFactory.getCurrentSession().getCriteriaBuilder();
		CriteriaQuery<Family> cq=builder.createQuery(Family.class);
		
		Root<Family> family=cq.from(Family.class);
		
		Subquery<Long> subCount=cq.subquery(Long.class);
		
		Root<Instrument> instrument=subCount.from(Instrument.class);
		Predicate predicate =builder.conjunction();
		predicate=builder.and(predicate, builder.equal(instrument.get("type").get("family").get("id"),family.get("id")));
		
		if(brandId!=null) {
			predicate=builder.and(predicate, builder.equal(instrument.get("brand").get("id"), brandId));
		}
		if(priceMin!=null) {
			predicate=builder.and(predicate, builder.greaterThanOrEqualTo(instrument.get("price"), priceMin));
		}
		if(priceMax!=null) {
			predicate=builder.and(predicate, builder.lessThan(instrument.get("price"), priceMax));
		}
		subCount.select(builder.count(instrument)).where(predicate);
		
		cq.select(
			    builder.construct(
			            Family.class,
			            family.get("id"),
			            family.get("name"),
			            subCount.getSelection()
			    )
			).where(havingInstruments?builder.greaterThan(builder.toInteger(subCount.getSelection()), 0):builder.conjunction());
			return sessionFactory.getCurrentSession().createQuery(cq).getResultList();
		
//		Query q=sessionFactory.getCurrentSession().createNativeQuery("select f.id, f.name, count(i.id) as instrumentcount "
//				+ "from family f "
//				+ "left join type t on t.family_id=f.id "
//				+ "left join instrument i on i.type_id=t.id "
//				+  (brandId==null?"":"where i.brand_id="+brandId+" ")
//				+ "group by f.id, f.name", Family.class);
//		
//		List<Family> families=q.list();
//		
//		return families;
		
	}
	@Override
	public Family readByTypeId(Integer typeId) {

		CriteriaBuilder builder=sessionFactory.getCurrentSession().getCriteriaBuilder();
		CriteriaQuery<Family> cq=builder.createQuery(Family.class);
		
		Root<Family> family=cq.from(Family.class);
		
		Subquery<Long> familyId=cq.subquery(Long.class);
		Root<Type> type=familyId.from(Type.class);
		Predicate subPredicate =builder.conjunction();
		subPredicate=builder.and(subPredicate, builder.equal(type.get("id"), typeId));
		familyId.select(type.get("family").get("id")).where(subPredicate);
		Predicate predicate=builder.conjunction();
		predicate=builder.and(predicate, builder.equal(family.get("id"), familyId.getSelection()));
		
		cq.select(family).where(predicate);
		
		return sessionFactory.getCurrentSession().createQuery(cq).getSingleResult();
	}
}
