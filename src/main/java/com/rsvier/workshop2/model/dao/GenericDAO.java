package com.rsvier.workshop2.model.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaQuery;

public abstract class GenericDAO<T> {
	
	protected EntityManager em;
	protected final Class<T> entityClass;
	
	protected GenericDAO(EntityManager em, Class<T> entityClass) {
		this.em = em;
		this.entityClass = entityClass;
	}
		
	public T create(T entity) {
		em.getTransaction().begin();
		em.persist(entity);
		em.getTransaction().commit();
		em.close();
		return entity;
	}
	
	public List<T> findAll() {
        CriteriaQuery<T> criteriaQuery = em.getCriteriaBuilder().createQuery(entityClass);
        criteriaQuery.select(criteriaQuery.from(entityClass));
        return em.createQuery(criteriaQuery).getResultList();
	}
	
	public T findById(Class<T> type, Long id) {
		em.getTransaction().begin();
		T entity = em.find(type, id);
		em.getTransaction().commit();
		em.close();
		return entity;
	}
	
	public void update(T entity) {
		em.getTransaction().begin();
		em.merge(entity);
		em.getTransaction().commit();
		em.close();
	}
	
	public void delete (T entity) {
		em.getTransaction().begin();
		em.remove(entity);
		em.getTransaction().commit();
		em.close();
	}
}
