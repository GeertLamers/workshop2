package com.rsvier.workshop2.useraccounts;

import java.util.ArrayList;
import java.util.Date;

import com.rsvier.workshop2.controller.Controller;
import com.rsvier.workshop2.controller.LoginController;
import com.rsvier.workshop2.customer.Customer;
import com.rsvier.workshop2.customer.CustomerDAOImpl;
import com.rsvier.workshop2.utility.Validator;
import com.rsvier.workshop2.view.LoginMenuView;

public class UserCreationController extends Controller {

	private CustomerDAOImpl customerModel;
	private AccountDAOImpl accountModel;

	public UserCreationController (UserCreationView theView) {
		this.currentMenu = theView;
	}

	@Override
	public void runView() {
		currentMenu.displayMessage();
		nextController = new LoginController(new LoginMenuView());
		jointAccountAndCustomerCreator();
	}

	public void jointAccountAndCustomerCreator() {
		String username = "";
		String password = "";
		String salt = "";
		String firstName = "";
		String lastName = "";
		String lastNamePreposition = "";
		String email = "";
		String phoneNumber = "";
		Date creationDate = new Date();
		ArrayList<String> necessaryCustomerInformation = new ArrayList<String>(); //temporary fix
		necessaryCustomerInformation.add("username");
		necessaryCustomerInformation.add("password");
		necessaryCustomerInformation.add("firstName");
		necessaryCustomerInformation.add("lastName");
		necessaryCustomerInformation.add("lastNamePreposition");
		necessaryCustomerInformation.add("email");
		necessaryCustomerInformation.add("phoneNumber");

		for (String customerProperty : necessaryCustomerInformation) {
			boolean validInput = false;
			String userInput = "";
			while (!validInput) {
				userInput = ((UserCreationView) currentMenu).askUserForInput(customerProperty);
				validInput = new Validator(userInput).validateUser(customerProperty);
			}
			switch (customerProperty) {
			case "username":
				username = userInput;
				break;
			case "password":
				PasswordHasher passwordHasher = new PasswordHasher();
				salt = passwordHasher.generateSalt();
				password = passwordHasher.makeSaltedPasswordHash(userInput, salt);
				break;
			case "firstName":
				firstName = userInput;
				break;
			case "lastName":
				lastName = userInput;
				break;
			case "lastNamePreposition":
				lastNamePreposition = userInput;
				break;
			case "email":
				email = userInput;
				break;
			case "phoneNumber":
				phoneNumber = userInput;
				break;
			default:
				System.out.println("Unidentified customer property. Please check your database. Program is closing..");
				System.exit(0);
			}
		}
		Customer newCustomer = new Customer(firstName, lastName, lastNamePreposition, email, phoneNumber/*, creationDate*/);
		Account newAccount = new Account(username, password, salt);
		accountModel = new AccountDAOImpl(entityManager, Account.class);
		accountModel.create(newAccount);
		newCustomer.setCustomerId(newAccount.getCustomerId());
		newCustomer.setAccount(newAccount);
		customerModel = new CustomerDAOImpl(entityManager, Customer.class);
		customerModel.create(newCustomer);
		currentMenu.displayCreateSuccess();
	}
}
