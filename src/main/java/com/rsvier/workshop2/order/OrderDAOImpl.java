
package com.rsvier.workshop2.order;

import com.rsvier.workshop2.customer.Customer;
import com.rsvier.workshop2.utility.GenericDAOImpl;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;


public class OrderDAOImpl extends GenericDAOImpl<Order> {

	protected OrderDAOImpl (EntityManager em, Class<Order> entityClass) {
		super(em, entityClass);
	}
	
	@Override
	public List<Order> findAll() {
        CriteriaQuery<Order> criteriaQuery = em.getCriteriaBuilder().createQuery(entityClass);
        criteriaQuery.select(criteriaQuery.from(entityClass));
        return em.createQuery(criteriaQuery).getResultList();
	}

	public List<Order> findAllOrdersOfCustomer(Customer customer) {
		TypedQuery<Order> query = em.createNamedQuery("Order.findAllByCustomer", Order.class);
		return query.getResultList();
	}

	public List<Order> findCompletedOrdersOnly() {
		TypedQuery<Order> query = em.createNamedQuery("Order.findCompletedOnly", Order.class);
		return query.getResultList();
	}

	public List<Order> findPendingOrdersOnly() {
		TypedQuery<Order> query = em.createNamedQuery("Order.findPendingOnly", Order.class);
		return query.getResultList();
	}
}
