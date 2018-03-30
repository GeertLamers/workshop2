package com.rsvier.workshop2.controller;

import java.util.*;

import com.rsvier.workshop2.model.*;
import com.rsvier.workshop2.useraccounts.*;
import com.rsvier.workshop2.view.*;

public abstract class Controller { // parent
	protected View currentMenu;
	protected User user;
	protected Controller nextController;
	protected HashMap<Integer,Controller> menuOptions;
	
	public abstract void runView();
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	public Controller getNextController() {
		return nextController;
	}
}