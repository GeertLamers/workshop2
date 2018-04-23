package com.rsvier.workshop2.order;

import java.util.List;

import com.rsvier.workshop2.view.View;

public class OrderView extends View<OrderView> {
	
	@Override
	public void displayMessage() {
	}
	
	@Override
	public void displayMenu() {
		displayMenuHeader();
		displayDivider();
		displayMenuOptions();
	}
	
	public void displayMenuHeader() {
		System.out.println("Order Menu \n");
	}
	
	public void displayMenuOptions() {
		System.out.print("1. View all orders \n" +
						 "2. Find all orders of a customer (currently N/A) \n" +
						 "3. View pending orders only (currently N/A) \n" +
						 "4. View completed orders only (currently N/A) \n" +
						 "5. Find a specific order \n" +
						 "6. Add new order \n" +
						 "7. Update order \n" +
						 "8. Delete order \n" +
						 "9. Return to main menu \n" +
						 "0. Exit program \n" +
						 "\n");
	}
	
	public void displayOrderPropertiesHeader() {
		System.out.printf("%-7s %-7s %-10s %-12s %-12s \n",
						   "id",
						   "customer id",
						   "# items",
						   "total price",
						   "shipping status");
	}
	
	public void displayOrderProperties(Order order) {
		System.out.printf("%-7d %-7d %-10d %-12d.2 %-12s \n",
						   order.getOrderId(),
						   order.getCustomerOfOrder().getCustomerId(),
						   order.getOrderItemsTotal(),
						   order.getOrderPriceTotal(),
						   order.isShipped());
	}
	
	public void displayAllOrders(List<Order> allOrders) {
		for (Order order : allOrders) {
			displayOrderProperties(order);
		}
	}
	
	public void displayOrderWithItemDetails(Order order) {
		// Displays the overview-details of the order
		displayOrderPropertiesHeader();
		displayDivider();
		displayOrderProperties(order);
		// Displays what items the order contains
		displayOrderItemListHeader();
		displayDivider();
		displayOrderItemListDetails(order.getItemsInOrder());
	}
	
	public void displayOrderItemListHeader() {
		System.out.printf("%-30s %-12s %-12s \n",
				   "product name",
				   "quantity",
				   "price per product");
	}
	
	public void displayOrderItemListDetails(List<OrderLineItem> arrayList) {
		for (OrderLineItem item : arrayList) {
			System.out.printf("%-30s %-12d %-12s \n",
					   item.getProduct().getName(),
					   item.getProductQuantity(),
					   (item.getProduct().getPrice()).toString()); // converts a BigDecimal into a String object
		}
	}
	
	public void displayOrderUpdateMenu() {
		System.out.print("What would you like to update? \n" +
						 "\n" +
						 "1. Items in order \n" +
						 "4. Shipping status \n" +
						 "5. Associated customer \n" +
						 "6. Associated shipping address \n" +
						 "9. Return to the order menu \n" +
						 "\n");
	}
}
