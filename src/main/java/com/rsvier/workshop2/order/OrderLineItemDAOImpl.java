package com.rsvier.workshop2.order;

import com.rsvier.workshop2.utility.GenericDAOImpl;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaQuery;

import java.util.List;


public class OrderLineItemDAOImpl extends GenericDAOImpl<OrderLineItem> {

	protected OrderLineItemDAOImpl (EntityManager em, Class<OrderLineItem> entityClass) {
		super(em, entityClass);
	}

	@Override
	public List<OrderLineItem> findALl() {
        CriteriaQuery<OrderLineItem> criteriaQuery = em.getCriteriaBuilder().createQuery(entityClass);
        criteriaQuery.select(criteriaQuery.from(entityClass));
        return em.createQuery(criteriaQuery).getResultList();
	}	
}