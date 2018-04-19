package com.rsvier.workshop2.useraccounts;

import javax.persistence.*;


@Entity
public class Account {
	
	public enum OwnerType {
		ADMIN,
		EMPLOYEE,
		CUSTOMER
	}
	
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	protected long customerId;
	
	protected String username;
	protected String encryptedPassword;
	@Enumerated(EnumType.STRING)
	@Column(length = 8)
	protected OwnerType ownerType;
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
	
	public OwnerType getOwnerType () {
		return this.ownerType;
	}
	
	public void setUserType (OwnerType ownerType) {
		this.ownerType = ownerType;
	}
	
	public void setSalt (String salt) {
		this.salt = salt;
	}
	
	public String getSalt () {
		return this.salt;
	}	
}
