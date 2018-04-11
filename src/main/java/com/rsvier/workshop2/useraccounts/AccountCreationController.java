package com.rsvier.workshop2.useraccounts;

import java.util.ArrayList;

import com.rsvier.workshop2.controller.Controller;
import com.rsvier.workshop2.controller.LoginController;
import com.rsvier.workshop2.customer.Customer;
import com.rsvier.workshop2.customer.CustomerDAOImpl;
import com.rsvier.workshop2.utility.Validator;
import com.rsvier.workshop2.view.LoginMenuView;

public class AccountCreationController extends Controller {
	
	public AccountCreationController (UserCreationView theView) {
		this.currentMenu = theView;
	}

	@Override
	public void runView() {
		currentMenu.displayMessage();
		nextController = new LoginController(new LoginMenuView());
		accountCreator();
	}
	
	private void accountCreator() {
		boolean accountCreated = false;
		Customer newCustomer = new Customer();
		
		while (!accountCreated) {

			ArrayList<String> necessaryCustomerInformation = new ArrayList<String>();
			for (String customerProperty : necessaryCustomerInformation) {
				boolean validInput = false;
				String userInput = "";
				if (customerProperty.equals("hash")) continue; // requires no action
				while (!validInput) {
					userInput = ((UserCreationView) currentMenu).askUserForInput(customerProperty);
					validInput = new Validator(userInput).validateNewUser(customerProperty);
				}
				switch (customerProperty) {
				//case "username":
					//newCustomer.setUsername(userInput);
					//break;
				case "password":
					PasswordHasher passwordHasher = new PasswordHasher();
					String salt = passwordHasher.generateSalt();
					//newCustomer.setEncryptedPassword(passwordHasher.makeSaltedPasswordHash(userInput, salt));
					//newCustomer.setSalt(salt);
					break;
				case "first_name":
					newCustomer.setFirstName(userInput);
					break;
				case "last_name":
					newCustomer.setLastName(userInput);
					break;
				case "last_name_preposition":
					newCustomer.setLastNamePreposition(userInput);
					break;
				case "email":
					newCustomer.setEmail(userInput);
					break;
				case "phone_number":
					newCustomer.setPhoneNumber(userInput);
					break;
				default:
					System.out.println("Unidentified customer property. Please check your database. Program is closing..");
					System.exit(0);
				}
			}
			Long customerID = new CustomerDAOImpl().createCustomer(newCustomer);
			newCustomer.setCustomerId(customerID);
			accountCreated = new AccountDAOImpl().createAccount(newCustomer);
			//TODO: Push username, password & customerID to login database
		}
		System.out.println("Account created. Returning to login screen..");
	}
}
