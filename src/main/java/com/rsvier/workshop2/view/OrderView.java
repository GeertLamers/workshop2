package com.rsvier.workshop2.view;

import java.util.ArrayList;

import com.rsvier.workshop2.model.Order;
import com.rsvier.workshop2.model.OrderLineItem;
import com.rsvier.workshop2.model.Product;

public class OrderView extends View {
	
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
						 "2. Find all orders of a customer \n" +
						 "3. View pending orders only \n" +
						 "4. View completed orders only \n" +
						 "5. Find a specific order \n" +
						 "6. Add new order \n" +
						 "7. Update order \n" +
						 "8. Delete order \n" +
						 "9. Return to main menu \n" +
						 "0. Exit program \n" +
						 "\n");
	}
	
	public void displayOrderPropertiesHeader() {
		System.out.printf("%-5d %-5d %-5d %-7d.2 %-7s \n",
						   "id",
						   "customer id",
						   "# items",
						   "total price",
						   "shipping status");
	}
	
	public void displayOrderProperties(Order order) {
		System.out.printf("%-5d %-5d %-5d %-7d.2 %-7s \n",
						   order.getOrderId(),
						   order.getCustomerOfOrder().getCustomerId(),
						   order.getOrderItemsTotal(),
						   order.getOrderPriceTotal(),
						   order.isShipped());
	}
	
	public void displayAllOrders(ArrayList<Order> allOrders) {
		for (Order order : allOrders) {
			displayOrderProperties(order);
		}
	}
	
	public void displayOrderWithItemDetails(Order order, ArrayList<OrderLineItem> orderItemList) {
		// Displays the overview-details of the order
		displayOrderPropertiesHeader();
		displayDivider();
		displayOrderProperties(order);
		// Displays what items the order contains
		displayOrderItemListHeader();
		displayDivider();
		displayOrderItemListDetails(orderItemList);
	}
	
	public void displayOrderItemListHeader() {
		System.out.printf("%-30s %-8s %-10s \n",
				   "product name",
				   "quantity",
				   "price per product");
	}
	
	public void displayOrderItemListDetails(ArrayList<OrderLineItem> listOfItems) {
		for (OrderLineItem item : listOfItems) {
			System.out.printf("%-30s %-8d %-10s \n",
					   item.getProduct().getProductName(),
					   item.getProductQuantity(),
					   (item.getProduct().getPrice()).toString());
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
	
	public void displayAddressList() {
		displayAddressPropertiesHeader();
		displayAddressDetails();
	}
	
	public void displayAddressPropertiesHeader() {
		//TODO write code
	}
	
	public void displayAddressDetails() {
		//TODO write code
	}
}
