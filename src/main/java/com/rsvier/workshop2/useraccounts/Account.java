package com.rsvier.workshop2.useraccounts;

import javax.persistence.*;


@Entity
public class Account {
	
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	protected long customerId;
	
	protected String username;
	protected String encryptedPassword;
	protected String userType;
	private String salt;
		
	protected Account() {
	}
	
	public Account (String username, String encryptedPassword, String salt) {
		this.username = username;
		this.encryptedPassword = encryptedPassword;
		this.salt = salt;
	}
	
	public long getCustomerId () {
		return customerId;
	}
	
	public void setCustomerId (long customerId) {
		this.customerId = customerId;
	}
	
	public String getEncryptedPassword () {
		return this.encryptedPassword;
	}
	
	public void setEncryptedPassword (String encryptedPassword) {
		this.encryptedPassword = encryptedPassword;
	}
	
	public String getUsername () {
		return this.username;
	}
	
	public void setUsername (String username) {
		this.username = username;
	}
	
	public String getUserType () {
		return this.userType;
	}
	
	public void setUserType (String userType) {
		this.userType = userType;
	}
	
	public void setSalt (String salt) {
		this.salt = salt;
	}
	
	public String getSalt () {
		return this.salt;
	}
	
}
