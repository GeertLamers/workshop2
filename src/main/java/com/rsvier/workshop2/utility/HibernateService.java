package com.rsvier.workshop2.utility;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class HibernateService {
	
	private static EntityManagerFactory emf;
	private static EntityManager em;
	
	public HibernateService() { 
	}
	
	public static EntityManager getEntityManager() {
		if (em == null) {
			EntityManagerFactory emf = Persistence.createEntityManagerFactory("HibernatusCommunicatus");
			EntityManager em = emf.createEntityManager();
		}
		return em;
	}
	
	public static void closeEntityManagerFactory() {
		emf.close();
	}
}
