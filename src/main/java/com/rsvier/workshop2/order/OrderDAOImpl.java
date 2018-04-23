
package com.rsvier.workshop2.order;

import com.rsvier.workshop2.customer.Customer;
import com.rsvier.workshop2.utility.GenericDAOImpl;

import java.util.List;

import javax.persistence.Query;
import javax.persistence.EntityManager;
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
		Query query = em.createQuery("FROM `order` o WHERE o.customer_id = :customerId");
		query.setParameter("customerId", customer.getCustomerId());
		@SuppressWarnings("unchecked")
		List<Order> ordersOfCustomer = query.getResultList();
		return ordersOfCustomer;
	}

	public List<Order> findCompletedOrdersOnly() {
		Query query = em.createQuery("FROM \"order\" o WHERE o.completed = 'true'");
		@SuppressWarnings("unchecked")
		List<Order> ordersOfCustomer = query.getResultList();
		return ordersOfCustomer;
	}

	public List<Order> findPendingOrdersOnly() {
		Query query = em.createQuery("FROM \"order\" o WHERE o.completed = 'false'");
		@SuppressWarnings("unchecked")
		List<Order> ordersOfCustomer = query.getResultList();
		return ordersOfCustomer;
	}
}
