package com.rsvier.workshop2.useraccounts;

import org.springframework.stereotype.Component;

import com.rsvier.workshop2.view.View;

@Component
public class UserMainMenuView extends View<UserMainMenuView> {

	@Override
	public void displayMessage() {
		System.out.println("Welcome to the main menu. Press 1 to view your pending and completed orders. Press 2 to view the available products."
				+ "Press 3 to view or edit your account information. Press 0 to exit.");
	}
	
	@Override
	public void displayMenu() {
	}
}
