package com.rsvier.workshop2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import com.rsvier.workshop2.customer.CustomerController;
import com.rsvier.workshop2.order.OrderController;
import com.rsvier.workshop2.product.ProductController;
import com.rsvier.workshop2.useraccounts.UserMainMenuView;

@Component
@ComponentScan
public class UserMainMenuController extends Controller {
	
	@Autowired
	private UserMainMenuView currentMenu;
	@Autowired
	private CustomerController customerController;
	@Autowired
	private ProductController productController;
	@Autowired
	private OrderController orderController;

	@Override
	public void runView() {
		currentMenu.displayMenu();
		boolean validChoice = false;
		while (!validChoice) {
			int userMenuChoice = Integer.parseInt(currentMenu.askUserForMenuChoice());
			// If input was 0 the View.askUserForInput method handles elegant program exit
			switch(userMenuChoice) {
				// User chose to manage customers
				case 1: nextController = customerController;
						validChoice = true;
						break;
				// User chose to manage products
				case 2: nextController = productController;
						validChoice = true;
						break;
				// User chose to manage orders
				case 3: nextController = orderController;
						validChoice = true;
						break;
				default: System.out.println("Not a valid option.");
						break;
			}
		}
	}
}
