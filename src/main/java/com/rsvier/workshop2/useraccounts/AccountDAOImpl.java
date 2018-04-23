package com.rsvier.workshop2.useraccounts;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.NoResultException;

import com.rsvier.workshop2.utility.GenericDAOImpl;

public class AccountDAOImpl extends GenericDAOImpl<Account> {

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
		if (userAccount.getEncryptedPassword().equals(new PasswordHasher().makeSaltedPasswordHash(password, userAccount.getSalt()))) {
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

	public Account findByUsername(String username) {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		Account userAccount = null;
		CriteriaQuery<Account> criteria = builder.createQuery( Account.class );
		Root<Account> personRoot = criteria.from( Account.class );
		criteria.select( personRoot );
		criteria.where( builder.equal( personRoot.get( Account_.username ),username ) );
		userAccount = em.createQuery(criteria).getSingleResult();
		return userAccount;
	}
}
