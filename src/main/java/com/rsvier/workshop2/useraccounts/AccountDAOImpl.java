package com.rsvier.workshop2.useraccounts;

import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.NoResultException;

import com.rsvier.workshop2.utility.GenericDAOImpl;

public class AccountDAOImpl extends GenericDAOImpl<Account> {
	
	private Logger logger = Logger.getLogger(AccountDAOImpl.class.getName());

	public AccountDAOImpl(EntityManager em, Class<Account> entityClass) {
		super(em, entityClass);
	}
	
	public boolean login(String username, String password) {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		Account userAccount = null;
		try {
			CriteriaQuery<Account> criteria = builder.createQuery( Account.class );
			Root<Account> personRoot = criteria.from( Account.class );
			criteria.select( personRoot );
			criteria.where( builder.equal( personRoot.get( Account_.username ),username ) );
			userAccount = em.createQuery(criteria).getSingleResult();
		}
		catch (NoResultException noAccountFound) {
			return false;
		}
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
