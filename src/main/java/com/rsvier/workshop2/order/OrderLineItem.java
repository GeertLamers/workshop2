package com.rsvier.workshop2.order;

import com.rsvier.workshop2.product.Product;

public class OrderLineItem {

	private Order parentOrder;
	private Product product;
	private int productQuantity;
	
	public OrderLineItem() {
	}
	
	public OrderLineItem(Order order,
						 Product product,
						 int productQuantity) {
		this.parentOrder = order;
		this.product = product;
		this.productQuantity = productQuantity;
	}
	
	public Order getParentOrder() {
		return parentOrder;
	}
	
	public void setParentOrder(Order order) {
		this.parentOrder = order;
	}
	
	public Product getProduct() {
		return product;
	}
	
	public void setProduct(Product product) {
		this.product = product;
	}
	
	public int getProductQuantity() {
		return productQuantity;
	}
	
	public void setProductQuantity(int productQuantity) {
		this.productQuantity = productQuantity;
	}

	@Override
	public String toString() {
		return "OrderLineItem [parentOrder=" + parentOrder + ", product=" + product + ", productQuantity="
				+ productQuantity + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((parentOrder == null) ? 0 : parentOrder.hashCode());
		result = prime * result + ((product == null) ? 0 : product.hashCode());
		result = prime * result + productQuantity;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OrderLineItem other = (OrderLineItem) obj;
		if (parentOrder == null) {
			if (other.parentOrder != null)
				return false;
		} else if (!parentOrder.equals(other.parentOrder))
			return false;
		if (product == null) {
			if (other.product != null)
				return false;
		} else if (!product.equals(other.product))
			return false;
		if (productQuantity != other.productQuantity)
			return false;
		return true;
	}
}
