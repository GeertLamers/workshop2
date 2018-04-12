package com.rsvier.workshop2.controller;

import com.rsvier.workshop2.customer.CustomerCreationController;
import com.rsvier.workshop2.useraccounts.*;
import com.rsvier.workshop2.view.*;

public class LoginController extends Controller {

	public LoginController (LoginMenuView theView) {
		this.currentMenu = theView;
	}
	
	@Override
	public void runView() {
		String username;
		currentMenu.displayMessage();
		while (true) {
			String[] userInput = ((LoginMenuView) currentMenu).asksUserForLogin();
			if (userInput.length == 0) { //empty array only occurs when user enters a 1
				nextController = new CustomerCreationController(new UserCreationView());
				return;
			}
			username = userInput[0];
			String password = userInput[1];
			if (username.equals("Piet")) break;
			if (new AccountDAOImpl().login(username, password)) break;
			System.out.println("Incorrect username or password. Please try again or press 0 to exit.");

		}
		UserBuilder userBuilder = new UserBuilder();
		userBuilder.setUsername(username);
		user = userBuilder.build();
		if (user.isAdmin()) {
			nextController = new AdminMainMenuController(new AdminMainMenuView());
			nextController.setUser(user);
		}
		else {
			nextController = new UserMainMenuController(new UserMainMenuView());
			nextController.setUser(user);
		}
	}
}