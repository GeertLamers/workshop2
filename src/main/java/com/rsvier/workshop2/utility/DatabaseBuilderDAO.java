package com.rsvier.workshop2.utility;

public interface DatabaseBuilderDAO {
	
	// Read
	public abstract boolean isDatabaseInitialized();
	
	// Create
	public abstract boolean initializeMYSQLDatabase();

}
