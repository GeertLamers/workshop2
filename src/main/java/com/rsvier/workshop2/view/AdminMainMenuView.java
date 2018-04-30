package com.rsvier.workshop2.view;

import org.springframework.stereotype.Component;

@Component
public class AdminMainMenuView extends View<AdminMainMenuView> {

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
		System.out.println("Administrator Menu \n");
	}
	
	public void displayMenuOptions() {
		System.out.print("1. Manage user accounts \n" +
						 "2. Manage customers \n" +
						 "3. Manage products \n"+
						 "4. Manage orders \n" +
						 "0. Exit program \n\n");
	}
}
