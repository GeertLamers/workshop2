package com.rsvier.workshop2.customer;

import java.util.ArrayList;
import java.util.Scanner;

import javax.persistence.EntityManager;

import com.rsvier.workshop2.controller.AdminMainMenuController;
import com.rsvier.workshop2.controller.Controller;
import com.rsvier.workshop2.controller.UserMainMenuController;
import com.rsvier.workshop2.useraccounts.UserCreationController;
import com.rsvier.workshop2.useraccounts.UserCreationView;
import com.rsvier.workshop2.useraccounts.Account.OwnerType;
import com.rsvier.workshop2.useraccounts.UserMainMenuView;
import com.rsvier.workshop2.utility.HibernateService;
import com.rsvier.workshop2.utility.Validator;
import com.rsvier.workshop2.view.AdminMainMenuView;

public class CustomerController extends Controller {
	
	private CustomerView currentMenu;
	private CustomerDAOImpl customerModel;
	private EntityManager entityManager = HibernateService.getEntityManager();
	private Scanner input = new Scanner(System.in);
	
	public CustomerController(CustomerView theView) {
		this.currentMenu = theView;
	}
	
	@Override
	public void runView() {
		boolean validChoice = false;
		while (!validChoice) {
			currentMenu.displayMenu();
			int userMenuChoice = Integer.parseInt(currentMenu.askUserForMenuChoice());
			switch (userMenuChoice) {
				case 1: findAllCustomers();
						validChoice = false;
						break;
				case 2: findCustomer();
						validChoice = false;
						break;
				case 3: addNewCustomer();
						validChoice = false;
						break;
				case 4: updateCustomerPersonalia();
						validChoice = false;
						break;
				case 5: updateCustomerAddresses();
						validChoice = false;
						break;
				case 6: deleteCustomer();
						validChoice = false;
						break;
				case 9: // Returns to main menu
						if (loggedInUser.getOwnerType() == OwnerType.ADMIN) {
							nextController = new AdminMainMenuController(new AdminMainMenuView());
						} else {
							nextController = new UserMainMenuController(new UserMainMenuView());
						}
						validChoice = true;
						break;
				default: System.out.println("Not a valid option.");
						break;
			}
		}
	}

	public void findAllCustomers() {
		customerModel = new CustomerDAOImpl(entityManager, Customer.class);
		ArrayList<Customer> allCustomers = (ArrayList<Customer>) customerModel.findAll();
		currentMenu.displayCustomerDetailsHeader();
		currentMenu.displayLongDivider();
		currentMenu.displayAllCustomers(allCustomers);
		
		currentMenu.pressEnterToReturn();
		this.runView();
	}
	
	public void findCustomer() {
		Customer foundCustomer = new Customer();
		Long findThisCustomer = inputValidCustomerId();
		foundCustomer = customerModel.findById(Customer.class, Long.valueOf(findThisCustomer)); 
		if (foundCustomer == null) {
			System.out.println("A customer could not be found with id: " + findThisCustomer);
			System.out.println("Please try again with a different id.");
			currentMenu.pressEnterToReturn();
			this.runView();
		} else {
			currentMenu.displayCustomerDetailsHeader();
			currentMenu.displayLongDivider();
			currentMenu.displayCustomerDetails(foundCustomer);
			
			currentMenu.pressEnterToReturn();
			this.runView();
		}
	}
	
 	public void addNewCustomer() {
 		// Creating a customer goes through the below controller and method
		new UserCreationController(new UserCreationView()).jointAccountAndCustomerCreator();
 	}

	public void updateCustomerPersonalia() {
		Customer customerToUpdate = new Customer();
		customerModel = new CustomerDAOImpl(entityManager, Customer.class);
		
		Long id = inputValidCustomerId();
		
		customerToUpdate = customerModel.findById(Customer.class, id);
		if(customerToUpdate == null) {
			System.out.println("No address was found with id " + id + ".");
			System.out.println("Returning to the address menu.");
			currentMenu.pressEnterToReturn();
			this.runView();
		}
		
		currentMenu.displayCustomerDetailsHeader();
		currentMenu.displayLongDivider();
		currentMenu.displayCustomerDetails(customerToUpdate);
		
		currentMenu.displayUpdateMenu();
		boolean validChoice = false;
		while (!validChoice) {
			int userMenuChoice = Integer.parseInt(currentMenu.askUserForMenuChoice());
			switch (userMenuChoice) {
				case 1: editName(customerToUpdate);
						validChoice = true;
						break;
				case 2: editEmail(customerToUpdate);
						validChoice = true;
						break;
				case 3: editPhoneNumber(customerToUpdate);
						validChoice = true;
						break;
				case 9:	// no action necessary as setting the validChoice to true will exit the user from the while loop and return to the customer menu
						validChoice = true;
						break;
				default: System.out.println("Not a valid option. \n");
						break;
			}
		}
		currentMenu.pressEnterToReturn();
		this.runView();
	}
	
	public void updateCustomerAddresses() {
		System.out.println("Are you certain you wish to switch to the address menu?");
		if(currentMenu.asksUserYesOrNo()) { // user wants to switch
			nextController = new AddressController(new AddressView());
		} else {
			currentMenu.pressEnterToReturn();
			this.runView();
		}
	}
	
	public void deleteCustomer() {
		Customer customerToDelete = new Customer();
		customerModel = new CustomerDAOImpl(entityManager, Customer.class);
		
		Long id = inputValidCustomerId();
		customerToDelete.setCustomerId(id);
		System.out.println("You entered the following ID:" + customerToDelete.getCustomerId());
	
		currentMenu.displayDeletionConfirmationPrompt(); // Require user confirmation
		boolean yesOrNo = currentMenu.asksUserYesOrNo();
		if (yesOrNo) { // user answered yes
			customerModel.delete(customerToDelete);
			currentMenu.displayDeleteSuccess();
			currentMenu.pressEnterToReturn();
			this.runView();
		} else {
			currentMenu.displayOperationCancelled();
			currentMenu.pressEnterToReturn();
			this.runView();
		}
	}
	
	/* EDIT CUSTOMER METHODS */
	
	public void editName(Customer customerToUpdate) {
		customerToUpdate.setFirstName(inputFirstName());
		customerToUpdate.setLastName(inputLastName());
		System.out.println("Do you wish to enter a preposition for your last name? (e.g. \"van\" or \"den\")?");
		if(currentMenu.asksUserYesOrNo()) { // user wants to add a preposition
			customerToUpdate.setLastNamePreposition(inputLastNamePreposition());
		}
		
		customerModel.update(customerToUpdate);	
	}
	
	public void editEmail(Customer customerToUpdate) {
		customerToUpdate.setEmail(inputEmail());
		customerModel = new CustomerDAOImpl(entityManager, Customer.class);
		customerModel.update(customerToUpdate);
	}
	
	public void editPhoneNumber(Customer customerToUpdate) {
		customerToUpdate.setPhoneNumber(inputPhoneNumber());
		customerModel = new CustomerDAOImpl(entityManager, Customer.class);
		customerModel.update(customerToUpdate);
	}
	
	/* INPUT & HELPER METHODS */
	
	public String inputFirstName() {
		System.out.print("Enter a first name: ");
		String firstName = input.nextLine();
		if(firstName.isEmpty()) { // can't be empty
			return inputFirstName();
		}
		return firstName;
	}
	
	public String inputLastName() {
		System.out.print("Enter a last name: ");
		String lastName = input.nextLine();
		if(lastName.isEmpty()) { // can't be empty
			return inputLastName();
		}
		return lastName;
	}
	
	public String inputLastNamePreposition() {
		System.out.print("Enter a preposition to the last name: ");
		String lastNamePreposition = input.nextLine();
		if(lastNamePreposition.isEmpty()) { // can't be empty
			System.out.println("No entry detected. Are you sure you do not want to enter a preposition after all?");
			if(currentMenu.asksUserYesOrNo()) {
				return "";
			} else {
				return inputLastNamePreposition();
			}
		} 
		return lastNamePreposition;
	}
	
	public String inputEmail() {
		System.out.print("Enter an email address: ");
		String email = input.nextLine();
		// TODO validate email
		if(email.isEmpty()) { // can't be empty
			return inputEmail();
		} else {
			return email;
		}
	}
	
	public String inputPhoneNumber() {
		System.out.print("Enter a phone number: ");
		String phoneNumber = input.nextLine();
		//TODO validate phone number
		if(phoneNumber.isEmpty()) { // can't be empty
			return inputPhoneNumber();
		} else {
			return phoneNumber;
		}
	}
	
	public Long inputValidCustomerId() {
		currentMenu.promptUserForItemId();
		String attemptAtId = input.nextLine();
		while (!Validator.isAnInt(attemptAtId)) {
			System.out.println("You did not enter a valid customer id. Please try again:");
			attemptAtId = input.nextLine();
		}
		Long id = Long.valueOf(attemptAtId);
		return id;
	}
}
