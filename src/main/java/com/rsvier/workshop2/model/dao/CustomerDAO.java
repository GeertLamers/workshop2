package com.rsvier.workshop2.model.dao;

import java.util.List;

import com.rsvier.workshop2.model.Customer;

public interface CustomerDAO {
	
	// Create
	public Long createCustomer(Customer customer);
	
	// Read
	public List<Customer> findAllCustomers();
	public Customer findCustomerById(Long customerId);
	public Customer findCustomerByLastName(String lastName);
	
	// Update
	public void updateCustomer(Customer customer);
	
	// Delete
	public void deleteCustomer(Customer customer);

}
