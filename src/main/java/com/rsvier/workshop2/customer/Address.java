package com.rsvier.workshop2.customer;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="Address")
public class Address {

	// Addresses can be either of the billing or delivery type
	
	public enum AddressType {
		BILLING,
		DELIVERY
	}
	
	@Id
	@Column(name = "id",
			nullable = false,
			unique = true)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long addressId;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "customer_id",
				nullable = false)
	private Customer customer;
	private String street;
	private int houseNumber;
	
	@Column(nullable = true)
	private String houseNumberAddition;
	private String postalCode;
	private String city;
	
	@Enumerated(EnumType.STRING)
	@Column(length = 8)
	public AddressType addressType;
	
	public Address() {
	}
	
	/* Constructor with parameters that render an address' uniqueness */
	public Address(String postalCode,
				   int houseNumber,
				   String houseNumberAddition,
				   Customer customer) {
	}
	
	/* Constructor with all parameters */
	public Address(String street,
				   int houseNumber,
				   String houseNumberAddition,
				   String postalCode,
				   String city,
				   String addressType,
				   boolean active,
				   Customer customer) {
	}

	public Long getAddressId() {
		return addressId;
	}

	public void setAddressId(Long addressId) {
		this.addressId = addressId;
	}
	
	public Customer getCustomerAtAddress() {
		return customer;
	}
	
	public void setCustomerAtAddress(Customer customer) {
		this.customer = customer;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public int getHouseNumber() {
		return houseNumber;
	}

	public void setHouseNumber(int houseNumber) {
		this.houseNumber = houseNumber;
	}

	public String getHouseNumberAddition() {
		return houseNumberAddition;
	}

	public void setHouseNumberAddition(String houseNumberAddition) {
		this.houseNumberAddition = houseNumberAddition;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public AddressType getAddressType() {
		return addressType;
	}

	public void setAddressType(AddressType addressType) {
		this.addressType = addressType;
	}
	
	@Override
	public String toString() {
		return "Address [addressId=" + addressId + ", customer=" + customer + ", street=" + street + ", houseNumber="
				+ houseNumber + ", houseNumberAddition=" + houseNumberAddition + ", postalCode=" + postalCode
				+ ", city=" + city + ", addressType=" + addressType + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((city == null) ? 0 : city.hashCode());
		result = prime * result + houseNumber;
		result = prime * result + ((postalCode == null) ? 0 : postalCode.hashCode());
		return result;
	}

	/* Addresses are considered equal when the house number, street and city all match*/
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Address other = (Address) obj;
		if (city == null) {
			if (other.city != null)
				return false;
		} else if (!city.equals(other.city))
			return false;
		if (houseNumber != other.houseNumber)
			return false;
		if (postalCode == null) {
			if (other.postalCode != null)
				return false;
		} else if (!postalCode.equals(other.postalCode))
			return false;
		return true;
	}
}
