package com.rsvier.workshop2.model.dao;

import java.util.List;

import com.rsvier.workshop2.model.Customer;
import com.rsvier.workshop2.model.Order;

public interface OrderDAO {
	
	// Create
	public int createOrder(Order order, Customer customer);
	
	// Read
	public Order findOrderById(Long orderId);
	public List<Order> findAllOrders();
	public List<Order> findAllOrdersOfCustomer(Customer customer);
	public List<Order> findCompletedOrders();
	public List<Order> findPendingOrders();
	public boolean isOrderStoredWithId(Long orderId);
	
	// Update
	public boolean updateOrder(Order order, Customer customer);
	
	// Delete
	public boolean deleteOrder(Order order);
}
