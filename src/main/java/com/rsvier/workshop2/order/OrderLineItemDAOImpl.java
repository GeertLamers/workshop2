package com.rsvier.workshop2.order;

import com.rsvier.workshop2.product.Product;
import com.rsvier.workshop2.utility.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class OrderLineItemDAOImpl implements OrderLineItemDAO {

	private String query;	
	private Logger logger = Logger.getLogger(OrderDAOImpl.class.getName());

	@Override
	public boolean createOrderLineItem(OrderLineItem orderLineItem) {
		query = "INSERT INTO order_line_item (productID, orderID, quantity) VALUES (?,?,?);";
		try (Connection conn = DataSource.getConnection();
				PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
				ResultSet rs = stmt.getGeneratedKeys();) {
			logger.info("Connected to database.");
			stmt.setLong(2, orderLineItem.getProduct().getProductId());
			stmt.setLong(3, orderLineItem.getParentOrder().getOrderId());
			stmt.setInt(4, orderLineItem.getProductQuantity());
			stmt.executeUpdate();
			try {
				if (rs.next()) {
					logger.info("Succesfully added new line item.");
					return true;
				}           
			} catch (SQLException e) {
				logger.info("Adding new line item failed.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return false;
	}

	@Override
	public List<OrderLineItem> getAllOrderLineItemsByOrderId(Long orderId) {
		List<OrderLineItem> list = new ArrayList<OrderLineItem>();
		query = "SELECT * FROM order_line_item WHERE orderID=?;";
		try (Connection conn = DataSource.getConnection();
				PreparedStatement stmt = conn.prepareStatement(query);
				ResultSet rs = stmt.executeQuery();) {
			logger.info("Connected to database.");	      
			while(rs.next()) {
				OrderLineItem orderLineItem = new OrderLineItem();
				// Associate a product for each retrieved line item
				Product product = new Product();
				product.setProductId(Long.valueOf(rs.getInt(2)));
				orderLineItem.setProduct(product);
				
				orderLineItem.setProductQuantity(rs.getInt(4));
				list.add(orderLineItem);
			}
			Order order = new Order();
			order.setOrderId(orderId);
			for (OrderLineItem item : list) {
				item.setParentOrder(order);
			}
			logger.info("Total line items:" + rs.getRow());
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return list;
	}
	
	@Override
	public List<OrderLineItem> getAllOrderLineItemsForProductId(Long productId) {
		List<OrderLineItem> list = new ArrayList<OrderLineItem>();
		query = "SELECT * FROM order_line_item WHERE productID=?;";
		try (Connection conn = DataSource.getConnection();
				PreparedStatement stmt = conn.prepareStatement(query);
				ResultSet rs = stmt.executeQuery();) {
			logger.info("Connected to database.");
			while(rs.next()) {
				OrderLineItem orderLineItem = new OrderLineItem();;
				
				// Associate an order for all retrieved line items
				Order order = new Order();;
				order.setOrderId(Long.valueOf(rs.getInt(3)));
				orderLineItem.setParentOrder(order);
		
				orderLineItem.setProductQuantity(rs.getInt(4));
				list.add(orderLineItem);
			}
			Product product = new Product();
			product.setProductId(productId);
			for(OrderLineItem item : list) {
				item.setProduct(product);
			}
			logger.info("Total line items:" + rs.getRow());
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return list;
	}

	@Override
	public void updateOrderLineItem(OrderLineItem orderLineItem) {
		query = "UPDATE orderLineItem SET productID = ?, orderID = ?," +
				"quantity = ?";
		try (Connection conn = DataSource.getConnection();
				PreparedStatement stmt = conn.prepareStatement(query);) {
			logger.info("Connected to database.");
			stmt.setLong(1, orderLineItem.getProduct().getProductId());
			stmt.setLong(2, orderLineItem.getParentOrder().getOrderId());
			stmt.setInt(3, orderLineItem.getProductQuantity());
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} 
	}

	@Override
	public void deleteSingleOrderLineItem(OrderLineItem orderLineItem) {
		query = "DELETE FROM order_line_item WHERE (productID, orderID, quantity) VALUES (?,?,?)";
		try (Connection conn = DataSource.getConnection();
				PreparedStatement stmt = conn.prepareStatement(query);) {
			logger.info("Connected to database.");
			stmt.setLong(1, orderLineItem.getProduct().getProductId());
			stmt.setLong(2, orderLineItem.getParentOrder().getOrderId());
			stmt.setInt(3, orderLineItem.getProductQuantity());
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} 
	}
	
	@Override
	public void deleteAllLineItemsFromOrder (Long orderId) {
		query = "DELETE FROM order_line_item WHERE order_id = " + orderId;
		try (Connection conn = DataSource.getConnection();
				PreparedStatement stmt = conn.prepareStatement(query);) {
			logger.info("Connected to database.");
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
