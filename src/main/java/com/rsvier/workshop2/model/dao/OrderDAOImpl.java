
package com.rsvier.workshop2.model.dao;

import com.rsvier.workshop2.model.Order;
import com.rsvier.workshop2.model.Customer;
import com.rsvier.workshop2.database.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class OrderDAOImpl implements OrderDAO {

	private String query;
	private Logger logger = Logger.getLogger(OrderDAOImpl.class.getName());

	@Override
	public int createOrder(Order order, Customer customer) {
		query = "INSERT INTO `order` (total_price, total_products, shipped_status, customerID, order_completed)" + 
									"VALUES (?, ?, ?, ?, ?);";
		try (Connection conn = DataSource.getConnection();
				PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);) {
			logger.info("Connected to database.");
			stmt.setBigDecimal(1, order.getOrderPriceTotal());
			stmt.setInt(2, order.getOrderItemsTotal());
			stmt.setBoolean(3,  order.isShipped());
			stmt.setLong(4, customer.getCustomerId());
			stmt.setBoolean(5, order.isCompleted());
			stmt.executeUpdate();
			int newOrderId = 0;
			ResultSet rs = stmt.getGeneratedKeys(); 
			if (rs.next()) {
				newOrderId = rs.getInt(1);
				logger.info("Successfully created new order.");
				return newOrderId;
			}
		} catch (SQLException e) {
			logger.info("Creating new order failed.");
		}
		return 0;
	}
	
	@Override
	public Order findOrderById(Long orderId) {
		Order foundOrder = new Order();
		Customer customer = new Customer();
		query = "SELECT * FROM `order` WHERE id=?";
		try (Connection conn = DataSource.getConnection();
				PreparedStatement stmt = conn.prepareStatement(query);) {
			logger.info("Connected to database.");
			stmt.setLong(1, orderId);
			try (ResultSet rs = stmt.executeQuery();) {
				if(rs.next()) {
					foundOrder.setOrderId(Long.valueOf(rs.getInt(1)));
					foundOrder.setOrderPriceTotal(rs.getBigDecimal(2));
					foundOrder.setOrderItemsTotal(rs.getInt(3));
					foundOrder.setShipped(rs.getBoolean(4));
					foundOrder.setCompleted(rs.getBoolean(6));
					// Assigns a customer to the order by its id
					customer.setCustomerId(Long.valueOf(rs.getInt(5)));
					foundOrder.setCustomerOfOrder(customer);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return foundOrder;
	}

	@Override
	public List<Order> findAllOrders() {
		List<Order> list = new ArrayList<Order>();
		Customer customer = new Customer();
		query = "SELECT * FROM `order`;";
		try (Connection conn = DataSource.getConnection();
				PreparedStatement stmt = conn.prepareStatement(query);) {
			logger.info("Connected to database.");
			try (ResultSet rs = stmt.executeQuery();) {
				while(rs.next()) {
					Order order = new Order();
					order.setOrderId(Long.valueOf(rs.getInt(1)));
					order.setOrderPriceTotal(rs.getBigDecimal(2));
					order.setOrderItemsTotal(rs.getInt(3));
					order.setShipped(rs.getBoolean(4));
					order.setCompleted(rs.getBoolean(6));
					// Assigns a new customer to the order by its id
					customer.setCustomerId(Long.valueOf(rs.getInt(5)));
					order.setCustomerOfOrder(customer);
					list.add(order);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (SQLException e) {
			logger.info("Could not retrieve orders.");
			e.printStackTrace();
		} 
		return list;
	}
	
	@Override
	public List<Order> findAllOrdersOfCustomer(Customer customer) {
		List<Order> list = new ArrayList<Order>();
		query = "SELECT * FROM `order` WHERE customerID = ?";
		try (Connection conn = DataSource.getConnection();
				PreparedStatement stmt = conn.prepareStatement(query);) {
			logger.info("Conneted to database.");
			stmt.setLong(1, customer.getCustomerId());
			try (ResultSet rs = stmt.executeQuery();) {
				while(rs.next()) {
					Order order = new Order();
					order.setCustomerOfOrder(customer);
					order.setOrderId(Long.valueOf(rs.getInt(1)));
					order.setOrderPriceTotal(rs.getBigDecimal(2));
					order.setOrderItemsTotal(rs.getInt(3));
					order.setShipped(rs.getBoolean(4));
					order.setCompleted(rs.getBoolean(6));
					list.add(order);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (SQLException e) {
			logger.info("Could not retrieve orders.");
			e.printStackTrace();
		} 
		return list;
	}
	
	@Override
	public List<Order> findCompletedOrders() {
		List<Order> listOfCompletedOrders = new ArrayList<>();
		query = "SELECT * FROM `order` WHERE order_completed = TRUE";
		try (Connection conn = DataSource.getConnection();
				PreparedStatement stmt = conn.prepareStatement(query);) {
			logger.info("Connected to database.");
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				Order foundOrder = new Order();
				foundOrder.setOrderId(Long.valueOf(rs.getInt(1)));
				foundOrder.setOrderPriceTotal(rs.getBigDecimal(2));
				foundOrder.setOrderItemsTotal(rs.getInt(3));
				foundOrder.setShipped(rs.getBoolean(4));
				
				// Links a customer object to the order via its customerId
				Customer customer = new Customer();
				customer.setCustomerId(Long.valueOf(rs.getInt(5)));
				foundOrder.setCustomerOfOrder(customer);
				
				foundOrder.setCompleted(rs.getBoolean(6));
				listOfCompletedOrders.add(foundOrder);
			}
		} catch (SQLException e) {
			logger.info("Failed to retrieve the list of completed orders.");
			e.printStackTrace();
		}
		return listOfCompletedOrders;
	}

	// TODO Fix
	@Override
	public List<Order> findPendingOrders() {
		List<Order> listOfPendingOrders = new ArrayList<>();
		query = "SELECT * FROM `order` WHERE order_completed = FALSE";
		try (Connection conn = DataSource.getConnection();
				PreparedStatement stmt = conn.prepareStatement(query);) {    
			ResultSet rs = stmt.executeQuery();
			logger.info("Connected to database.");
			while(rs.next()) {
				Order foundOrder = new Order();
				foundOrder.setOrderId(Long.valueOf(rs.getInt(1)));
				foundOrder.setOrderPriceTotal(rs.getBigDecimal(2));
				foundOrder.setOrderItemsTotal(rs.getInt(3));
				foundOrder.setShipped(rs.getBoolean(4));
				
				// Links a customer object to the order via its customerId
				Customer customer = new Customer();
				customer.setCustomerId(Long.valueOf(rs.getInt(5)));
				foundOrder.setCustomerOfOrder(customer);
				
				foundOrder.setCompleted(rs.getBoolean(6));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			logger.info("Failed to retrieve the list of pending orders.");
		}
		return listOfPendingOrders;
	}
	
	@Override
	public boolean isOrderStoredWithId(Long orderId) {
		boolean isStored = false;
		query = "SELECT * FROM `order` WHERE id=?";
		query = "SELECT * FROM product WHERE id=?";
		try (Connection conn = DataSource.getConnection();
				PreparedStatement stmt = conn.prepareStatement(query);) {
			logger.info("Connected to database.");	    
			stmt.setLong(1, orderId);
			ResultSet rs = stmt.executeQuery();
			if (rs.next())
				isStored = true;
			else
				isStored = false;
		} catch (Exception e) {
			logger.info("No order found.");
			e.printStackTrace();
		}
		return isStored;
	}

	@Override
	public boolean updateOrder(Order order, Customer customer) {
		query = "UPDATE `order` SET total_price = ?, total_products = ?," +
				"shipped_status = ?, customerID = ?, order_completed = ?" +
				"WHERE id=?";
		try (Connection conn = DataSource.getConnection();
				PreparedStatement stmt = conn.prepareStatement(query);) {
			logger.info("Connected to database.");
			stmt.setBigDecimal(1, order.getOrderPriceTotal());
			stmt.setInt(2, order.getOrderItemsTotal());
			stmt.setBoolean(3, order.isShipped());
			stmt.setLong(4, customer.getCustomerId());
			stmt.setBoolean(5,  order.isCompleted());
			stmt.executeUpdate();
		} catch (SQLException e) {
			logger.info("Updating order failed.");
			e.printStackTrace();
			return false;
		} 
		return true;
	}

	@Override
	public boolean deleteOrder(Order order) {
		// This currently deletes OrderLineItems associated with the order as well via cascade
		// Don't currently know how to circumvent the constraint - to be improved
		query = "DELETE * FROM `order` WHERE id=?";
		try (Connection conn = DataSource.getConnection();
				PreparedStatement stmt = conn.prepareStatement(query);) {
			logger.info("Connected to database.");
			stmt.setLong(1, order.getOrderId());
			stmt.executeUpdate();
		} catch (SQLException e) {
			logger.info("Deleting order failed.");
			e.printStackTrace();
			return false;
		} 
		return true;
	}
}
