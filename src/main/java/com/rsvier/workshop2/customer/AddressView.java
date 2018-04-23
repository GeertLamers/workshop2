package com.rsvier.workshop2.customer;

import java.util.ArrayList;

import com.rsvier.workshop2.view.View;

public class AddressView extends View<AddressView> {
	
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
		System.out.println("Address Menu \n");
	}
	
	public void displayMenuOptions() {
		System.out.print("1. Find an address by its ID \n" +
						 "2. Find an address by postal code & house number (currently N/A) \n" +
						 "3. Find an address by customer ID \n" +
						 "4. Add new address \n" +
						 "5. Update address details \n" +
						 "6. Delete address \n" +
						 "7. Return to customer menu \n" +
						 "9. Return to main menu \n" +
						 "0. Exit program \n" +
						 "\n");
	}
	
	public void displayAddressDetailsHeader() {
		System.out.printf("%-5s %-12s %-7s %-7s %-30s %-20s %-10s \n",
						   "id",
						   "postal code",
						   "house #",
						   "add.",
						   "street",
						   "city",
						   "type");
	}
	
	public void displayAddressDetails(Address address) {
		System.out.printf("%-5s %-12s %-7s %-7s %-30s %-20s %-10s \n",
						  address.getAddressId(),
						  address.getPostalCode(),
						  address.getHouseNumber(),
						  address.getHouseNumberAddition(),
						  address.getStreet(),
						  address.getCity(),
						  address.getAddressType());
	}
	
	public void displayAllAddresses(ArrayList<Address> allAddresses) {
		for (Address address : allAddresses) {
			displayAddressDetails(address);
		}
	}
	
	public void displayAddressUpdateMenu() {
		System.out.print("\n What would you like to update? \n" +
						 "\n" +
						 "1. Postal code \n" +
						 "2. House number \n" +
						 "3. Street \n" +
						 "4. City \n" +
						 "5. Address type \n" +
						 "9. Return to the address menu \n" + 
						 "\n");
	}
	
	public void askWhichAddress() {
		System.out.print("Please provide an ID-number or postal code of an address.");
	}

}
