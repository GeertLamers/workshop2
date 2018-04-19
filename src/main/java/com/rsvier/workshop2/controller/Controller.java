package com.rsvier.workshop2.controller;

import javax.persistence.EntityManager;

import com.rsvier.workshop2.useraccounts.Account;
import com.rsvier.workshop2.utility.HibernateService;
import com.rsvier.workshop2.view.*;

public abstract class Controller { // parent
	protected View<?> currentMenu;
	protected Controller nextController;
	protected EntityManager entityManager = HibernateService.getEntityManager();
	protected Account loggedInUser;
	
	public abstract void runView();
	public void setLoggedInUser (Account loggedInUser) {
		this.loggedInUser = loggedInUser;
	}
	
	public Controller getNextController() {
		return nextController;
	}
}