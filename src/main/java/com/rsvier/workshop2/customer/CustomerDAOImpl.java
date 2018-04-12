package com.rsvier.workshop2.customer;

import com.rsvier.workshop2.product.Product;
import com.rsvier.workshop2.utility.DataSource;
import com.rsvier.workshop2.utility.GenericDAOImpl;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.persistence.EntityManager;


public class CustomerDAOImpl extends GenericDAOImpl<Customer> {

	protected CustomerDAOImpl(EntityManager em, Class<Customer> entityClass) {
		super(em, entityClass);
	}

	public boolean createCustomer(Customer customer) {
		return false;
	}

	public List<Customer> findAllCustomers() {
		List<Customer> list = new ArrayList<Customer>();
		query = "SELECT * FROM customer;";
		try (Connection conn = DataSource.getConnection();
				PreparedStatement stmt = conn.prepareStatement(query);
				ResultSet rs = stmt.executeQuery();) {
			logger.info("Connected to database.");	      
			while(rs.next()) {
				Customer customer = new Customer();
				customer.setCustomerId(Long.valueOf(rs.getInt(1)));
				customer.setFirstName(rs.getString(2));
				customer.setLastName(rs.getString(3));
				customer.setLastNamePreposition(rs.getString(4));
				customer.setEmail(rs.getString(5));
				customer.setPhoneNumber(rs.getString(6));
				list.add(customer);
			}
			logger.info("Total customers:" + rs.getRow());
		} catch (SQLException e) {
			logger.info("Could not retrieve customers");
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public Customer findCustomerById(Long customerId) {
		Customer foundCustomer = new Customer();
		query = "SELECT * FROM customer WHERE id=?";
		try (Connection conn = DataSource.getConnection();
				PreparedStatement stmt = conn.prepareStatement(query);) {
			logger.info("Connected to database.");
			stmt.setLong(1, customerId);
			try (ResultSet rs = stmt.executeQuery();) {
				if(rs.next()) {
					foundCustomer.setCustomerId(Long.valueOf(rs.getInt(1)));
					foundCustomer.setFirstName(rs.getString(2));
					foundCustomer.setLastName(rs.getString(3));
					foundCustomer.setLastNamePreposition(rs.getString(4));
					foundCustomer.setEmail(rs.getString(5));
					foundCustomer.setPhoneNumber(rs.getString(6));
				} 
			}catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (SQLException e) {
			logger.info("No customer found");
			e.printStackTrace();
		} 
		return foundCustomer;
	}

	@Override
	public Customer findCustomerByLastName(String lastName) {
		Customer foundCustomer = new Customer();
		query = "SELECT * FROM customer WHERE last_name=?";
		try (Connection conn = DataSource.getConnection();
				PreparedStatement stmt = conn.prepareStatement(query);) {
			logger.info("Connected to database.");
			stmt.setString(1, lastName);
			try (ResultSet rs = stmt.executeQuery();) {
				if(rs.next()) {
					foundCustomer.setCustomerId(Long.valueOf(rs.getInt(1)));
					foundCustomer.setFirstName(rs.getString(2));
					foundCustomer.setLastName(rs.getString(3));
					foundCustomer.setLastNamePreposition(rs.getString(4));
					foundCustomer.setEmail(rs.getString(5));
					foundCustomer.setPhoneNumber(rs.getString(6));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return foundCustomer;
	}

	@Override
	public void updateCustomer(Customer customer) {
		query = "UPDATE customer SET first_name = ?, last_name = ?, last_name_preposition = ?, " +
				"email = ?, phone_number = ? WHERE id=?";
		try (Connection conn = DataSource.getConnection();
				PreparedStatement stmt = conn.prepareStatement(query);) {
			logger.info("Connected to database.");
			stmt.setString(1, customer.getFirstName());
			stmt.setString(2, customer.getLastName());
			stmt.setString(3, customer.getLastNamePreposition());
			stmt.setString(4, customer.getEmail());
			stmt.setString(5, customer.getPhoneNumber());
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} 
	}

	//TODO Should probably make a check for whether customer has active orders before deltion
	@Override
	public void deleteCustomer(Customer customer) {
		query = "DELETE FROM customer WHERE id=?";
		try (Connection conn = DataSource.getConnection();
				PreparedStatement stmt = conn.prepareStatement(query);) {
			logger.info("Connected to database.");
			stmt.setLong(1, customer.getCustomerId());
			stmt.executeUpdate();
		} catch (SQLException e) {
			logger.info("Could not delete customer");
			e.printStackTrace();
		} 
	}

	@Override
	public List<Product> findALl() {
		// TODO Auto-generated method stub
		return null;
	}
}
