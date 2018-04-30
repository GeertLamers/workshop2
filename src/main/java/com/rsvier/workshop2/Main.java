package com.rsvier.workshop2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.rsvier.workshop2.controller.Controller;
import com.rsvier.workshop2.controller.LoginController;
import com.rsvier.workshop2.useraccounts.Account;
import com.rsvier.workshop2.useraccounts.UserMainMenuView;
import com.rsvier.workshop2.utility.DatabaseBuilderDAOImpl;
import com.rsvier.workshop2.view.*;

@ComponentScan
@Configuration
public class Main {
	@Autowired @Qualifier("loginController")
	Controller currentController;

	public static void main (String args[])  {
		initializeDatabase();
		Main main = new Main();
		main.run();
	}
	
	@SuppressWarnings("resource")
	private void run() {
		ApplicationContext context = new AnnotationConfigApplicationContext(Main.class, Controller.class, View.class);
		currentController = context.getBean(LoginController.class);
		currentController.runView(); // run next menu
		Account loggedInUser = ((LoginController) currentController).getLoggedInUser();
		
		while(true) { //infinite loop until the user stops the program
			currentController = currentController.getNextController(); // get next menu & model from the users choice
			currentController.setLoggedInUser(loggedInUser);
			currentController.runView(); // run next menu
		}
	}
	
	private static void initializeDatabase() {
		System.out.println("Initialize database?");
		if (new UserMainMenuView().asksUserYesOrNo()) {
			if (new DatabaseBuilderDAOImpl().createMYSQLDatabase()) {
				System.out.println("Database initalized");
			}
			else {
				System.out.println("Failed to initalize database");
			}
		}
	}
	
}