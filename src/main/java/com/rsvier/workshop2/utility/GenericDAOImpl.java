package com.rsvier.workshop2.utility;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaQuery;

public abstract class GenericDAOImpl<T> implements GenericDAO<T> {
	
	protected EntityManager em;
	protected final Class<T> entityClass;
	
	protected GenericDAOImpl(EntityManager em, Class<T> entityClass) {
		this.em = em;
		this.entityClass = entityClass;
	}
		
	public T create(T entity) {
		em.getTransaction().begin();
		em.persist(entity);
		em.getTransaction().commit();
		return entity;
	}
	
	public List<T> findAll() {
        CriteriaQuery<T> criteriaQuery = em.getCriteriaBuilder().createQuery(entityClass);
        criteriaQuery.select(criteriaQuery.from(entityClass));
        return em.createQuery(criteriaQuery).getResultList();
	}
	
	public T findById(Class<T> type, Long id) {
		T entity = em.find(type, id);
		return entity;
	}
	
	public void update(T entity) {
		em.getTransaction().begin();
		em.merge(entity);
		em.getTransaction().commit();
	}
	
	public void delete (T entity) {
		em.getTransaction().begin();
		em.remove(entity);
		em.getTransaction().commit();
	}
}
