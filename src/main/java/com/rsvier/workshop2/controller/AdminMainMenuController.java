package com.rsvier.workshop2.controller;

import java.util.*;

import com.rsvier.workshop2.customer.CustomerController;
import com.rsvier.workshop2.customer.CustomerView;
import com.rsvier.workshop2.order.OrderController;
import com.rsvier.workshop2.order.OrderView;
import com.rsvier.workshop2.product.ProductController;
import com.rsvier.workshop2.product.ProductView;
import com.rsvier.workshop2.useraccounts.AdminUserOverview;
import com.rsvier.workshop2.useraccounts.AdminUserOverviewController;
import com.rsvier.workshop2.view.*;

public class AdminMainMenuController extends Controller {
	
	public AdminMainMenuController(View currentMenu) {
		this.currentMenu = currentMenu;
	}

	@Override
	public void runView() {
		menuOptions = new HashMap<Integer, Controller>();
		menuOptions.put(1, new AdminUserOverviewController(new AdminUserOverview()));
		menuOptions.put(2, new CustomerController(new CustomerView()));
		menuOptions.put(3, new ProductController(new ProductView()));
		menuOptions.put(4, new OrderController(new OrderView()));
		
		currentMenu.displayMenu();
		nextController = menuOptions.get(currentMenu.asksUserForMenuChoice(menuOptions));
	}
}