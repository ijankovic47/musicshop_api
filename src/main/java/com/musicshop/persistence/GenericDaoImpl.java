package com.musicshop.persistence;

import java.io.Serializable;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class GenericDaoImpl<T, PK extends Serializable> implements GenericDao<T, PK> {

	@Autowired
	protected SessionFactory sessionFactory;

	protected Class<T> type;

	public GenericDaoImpl(Class<T> type) {
		this.type = type;
	}

	@Override
	public PK create(T entity) {

		@SuppressWarnings("unchecked")
		PK id = (PK) sessionFactory.getCurrentSession().save(entity);
		return id;
	}

	@Override
	public T readById(PK id) {

		return sessionFactory.getCurrentSession().get(this.type, id);
	}

	@Override
	public List<T> readAll() {

		CriteriaBuilder criteriaBuilder = sessionFactory.getCurrentSession().getCriteriaBuilder();
		CriteriaQuery<T> criteria = criteriaBuilder.createQuery(this.type);
		Root<T> root = criteria.from(this.type);
		criteria.select(root);
		return sessionFactory.getCurrentSession().createQuery(criteria).getResultList();
	}
	
	@Override
	public void delete(PK id) {
		sessionFactory.getCurrentSession().delete(readById(id));
	}
}
