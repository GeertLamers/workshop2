package com.rsvier.workshop2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import com.rsvier.workshop2.customer.CustomerController;
import com.rsvier.workshop2.customer.CustomerView;
import com.rsvier.workshop2.order.OrderController;
import com.rsvier.workshop2.order.OrderView;
import com.rsvier.workshop2.product.ProductController;
import com.rsvier.workshop2.product.ProductView;
import com.rsvier.workshop2.useraccounts.AdminUserOverview;
import com.rsvier.workshop2.useraccounts.AdminUserOverviewController;
import com.rsvier.workshop2.view.*;

@Component
@ComponentScan
public class AdminMainMenuController extends Controller {
	
	@Autowired @Qualifier("adminMainMenuView")
	public AdminMainMenuView currentMenu;

	@Override
	public void runView() {
		currentMenu.displayMenu();
		boolean validChoice = false;
		while (!validChoice) {
			int userMenuChoice = Integer.parseInt(currentMenu.askUserForMenuChoice());
			// If input was 0 the View.askUserForInput method handles elegant program exit
			switch (userMenuChoice) {
				// User chose to manage accounts
				case 1: nextController = new AdminUserOverviewController();
						validChoice = true;
						break;
				// User chose to manage customers
				case 2: nextController = new CustomerController();
						validChoice = true;
						break;
				// User chose to manage products
				case 3: nextController = new ProductController() ;
						validChoice = true;
						break;
				// User chose to manage orders
				case 4: nextController = new OrderController();
						validChoice = true;
						break;
				default: System.out.println("Not a valid option.");
						break;
			}
		}
	}
}