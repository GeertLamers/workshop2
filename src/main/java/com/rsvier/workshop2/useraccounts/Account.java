package com.rsvier.workshop2.useraccounts;

import javax.persistence.*;


@Entity
public class Account {
	
	@Id	@PrimaryKeyJoinColumn @GeneratedValue
	protected long customerId;
	
	protected String username;
	protected String encryptedPassword;
	protected String userType;
	private String salt;
		
	protected Account() {
	}
	
	public Account (Long customerId, String username, String encryptedPassword, String userType, String salt) {
		this.customerId = customerId;
		this.username = username;
		this.encryptedPassword = encryptedPassword;
		this.userType = userType;
		this.salt = salt;
	}
	
	// Are getters and setters really needed?
		
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
