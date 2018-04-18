package com.rsvier.workshop2.useraccounts;

import java.util.logging.Logger;
import javax.persistence.EntityManager;
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
	
	public int getUserID(String username) {
		return 1;
	}

	public boolean isAdmin(int userID) {
		return true;
	}
}
