package com.rsvier.workshop2.product;

import com.rsvier.workshop2.utility.GenericDAOImpl;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaQuery;

public class ProductDAOImpl extends GenericDAOImpl<Product> {
	
	public ProductDAOImpl(EntityManager em, Class<Product> entityClass) {
		super(em, entityClass);
	}
	
	@Override
	public List<Product> findAll() {
        CriteriaQuery<Product> criteriaQuery = em.getCriteriaBuilder().createQuery(entityClass);
        criteriaQuery.select(criteriaQuery.from(entityClass));
        return em.createQuery(criteriaQuery).getResultList();
	}
}
