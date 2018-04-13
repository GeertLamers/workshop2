package com.rsvier.workshop2.customer;

import java.util.Scanner;

import javax.persistence.EntityManager;

import com.rsvier.workshop2.controller.AdminMainMenuController;
import com.rsvier.workshop2.controller.Controller;
import com.rsvier.workshop2.controller.UserMainMenuController;
import com.rsvier.workshop2.useraccounts.UserMainMenuView;
import com.rsvier.workshop2.utility.HibernateService;
import com.rsvier.workshop2.view.AdminMainMenuView;

public class AddressController extends Controller {
	
	private AddressView currentMenu;
	private AddressDAOImpl addressModel;
	private EntityManager entityManager = HibernateService.getEntityManager();
	private Scanner input = new Scanner(System.in);
	
	public AddressController(AddressView theView) {
		this.currentMenu = theView;
	}

	@Override
	public void runView() {
		currentMenu.displayMenu();
		int userMenuChoice = Integer.parseInt(currentMenu.askUserForInput());
		switch (userMenuChoice) {
		case 1: findAddress();
				break;
		case 2: findAddressByPostalCodeAndHouseNumber();
				break;
		case 3: findAddressByCustomerId();
				break;
		case 4: addNewAddress();
				break;
		case 5: updateAddress();
				break;
		case 6: updateAddress();
				break;
		case 7: goToCustomerMenu();
		case 9: // Returns to main menu
				if (user.isAdmin()) {
					nextController = new AdminMainMenuController(new AdminMainMenuView());
					nextController.setUser(user);
				}
				else {
					nextController = new UserMainMenuController(new UserMainMenuView());
					nextController.setUser(user);
				}
				break;
		default: System.out.println("Not a valid option.");
				currentMenu.displayMenu();
		}
	}
	
	public void findAddress() {
		Address address = new Address();
		addressModel  = new AddressDAOImpl(entityManager, Address.class); 
	}
	
	public void findAddressByPostalCodeAndHouseNumber() {
		
	}
	
	public void findAddressByCustomerId() {
		
	}
	
	public void addNewAddress() {
		
	}
	
	public void updateAddress() {
		
	}
	
	public void deleteAddress() {
		
	}
	
	public void goToCustomerMenu() {
		System.out.println("Are you certain you wish to go to the customer menu?");
		if(currentMenu.asksUserYesOrNo()) {
			nextController = new CustomerController(new CustomerView());
			nextController.setUser(user);
		} else {
			currentMenu.pressEnterToReturn();
		}
	}
}
