package com.rsvier.workshop2.customer;

import com.rsvier.workshop2.utility.GenericDAOImpl;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaQuery;

public class AddressDAOImpl extends GenericDAOImpl<Address> {

	protected AddressDAOImpl(EntityManager em, Class<Address> entityClass) {
		super(em, entityClass);
	}

	@Override
	public List<Address> findALl() {
        CriteriaQuery<Address> criteriaQuery = em.getCriteriaBuilder().createQuery(entityClass);
        criteriaQuery.select(criteriaQuery.from(entityClass));
        return em.createQuery(criteriaQuery).getResultList();
	}
}
