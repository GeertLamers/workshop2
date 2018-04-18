package com.rsvier.workshop2.controller;

import com.rsvier.workshop2.useraccounts.Account;
import com.rsvier.workshop2.useraccounts.AccountDAOImpl;
import com.rsvier.workshop2.useraccounts.UserCreationController;
import com.rsvier.workshop2.useraccounts.UserCreationView;
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
				nextController = new UserCreationController(new UserCreationView());
				return;
			}
			nextController = new AdminMainMenuController(new AdminMainMenuView());
			username = userInput[0];
			String password = userInput[1];
			if (new AccountDAOImpl(entityManager, Account.class).login(username, password)) break;
			System.out.println("Incorrect username or password. Please try again or press 0 to exit.");

		}
	}
}