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
	private AdminMainMenuView currentMenu;
	
	@Autowired @Qualifier("customerController")
	private CustomerController customerController;
	
	@Autowired @Qualifier("adminUserOverviewController")
	private AdminUserOverviewController adminUserOverviewController;
	
	@Autowired @Qualifier("productController")
	private ProductController productController;
	
	@Autowired @Qualifier("orderController")
	private OrderController orderController;
	
	@Override
	public void runView() {
		currentMenu.displayMenu();
		boolean validChoice = false;
		while (!validChoice) {
			int userMenuChoice = Integer.parseInt(currentMenu.askUserForMenuChoice());
			// If input was 0 the View.askUserForInput method handles elegant program exit
			switch (userMenuChoice) {
				// User chose to manage accounts
				case 1: nextController = adminUserOverviewController;
						validChoice = true;
						break;
				// User chose to manage customers
				case 2: nextController = customerController;
						validChoice = true;
						break;
				// User chose to manage products
				case 3: nextController = productController;
						validChoice = true;
						break;
				// User chose to manage orders
				case 4: nextController = orderController;
						validChoice = true;
						break;
				default: System.out.println("Not a valid option.");
						break;
			}
		}
	}
}