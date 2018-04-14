package com.rsvier.workshop2.controller;


import com.rsvier.workshop2.customer.CustomerController;
import com.rsvier.workshop2.customer.CustomerView;
import com.rsvier.workshop2.order.OrderController;
import com.rsvier.workshop2.order.OrderView;
import com.rsvier.workshop2.product.ProductController;
import com.rsvier.workshop2.product.ProductView;
import com.rsvier.workshop2.useraccounts.UserMainMenuView;
import com.rsvier.workshop2.view.*;

public class UserMainMenuController extends Controller {
	
	public UserMainMenuController(View<UserMainMenuView> currentMenu) {
		this.currentMenu = currentMenu;
	}

	@Override
	public void runView() {
		currentMenu.displayMenu();
		int userMenuChoice = Integer.parseInt(currentMenu.askUserForMenuChoice());
		switch (userMenuChoice) {
			// User chose to manage customers
			case 1: nextController = new CustomerController(new CustomerView());
					break;
			// User chose to manage products
			case 2: nextController = new ProductController(new ProductView()) ;
					break;
			// User chose to manage orders
			case 3: nextController = new OrderController(new OrderView());
					break;
		}
	}
}
