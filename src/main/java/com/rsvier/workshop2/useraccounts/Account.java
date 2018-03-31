package com.rsvier.workshop2.useraccounts;

public class Account {
	
	private Long accountId;
	private boolean active;
	private String ownerType;
	
	public Account() {
	}
	
	public Account(Long accoundId,
				   boolean active,
				   String accountOwner) {
	}
	
	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getOwnerType() {
		return ownerType;
	}
	
	public void setOwnerType(String ownerType) {
		this.ownerType = ownerType;
	}
}
