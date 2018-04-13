package com.rsvier.workshop2.customer;

import com.rsvier.workshop2.utility.GenericDAOImpl;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaQuery;

import javax.persistence.EntityManager;

public class CustomerDAOImpl extends GenericDAOImpl<Customer> {

	protected CustomerDAOImpl(EntityManager em, Class<Customer> entityClass) {
		super(em, entityClass);
	}

	@Override
	public List<Customer> findAll() {
        CriteriaQuery<Customer> criteriaQuery = em.getCriteriaBuilder().createQuery(entityClass);
        criteriaQuery.select(criteriaQuery.from(entityClass));
        return em.createQuery(criteriaQuery).getResultList();
	}
}
