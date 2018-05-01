package com.rsvier.workshop2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import com.rsvier.workshop2.useraccounts.Account;
import com.rsvier.workshop2.useraccounts.AccountDAOImpl;
import com.rsvier.workshop2.useraccounts.UserCreationController;
import com.rsvier.workshop2.view.*;

@Component
@ComponentScan
public class LoginController extends Controller {

	@Autowired
	private LoginMenuView currentMenu;
	@Autowired
	private AdminMainMenuController adminMainMenuController;
	
	// User login functionality currently unused
	// @Autowired
	// private UserMainMenuController userMainMenuController;
	
	@Autowired
	private UserCreationController userCreationController;
	
	@Override
	public void runView() {
		String username;
		currentMenu.displayMessage();
		while (true) {
			String[] userInput = currentMenu.asksUserForLogin();
			if (userInput.length == 0) { //empty array only occurs when user enters a 1
				nextController = userCreationController;
				return;
			}
			nextController = adminMainMenuController;
			username = userInput[0];
			String password = userInput[1];
			if (new AccountDAOImpl(entityManager, Account.class).login(username, password)) {
				loggedInUser = (new AccountDAOImpl(entityManager, Account.class).findByUsername(username));
				break;
			}
			System.out.println("Incorrect username or password. Please try again or press 0 to exit.");
		}
	}
}