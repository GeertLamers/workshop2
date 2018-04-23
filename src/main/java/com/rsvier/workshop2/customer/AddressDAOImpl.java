package com.rsvier.workshop2.customer;

import com.rsvier.workshop2.utility.GenericDAOImpl;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;

public class AddressDAOImpl extends GenericDAOImpl<Address> {

	public AddressDAOImpl(EntityManager em, Class<Address> entityClass) {
		super(em, entityClass);
	}

	@Override
	public List<Address> findAll() {
        CriteriaQuery<Address> criteriaQuery = em.getCriteriaBuilder().createQuery(entityClass);
        criteriaQuery.select(criteriaQuery.from(entityClass));
        return em.createQuery(criteriaQuery).getResultList();
	}
	
	public List<Address> findAddressesByCustomer(Customer customer) {
		Query query = em.createQuery("FROM Address a WHERE a.customer = :customer");
		query.setParameter("customer", customer);
		@SuppressWarnings("unchecked")
		List<Address> addressesOfCustomer = query.getResultList();
		return addressesOfCustomer;
	}
}
