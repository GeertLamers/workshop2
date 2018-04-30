package com.rsvier.workshop2.customer;

import java.util.ArrayList;

import org.springframework.stereotype.Component;

import com.rsvier.workshop2.view.View;

@Component
public class CustomerView extends View<CustomerView> {
	
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
		System.out.println("Customer Menu \n");
	}
	
	public void displayMenuOptions() {
		System.out.print("1. View customer list \n" +
						 "2. Find a customer \n" +
						 "3. Add new customer and/or account \n" +
						 "4. Update customer details \n" +
						 "5. Update customer address(es) \n" +
						 "6. Delete customer \n" +
						 "9. Return to main menu \n" +
						 "0. Exit program \n" +
						 "\n");
	}
	
	public void displayCustomerDetailsHeader() {
		System.out.printf("%-3s %-20s %-15s %-20s %-25s %-20s \n",
						   "id",
						   "last name",
						   "preposition",
						   "first name",
						   "email",
						   "phone #");
	}
	
	public void displayCustomerDetails(Customer customer) {
		System.out.printf("%-3d %-20s %-15s %-20s %-25s %-20s \n",
						  customer.getCustomerId(),
						  customer.getLastName(),
						  customer.getLastNamePreposition(),
						  customer.getFirstName(),
						  customer.getEmail(),
						  customer.getPhoneNumber());
	}
	
	public void displayAllCustomers(ArrayList<Customer> allCustomers) {
		for (Customer customer : allCustomers) {
			displayCustomerDetails(customer);
		}
	}
	
	public void displayUpdateMenu() {
		System.out.print("What would you like to update? \n" +
						 "\n" +
						 "1. Name \n" +
						 "2. Email \n" +
						 "3. Phone number \n" +
						 "9. Return to customer menu" +
						 "\n");
	}
	
	public void askWhichCustomer() {
		System.out.print("Please provide an ID-number or last name of a customer.");
	}

}
