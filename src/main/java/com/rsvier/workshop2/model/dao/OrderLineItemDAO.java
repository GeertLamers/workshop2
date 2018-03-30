package com.rsvier.workshop2.model.dao;

import java.util.List;

import com.rsvier.workshop2.model.OrderLineItem;

public interface OrderLineItemDAO {
	
	// Create
	public boolean createOrderLineItem(OrderLineItem orderLineItem);
	
	// Read
	public List<OrderLineItem> getAllOrderLineItemsByOrderId(Long orderId);
	public List<OrderLineItem> getAllOrderLineItemsForProductId (Long productId);
	
	// Update
	public void updateOrderLineItem(OrderLineItem orderLineItem);
	
	// Delete
	public void deleteSingleOrderLineItem(OrderLineItem orderLineItem);
	public void deleteAllLineItemsFromOrder(Long orderId);
}
