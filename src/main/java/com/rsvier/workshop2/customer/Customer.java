package com.rsvier.workshop2.customer;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.rsvier.workshop2.useraccounts.Account;

@Entity
@Table(name="Customer",
		uniqueConstraints={@UniqueConstraint(columnNames={"ID"})})
public class Customer {
	
	@Id	@Column(name="ID", nullable=false, unique=true, length=11)
	private long customerId;
	
	@PrimaryKeyJoinColumn @OneToOne(
			fetch = FetchType.LAZY,
			optional = false)
	private Account account;
	
	private String firstName;
	private String lastName;
	private String lastNamePreposition;
	private String email;
	private String phoneNumber;
	private boolean customerActive;
	private int active;
	private Date creationDate;

	protected Customer() {
	}

	// Complete constructor with all params
	public Customer(String username,
					String password,
					String salt,
					String firstName,
					String lastName,
					String lastNamePreposition,
					String email,
					String phoneNumber,
					Date creationDate) {
		this.account = new Account(username, password, salt);
		this.firstName = firstName;
		this.lastName = lastName;
		this.lastNamePreposition = lastNamePreposition;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.creationDate = creationDate;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getLastNamePreposition() {
		return lastNamePreposition;
	}

	public void setLastNamePreposition(String lastNamePreposition) {
		this.lastNamePreposition = lastNamePreposition;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public boolean isCustomerActive() {
		return customerActive;
	}

	public void setCustomerActive(boolean customerActive) {
		this.customerActive = customerActive;
	}
	
	public void setActive (int active) {
		this.active = active;
	}
	
	public int getActive () {
		return this.active;
	}

	@Override
	public String toString() {
		return "Customer [customerId=" + customerId + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", lastNamePreposition=" + lastNamePreposition + ", email=" + email + ", phoneNumber=" + phoneNumber
				+ ", customerActive=" + customerActive + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result + ((lastNamePreposition == null) ? 0 : lastNamePreposition.hashCode());
		return result;
	}
	
	/* Customers are considered equal when their names and e-mails are identical concurrently */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Customer other = (Customer) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		if (lastNamePreposition == null) {
			if (other.lastNamePreposition != null)
				return false;
		} else if (!lastNamePreposition.equals(other.lastNamePreposition))
			return false;
		return true;
	}
}
