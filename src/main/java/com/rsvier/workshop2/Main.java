package com.rsvier.workshop2;

import com.rsvier.workshop2.controller.Controller;
import com.rsvier.workshop2.controller.LoginController;
import com.rsvier.workshop2.useraccounts.UserMainMenuView;
import com.rsvier.workshop2.utility.DatabaseBuilderDAOImpl;
import com.rsvier.workshop2.view.*;

public class Main {

	public static void main (String args[])  {

		System.out.println("Initialize database?");
		if (new UserMainMenuView().asksUserYesOrNo()) {
			if (new DatabaseBuilderDAOImpl().createMYSQLDatabase()) {
				System.out.println("Database initalized");
			}
			else {
				System.out.println("Failed to initalize database");
			}
		}
		
		Controller currentController = new LoginController(new LoginMenuView());
		currentController.runView(); // run next menu
		
		while(true) { //infinite loop until the user stops the program
			currentController = currentController.getNextController(); // get next menu & model from the users choice
			currentController.runView(); // run next menu
		}
	}
}