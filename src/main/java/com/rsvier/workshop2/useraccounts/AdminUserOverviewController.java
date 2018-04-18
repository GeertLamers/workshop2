package com.rsvier.workshop2.useraccounts;

import java.util.ArrayList;

import javax.persistence.EntityManager;
import com.rsvier.workshop2.controller.AdminMainMenuController;
import com.rsvier.workshop2.controller.Controller;
import com.rsvier.workshop2.utility.HibernateService;
import com.rsvier.workshop2.view.*;

public class AdminUserOverviewController extends Controller{
	private AdminUserOverview currentMenu;
	private EntityManager entityManager = HibernateService.getEntityManager();
	private AccountDAOImpl accountModel;
	
	public AdminUserOverviewController (AdminUserOverview theView) {
		this.currentMenu = theView;
	}

	@Override
	public void runView() {
		currentMenu.displayMenu();
		currentMenu.displayMenuOptions();
		boolean validChoice = false;
		while (!validChoice) {
			int userMenuChoice = Integer.parseInt(currentMenu.askUserForMenuChoice());
			switch (userMenuChoice) {
				case 1: showAllUsers();
						validChoice = true;
						break;
				case 2: deleteUser();
						validChoice = true;
						break;
				case 3: changeUser();
						validChoice = true;
						break;
				case 9: // Returns to main menu
						validChoice = true;
						nextController = new AdminMainMenuController(new AdminMainMenuView());
						break;
				default: System.out.println("Not a valid option.");
						break;
				}
		}
	}
	
	private void changeUser() {
	}

	private void deleteUser() {
	}

	private void showAllUsers() {
		accountModel = new AccountDAOImpl(entityManager, Account.class);
		ArrayList<Account> allUsers = (ArrayList<Account>) accountModel.findAll();
		currentMenu.displayLongDivider();
		currentMenu.printAllUsers(allUsers);
		currentMenu.pressEnterToReturn();
	}
}
