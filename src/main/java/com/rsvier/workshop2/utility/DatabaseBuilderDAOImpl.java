package com.rsvier.workshop2.utility;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import com.ibatis.common.jdbc.ScriptRunner;
import com.rsvier.workshop2.controller.Controller;
import com.rsvier.workshop2.customer.Address;
import com.rsvier.workshop2.customer.Customer;
import com.rsvier.workshop2.customer.CustomerDAOImpl;
import com.rsvier.workshop2.customer.Address.AddressType;
import com.rsvier.workshop2.customer.AddressDAOImpl;
import com.rsvier.workshop2.useraccounts.Account;
import com.rsvier.workshop2.useraccounts.Account.OwnerType;
import com.rsvier.workshop2.useraccounts.AccountDAOImpl;
import com.rsvier.workshop2.useraccounts.PasswordHasher;

public class DatabaseBuilderDAOImpl extends Controller implements DatabaseBuilderDAO {
	
	private Logger logger = Logger.getLogger(CustomerDAOImpl.class.getName());
	private CustomerDAOImpl customerModel;
	private AccountDAOImpl accountModel;
	private AddressDAOImpl addressModel;

	public boolean isDatabaseInitialized() {
		// TODO Auto-generated method stub
		return false;
	}
	public boolean createMYSQLDatabase () {
		 createOurAccount();
		 createFillerAccounts();
		 createAddresses();
		 return true;
	}
	
	private void createAddresses() {
		// Create filler addresses and map them to our customers (run AFTER creating the customers!)
		int testNumber = 10;
		customerModel = new CustomerDAOImpl(entityManager, Customer.class);
		addressModel = new AddressDAOImpl(entityManager, Address.class);
		
		List<Customer> customerList = customerModel.findAll(); // Load all customers
		for (Customer customer : customerList) {
			Address address = new Address();
			address.setStreet("Teststreet " + testNumber);
			address.setHouseNumber(testNumber);
			address.setHouseNumberAddition("");
			address.setPostalCode("ABCD" + testNumber);
			address.setCity("Amsterdam");
			address.setAddressType(AddressType.DELIVERY);
			address.setCustomerAtAddress(customer);
			addressModel = new AddressDAOImpl(entityManager, Address.class);
			addressModel.create(address);
			testNumber++;
		}
	}
	
	private void createOurAccount () {
		PasswordHasher passwordHasher = new PasswordHasher();
		
		// Our account
		String bestPassword = "Hello";
		String bestSalt = passwordHasher.generateSalt();
		String bestEncryptedPassword = passwordHasher.makeSaltedPasswordHash(bestPassword, bestSalt);

		Customer bestCustomer = new Customer("Onne", "Geheim", "", "rsvier@rsvier.rsvier", "0123456789"/*, creationDate*/);
		Account bestAccount = new Account("Onne", bestEncryptedPassword, bestSalt);
		bestAccount.setOwnerType(OwnerType.ADMIN);
		accountModel = new AccountDAOImpl(entityManager, Account.class);
		accountModel.create(bestAccount);
		bestCustomer.setCustomerId(bestAccount.getCustomerId());
		bestCustomer.setAccount(bestAccount);
		customerModel = new CustomerDAOImpl(entityManager, Customer.class);
		customerModel.create(bestCustomer);
		
	}
	
	private void createFillerAccounts() {
		PasswordHasher passwordHasher = new PasswordHasher();
		
		// Filler accounts
		String username = "A";
		String password = "A";
		String salt = passwordHasher.generateSalt();
		String encryptedPassword = passwordHasher.makeSaltedPasswordHash(password, salt);
		String firstName = "A";
		String lastName = "A";
		String lastNamePreposition = "";
		String email = "A";
		String phoneNumber = "0";
		for (Character append = 'A'; append <= 'Z' ; append++) {
			username = append.toString();
			password =  append.toString();
			firstName = append.toString();
			lastName = append.toString();
			Customer newCustomer = new Customer(firstName, lastName, lastNamePreposition, email, phoneNumber/*, creationDate*/);
			Account newAccount = new Account(username, encryptedPassword, salt);
			newAccount.setOwnerType(OwnerType.CUSTOMER);
			accountModel = new AccountDAOImpl(entityManager, Account.class);
			accountModel.create(newAccount);
			newCustomer.setCustomerId(newAccount.getCustomerId());
			newCustomer.setAccount(newAccount);
			customerModel = new CustomerDAOImpl(entityManager, Customer.class);
			customerModel.create(newCustomer);
		}
	}
	
	public boolean initializeMYSQLDatabase() { // old method
			String aSQLScriptFilePath = "create.sql";
			Connection conn = null;
			Reader reader = null;
			boolean success = true;
			try {
				conn = DataSource.getConnection();
				logger.info("Connected to database.");
				// Initialize object for ScripRunner
				ScriptRunner sr = new ScriptRunner(conn, false, false);

				// Give the input file to Reader
				reader = new BufferedReader(
	                               new FileReader(aSQLScriptFilePath));

				// Execute script
				sr.runScript(reader);

			} catch (Exception e) {
				System.err.println("Failed to Execute" + aSQLScriptFilePath
						+ " The error is " + e.getMessage());
				success = false;
			}
			finally {
				// close file reader
				if (reader != null) {
					try {
					reader.close();
					}
					catch (IOException e) {
						e.printStackTrace();
					}
					
				}
				// close db connection
				if (conn != null) {
					try {
						conn.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
			return success;
	}
	@Override
	public void runView() {
		// TODO Auto-generated method stub
		
	}
}
