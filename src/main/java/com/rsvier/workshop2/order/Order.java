package com.rsvier.workshop2.order;

import java.math.BigDecimal;
import java.util.ArrayList;

import com.rsvier.workshop2.customer.Address;
import com.rsvier.workshop2.customer.Customer;

public class Order {
	
	private Long orderId;
	private Customer customer;
	private ArrayList<OrderLineItem> itemsInOrder;
	private BigDecimal orderPriceTotal;
	private int orderItemsTotal;
	private boolean shipped;
	private Address shippedTo;
	private boolean completed;
	
	public Order() {
	}
	
	public Order(Customer customer,
				 BigDecimal orderPriceTotal,
				 int orderItemsTotal) {

		this.customer = customer;
		this.orderPriceTotal = orderPriceTotal;
		this.orderItemsTotal = orderItemsTotal;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	
	public Customer getCustomerOfOrder() {
		return customer;
	}

	public void setCustomerOfOrder(Customer customer) {
		this.customer = customer;
	}
	
	public ArrayList<OrderLineItem> getItemsInOrder() {
		return itemsInOrder;
	}

	public BigDecimal getOrderPriceTotal() {
		return orderPriceTotal;
	}

	public void setOrderPriceTotal(BigDecimal orderPriceTotal) {
		this.orderPriceTotal = orderPriceTotal;
	}

	public int getOrderItemsTotal() {
		return orderItemsTotal;
	}

	public void setOrderItemsTotal(int orderItemsTotal) {
		this.orderItemsTotal = orderItemsTotal;
	}

	public boolean isShipped() {
		return shipped;
	}

	public void setShipped(boolean shipped) {
		this.shipped = shipped;
	}

	public Address getShippedTo() {
		return shippedTo;
	}

	public void setShippedTo(Address shippedTo) {
		this.shippedTo = shippedTo;
	}
	
	public boolean isCompleted() {
		return this.completed;
	}
	
	public void setCompleted(boolean completed) {
		this.completed = completed;
	}

	@Override
	public String toString() {
		return "Order [orderId=" + orderId + ", customer=" + customer + ", itemsInOrder=" + itemsInOrder
				+ ", orderPriceTotal=" + orderPriceTotal + ", orderItemsTotal=" + orderItemsTotal + ", shipped="
				+ shipped + ", shippedTo=" + shippedTo + ", completed=" + completed + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((customer == null) ? 0 : customer.hashCode());
		result = prime * result + ((itemsInOrder == null) ? 0 : itemsInOrder.hashCode());
		result = prime * result + ((shippedTo == null) ? 0 : shippedTo.hashCode());
		return result;
	}

	/* Orders are the same when their contents and addressee are identical */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Order other = (Order) obj;
		if (customer == null) {
			if (other.customer != null)
				return false;
		} else if (!customer.equals(other.customer))
			return false;
		if (itemsInOrder == null) {
			if (other.itemsInOrder != null)
				return false;
		} else if (!itemsInOrder.equals(other.itemsInOrder))
			return false;
		if (shippedTo == null) {
			if (other.shippedTo != null)
				return false;
		} else if (!shippedTo.equals(other.shippedTo))
			return false;
		return true;
	}	
}
