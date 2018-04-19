package com.rsvier.workshop2.customer;

import java.util.ArrayList;
import java.util.Scanner;

import com.rsvier.workshop2.controller.AdminMainMenuController;
import com.rsvier.workshop2.controller.Controller;
import com.rsvier.workshop2.controller.UserMainMenuController;
import com.rsvier.workshop2.useraccounts.Account;
import com.rsvier.workshop2.useraccounts.UserMainMenuView;
import com.rsvier.workshop2.utility.Validator;
import com.rsvier.workshop2.view.AdminMainMenuView;

public class CustomerController extends Controller {
	
	private CustomerDAOImpl customerModel;
	private Scanner input = new Scanner(System.in);
	private CustomerView currentMenu;
	
	public CustomerController(CustomerView theView) {
		this.currentMenu = theView;
	}
	
	@Override
	public void runView() {
		currentMenu.displayMenu();
		int userMenuChoice = Integer.parseInt(currentMenu.askUserForMenuChoice());
		switch (userMenuChoice) {
		case 1: findAllCustomers();
				break;
		case 2: findCustomer();
				break;
		case 3: addNewCustomer();
				break;
		case 4: updateCustomerPersonalia();
				break;
		case 5: updateCustomerAddresses();
				break;
		case 6: deleteCustomer();
				break;
		case 9: // Returns to main menu
				if (loggedInUser.getOwnerType().equals("ADMIN")) {
					nextController = new AdminMainMenuController(new AdminMainMenuView());
				}
				else {
					nextController = new UserMainMenuController(new UserMainMenuView());
				}
				break;
		default: System.out.println("Not a valid option.");
				currentMenu.displayMenu();
		}
	}

	public void findAllCustomers() {
		ArrayList<Customer> allCustomers = (ArrayList<Customer>) customerDao.findAllCustomers();
		currentMenu.displayCustomerDetailsHeader();
		currentMenu.displayDivider();
		currentMenu.displayAllCustomers(allCustomers);
	}
	
	public void findCustomer() {
		Customer foundCustomer = new Customer();
		currentMenu.displayCanFindByIdAndName();
		String findThisCustomer = currentMenu.askUserForMenuChoice();
		if (Validator.isAnInt(findThisCustomer)) {
			foundCustomer = customerDao.findCustomerById(Long.valueOf(findThisCustomer));
		} else {
			//TODO - account for the possibility of multiple customers with the same last name in DAO
			foundCustomer = customerDao.findCustomerByLastName(findThisCustomer);
		}
		currentMenu.displayCustomerDetailsHeader();
		currentMenu.displayDivider();
		currentMenu.displayCustomerDetails(foundCustomer);
	}
	
	public void addNewCustomer() {
		
	}
	
	public void updateCustomerPersonalia() {
		
	}
	
	public void updateCustomerAddresses() {
		
	}
	
	public void deleteCustomer() {
		
	}
}
