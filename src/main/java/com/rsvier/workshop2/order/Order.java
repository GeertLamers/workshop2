package com.rsvier.workshop2.order;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.rsvier.workshop2.customer.Customer;

@Entity
@Table (name = "Orders")
public class Order {
	
	@Id
	@Column(name = "id",
			nullable = false,
			unique = true)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long orderId;
	
	@ManyToOne(optional = false)
	private Customer customer;
    
	@OneToMany(mappedBy = "parentOrder",
			   fetch = FetchType.EAGER,
			   cascade = CascadeType.ALL,
			   orphanRemoval = true)
	@Fetch(value = FetchMode.SUBSELECT)
	@Column(nullable = false)
	private List<OrderLineItem> itemsInOrder;
	private BigDecimal orderPriceTotal;
	private int orderItemsTotal;
	private boolean shipped;
	private boolean completed;
	
	// ======================================================== //
	
	public Order() {
		itemsInOrder = new ArrayList<OrderLineItem>();
	}
	
	public Order(Customer customer,
				 ArrayList<OrderLineItem> itemsInOrder) {
		this.customer = customer;
		this.itemsInOrder = itemsInOrder;
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
	
	public void setOrderItems(List<OrderLineItem> lineItems) {
		if(itemsInOrder == null) {
			this.itemsInOrder = lineItems;	
		} else {
			this.itemsInOrder.retainAll(lineItems);
			this.itemsInOrder.addAll(lineItems);
		}
	}	
	
	public List<OrderLineItem> getItemsInOrder() {
		return itemsInOrder;
	}

	public BigDecimal getOrderPriceTotal() {
		return orderPriceTotal;
	}

	public void setOrderPriceTotal(List<OrderLineItem> lineItems) {
		BigDecimal orderPriceTotal = BigDecimal.ZERO;
		ListIterator<OrderLineItem> it = lineItems.listIterator();
		while(it.hasNext()) {
			OrderLineItem lineItem = it.next();
			BigDecimal itemPrice = lineItem.getProduct().getPrice();
			int itemQuantity = lineItem.getProductQuantity();
			orderPriceTotal = orderPriceTotal.add(itemPrice.multiply(new BigDecimal(itemQuantity)));
		}
		this.orderPriceTotal = orderPriceTotal;
	}

	public int getOrderItemsTotal() {
		return orderItemsTotal;
	}

	public void setOrderItemsTotal(List<OrderLineItem> lineItems) {
		ListIterator<OrderLineItem> it = lineItems.listIterator();
		int totalOfLineItemQuantities = 0;
		while(it.hasNext()) {
			OrderLineItem lineItem = it.next();
			int itemQuantity = lineItem.getProductQuantity();
			totalOfLineItemQuantities += itemQuantity;
		}
		this.orderItemsTotal = totalOfLineItemQuantities;
	}

	public boolean isShipped() {
		return shipped;
	}

	public void setShipped(boolean shipped) {
		this.shipped = shipped;
	}

	public boolean isCompleted() {
		return completed;
	}

	public void setCompleted(boolean completed) {
		this.completed = completed;
	}

	@Override
	public String toString() {
		return "Order [orderId=" + orderId + ", customer=" + customer + ", itemsInOrder=" + itemsInOrder
				+ ", orderPriceTotal=" + orderPriceTotal + ", orderItemsTotal=" + orderItemsTotal + ", shipped="
				+ shipped + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((customer == null) ? 0 : customer.hashCode());
		result = prime * result + ((itemsInOrder == null) ? 0 : itemsInOrder.hashCode());
		return result;
	}

	/* Orders are the same when their contents and customer are identical */
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
		return true;
	}
}
