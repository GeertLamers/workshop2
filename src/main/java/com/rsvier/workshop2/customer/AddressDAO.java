package com.rsvier.workshop2.customer;

import java.util.List;

public interface AddressDAO {

	// Create
	public Long createAddress(Address address, Customer customer);
	
	// Read
	public List<Address> findAllAddresses();
	public Address findAddressById(Long addressId);
	public List<Address> findAddressesByCustomer(Customer customer);
		
	// Update
	public void updateAddress (Address address, Customer customer);
		
	// Delete
	public void deleteAddress(Address address);
}
