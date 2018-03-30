package com.rsvier.workshop2.model.dao;

public interface DatabaseBuilderDAO {
	
	// Read
	public abstract boolean isDatabaseInitialized();
	
	// Create
	public abstract boolean initializeMYSQLDatabase();

}
