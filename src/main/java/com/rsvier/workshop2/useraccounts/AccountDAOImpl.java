package com.rsvier.workshop2.useraccounts;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Logger;

import javax.persistence.EntityManager;

import com.rsvier.workshop2.Main;
import com.rsvier.workshop2.customer.Customer;
import com.rsvier.workshop2.product.Product;
import com.rsvier.workshop2.utility.DataSource;
import com.rsvier.workshop2.utility.GenericDAOImpl;

public class AccountDAOImpl extends GenericDAOImpl<Account> {
	
	private Logger logger = Logger.getLogger(AccountDAOImpl.class.getName());

	public AccountDAOImpl(EntityManager em, Class<Account> entityClass) {
		super(em, entityClass);
	}

	public boolean login(String username, String password) {
		if (username.equals("Onne") && password.equals("Hello")) { // to avoid the hashing
			logger.info("User Onne detected");
			return true;
		}
		Account userAccount = em.find(Account.class, username);
		if (userAccount.getEncryptedPassword().equals(password)) {
			return true;
		}
		return false;
	}
	
	public ArrayList<String> getUsernameList() {
		String query = "Select username from account where id > 0";
		ArrayList<String> allOfTheUsers = new ArrayList<>();
		try (Connection connection = DataSource.getConnection();
			PreparedStatement statement = connection.prepareStatement(query);){
			logger.info("Connected to database");
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				allOfTheUsers.add(resultSet.getObject(1) + "");
			}
			logger.info("List of usernames retrieved");
			if (!Main.hikariEnabled) connection.close(); // necessary for the JDBC
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return allOfTheUsers;
	}
	
	public int getUserID(String username) {
		return 1;
	}
	
	public ArrayList<String> retrieveAccountProperties() { // retrieves the information we need from the customer from the tables
		ArrayList<String> resultSetAsArrayList = new ArrayList<>();
		return resultSetAsArrayList;
		/*String query = "select column_name from information_schema.columns\r\n" + 
				" where table_name = 'account'";
		String query2 =  "select column_name from information_schema.columns\r\n" + 
				" where table_name = 'customer'";
		try (Connection connection = DataSource.getConnection();
			PreparedStatement statement = connection.prepareStatement(query);
			ResultSet resultSet = statement.executeQuery();) {
			while (resultSet.next()) {
				int i = 1;
				resultSetAsArrayList.add(resultSet.getString(i));
				i++;
			}
			PreparedStatement statement2 = connection.prepareStatement(query2);
			ResultSet resultSet2 = statement2.executeQuery();
			while (resultSet2.next()) {
				int i = 1;
				resultSetAsArrayList.add(resultSet2.getString(i));
				i++;
				}
			resultSetAsArrayList.remove("id"); //necessary until I figure out how not to select these
			resultSetAsArrayList.remove("owner_type");
			resultSetAsArrayList.remove("customer_id");
			resultSetAsArrayList.remove("active");
			resultSetAsArrayList.remove("id");
			if (!Main.hikariEnabled) connection.close(); // necessary for the JDBC
		}
	
		catch (SQLException e) {
			e.printStackTrace();
		}
		return resultSetAsArrayList;
		*/
	}
	
	@Override
	public boolean createAccount(Customer newCustomer) { // creates a new user
			String query = "INSERT INTO customer (first_name, last_name, last_name_preposition, email, phone_number) VALUES (?,?,?,?,?)";
			try (Connection connection = DataSource.getConnection();
				PreparedStatement statement = connection.prepareStatement(query);){
				logger.info("Connected to the database");
				statement.setString(1, newCustomer.getFirstName());
				statement.setString(2, newCustomer.getLastName());
				statement.setString(3, newCustomer.getLastNamePreposition());
				statement.setString(4, newCustomer.getEmail());
				statement.setString(5, newCustomer.getPhoneNumber());
				statement.executeUpdate();
				logger.info("Created a new customer in the customer table");
				if (!Main.hikariEnabled) connection.close(); // necessary for the JDBC
			}
			catch (SQLException e) {
				e.printStackTrace();
				return false;
			}
			
			query = "SELECT id FROM customer WHERE first_name = (?)";
			ResultSet resultSet = null;
			int id = 0;
			try (Connection connection = DataSource.getConnection();
				PreparedStatement statement = connection.prepareStatement(query);){
				logger.info("Connected to the database");
				statement.setString(1,  newCustomer.getFirstName());
				resultSet = statement.executeQuery();
				if (resultSet.next()) id = resultSet.getInt(1);
				logger.info("Retrieved new customer's user ID");
				if (!Main.hikariEnabled) connection.close(); // necessary for the JDBC
			}
			catch (SQLException e) {
				e.printStackTrace();
				return false;
			}
			
			query = "INSERT INTO account (customer_id, username, password, owner_type, hash) VALUES (?,?,?,?,?)";
			try (Connection connection = DataSource.getConnection();
				PreparedStatement statement = connection.prepareStatement(query);){
				logger.info("Connected to the database");
				statement.setInt(1, id);
				statement.setString(2,  newCustomer.getUsername());
				statement.setString(3,  newCustomer.getEncryptedPassword());
				statement.setString(4,  "NOTADMIN");
				statement.setString(5,  newCustomer.getSalt().toString());
				statement.executeUpdate();
				logger.info("Added new customer to the account table");
				if (!Main.hikariEnabled) connection.close(); // necessary for the JDBC
			}
			catch (SQLException e) {
				e.printStackTrace();
				return false;
			}
			
			return true;
	}

	@Override
	public boolean changePassword() {
		// TODO Auto-generated method stub
		return false;
	}
	
	public boolean upgradeAccount(String changeThisUser) {
		String query = "update account set owner_type='ADMIN' where username=?";
		try (Connection connection = DataSource.getConnection();
				PreparedStatement statement = connection.prepareStatement(query);){
				logger.info("Connected to the database");
				statement.setString(1,  changeThisUser);
				statement.executeUpdate();
				logger.info("Upgraded " + changeThisUser + " to admin");
				if (!Main.hikariEnabled) connection.close(); // necessary for the JDBC
		}
		catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public boolean changeUsername() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteAccount(String deleteThisUser) {
		boolean success = false;
		String query = "Delete from account where username = (?)";
		try (Connection connection = DataSource.getConnection();
			PreparedStatement statement = connection.prepareStatement(query);){
			logger.info("Connected to database");
			statement.setString(1,  deleteThisUser);
			statement.executeUpdate();
			success = true;
			if (!Main.hikariEnabled) connection.close(); // necessary for the JDBC
		}
		catch (SQLException e) {
			e.printStackTrace();
			success = false;
		}
		return success;
	}


	@Override
	public boolean isAdmin(int userID) {
		return true;
	}
}
