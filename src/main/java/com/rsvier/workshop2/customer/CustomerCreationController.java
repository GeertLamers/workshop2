package com.rsvier.workshop2.customer;

import java.util.ArrayList;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.transaction.UserTransaction;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.rsvier.workshop2.controller.Controller;
import com.rsvier.workshop2.controller.LoginController;
import com.rsvier.workshop2.useraccounts.PasswordHasher;
import com.rsvier.workshop2.useraccounts.UserCreationView;
import com.rsvier.workshop2.utility.HibernateService;
import com.rsvier.workshop2.utility.Validator;
import com.rsvier.workshop2.view.LoginMenuView;

public class CustomerCreationController extends Controller {
	
	private EntityManager entityManager = HibernateService.getEntityManager();
	private CustomerDAOImpl customerModel;
	
	public CustomerCreationController (UserCreationView theView) {
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
		while (!accountCreated) {
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
					validInput = new Validator(userInput).validateNewUser(customerProperty);
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
			Customer newCustomer = new Customer(username, password, salt, firstName, lastName, lastNamePreposition, email, phoneNumber, creationDate);
			customerModel = new CustomerDAOImpl(entityManager, Customer.class);
			customerModel.create(newCustomer);
			accountCreated = currentMenu.displayCreateSuccess();
		}
		System.out.println("Account created. Returning to login screen..");
	}
}
