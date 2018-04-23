package com.rsvier.workshop2.customer;

import java.util.ArrayList;
import java.util.Scanner;

import javax.persistence.EntityManager;

import com.rsvier.workshop2.controller.AdminMainMenuController;
import com.rsvier.workshop2.controller.Controller;
import com.rsvier.workshop2.controller.UserMainMenuController;
import com.rsvier.workshop2.customer.Address.AddressType;
import com.rsvier.workshop2.useraccounts.UserMainMenuView;
import com.rsvier.workshop2.useraccounts.Account.OwnerType;
import com.rsvier.workshop2.utility.HibernateService;
import com.rsvier.workshop2.utility.Validator;
import com.rsvier.workshop2.view.AdminMainMenuView;

public class AddressController extends Controller {
	
	private AddressView currentMenu;
	private AddressDAOImpl addressModel;
	// See the addNewAddress() method for clarity on why the customer is initialized as null
	private Customer customerAtAddress = null;
	private CustomerDAOImpl customerModel;
	private EntityManager entityManager = HibernateService.getEntityManager();
	private Scanner input = new Scanner(System.in);
	
	public AddressController(AddressView theView) {
		this.currentMenu = theView;
	}
	
	// Special constructor that is called only when a new customer is created by the user from the customer controller
	// That newly created customer is then passed here to ensure a link between address and customer always exists
	public AddressController(AddressView theView, Customer newCustomer) {
		this.currentMenu = theView;
		this.customerAtAddress = newCustomer;
	}

	@Override
	public void runView() {
		currentMenu.displayMenu();
		boolean validChoice = false;
		while (!validChoice) {
			int userMenuChoice = Integer.parseInt(currentMenu.askUserForMenuChoice());
			switch (userMenuChoice) {
				case 1: findAddress();
						validChoice = true;
						break;
				case 2: findAddressByPostalCodeAndHouseNumber();
						validChoice = true;
						break;
				case 3: findAddressesByCustomerId();
						validChoice = true;
						break;
				case 4: addNewAddress();
						validChoice = true;
						break;
				case 5: updateAddress();
						validChoice = true;
						break;
				case 6: deleteAddress();
						validChoice = true;
						break;
				case 7: goToCustomerMenu(); // Returns user back to the customer management menu
						validChoice = true;
						break;
				case 9: // Returns to main menu
					validChoice = true;
					if (loggedInUser.getOwnerType() == OwnerType.ADMIN) {
						nextController = new AdminMainMenuController(new AdminMainMenuView());
					} else {
						nextController = new UserMainMenuController(new UserMainMenuView());
					}
					break;
				default: System.out.println("Not a valid choice.");
						break;
			}
		}
	}
	
	public void findAddress() {
		addressModel  = new AddressDAOImpl(entityManager, Address.class);	
		Long findThisId = inputValidAddressId();
		
		Address foundAddress = addressModel.findById(Address.class, findThisId);
		if (foundAddress == null) {
			System.out.println("An address could not be found with id: " + findThisId);
			System.out.println("Please try again with a different id.");
			currentMenu.pressEnterToReturn();
			this.runView();
		} else {
			currentMenu.displayAddressDetailsHeader();
			currentMenu.displayLongDivider();
			currentMenu.displayAddressDetails(foundAddress);
			
			currentMenu.pressEnterToReturn();
			this.runView();
		}
	}
	
	public void findAddressByPostalCodeAndHouseNumber() {
		// TODO implement query
	}
	
	public void findAddressesByCustomerId() {
		ArrayList<Address> listOfAddresses = new ArrayList<Address>();
		addressModel = new AddressDAOImpl(entityManager, Address.class);
		Customer customer = inputCustomerForAddress();
		
		listOfAddresses = (ArrayList<Address>) addressModel.findAddressesByCustomer(customer);
		
		if(listOfAddresses == null) {
			System.out.println("No addresses were found for customer with id: " + customer.getCustomerId());
			System.out.println("Please enter an address for the customer first, or try a different id.");
			currentMenu.pressEnterToReturn();
			this.runView();
		} else {
			currentMenu.displayAddressDetailsHeader();
			currentMenu.displayDivider();
			currentMenu.displayAllAddresses(listOfAddresses);
			currentMenu.pressEnterToReturn();
			this.runView();
		}
	}
	
	public void addNewAddress() {
		Address addressToAdd = new Address();
		addressModel = new AddressDAOImpl(entityManager, Address.class);
		
		// The null check here exists to check whether a customer object was passed via the special class constructor
		// that only exists in the customer controller. If that was not the case, the user is prompted to retrieve a customer
		// from the database by its id. The goal is to ensure an address is always tied to a customer.
		if(customerAtAddress == null) {
			customerAtAddress = inputCustomerForAddress();
		}
		
		addressToAdd.setCustomerAtAddress(customerAtAddress);
		
		System.out.println("Please enter address details below:");
		
		String streetName = inputStreetName();
		addressToAdd.setStreet(streetName);
		
		int houseNumber = inputHouseNumber();
		addressToAdd.setHouseNumber(houseNumber);
	
		System.out.println("Do you wish to enter an addition to the house number (e.g. A, HS etc.)?");
		boolean enterAnAddition = currentMenu.asksUserYesOrNo();
		if(enterAnAddition) {
			String houseNumberAddition = inputHouseNumberAddition();
			addressToAdd.setHouseNumberAddition(houseNumberAddition);
		}
		
		String postalCode = inputPostalCode();
		addressToAdd.setPostalCode(postalCode);
		
		String city = inputCity();
		addressToAdd.setCity(city);
		
		inputAddressTypeForAddress(addressToAdd);
		
		Address newlyCreatedAddress = addressModel.create(addressToAdd);
		if (newlyCreatedAddress == null) {
			currentMenu.displayOperationFailed();
		} else {
			currentMenu.displayCreateSuccess();
		}
		currentMenu.pressEnterToReturn();
		this.runView();
	}
	
	public void updateAddress() {
		Address addressToUpdate = new Address();
		addressModel = new AddressDAOImpl(entityManager, Address.class);
		
		Long id = inputValidAddressId();
		
		addressToUpdate = addressModel.findById(Address.class, id);
		if(addressToUpdate == null) {
			System.out.println("No address was found with id " + id + ".");
			System.out.println("Returning to the address menu.");
			currentMenu.pressEnterToReturn();
			this.runView();
		}
		
		currentMenu.displayAddressDetailsHeader();
		currentMenu.displayLongDivider();
		currentMenu.displayAddressDetails(addressToUpdate);
		
		currentMenu.displayAddressUpdateMenu();
		
		boolean validChoice = false;
		while (!validChoice) {
			int userChoiceNumber = Integer.parseInt(currentMenu.askUserForMenuChoice());
			switch(userChoiceNumber) {
				case 1: editPostalCode(addressToUpdate);
						validChoice = true;
						break;
				case 2: editHouseNumber(addressToUpdate);
						validChoice = true;
						break;
				case 3: editStreetName(addressToUpdate);
						validChoice = true;
						break;
				case 4: editCity(addressToUpdate);
						validChoice = true;
						break;
				case 5: editTypeOfAddress(addressToUpdate);
						validChoice = true;
						break;
				case 9:	validChoice = true;
						this.runView();
						break;
				default: System.out.println("Invalid choice. \n");
						currentMenu.displayAddressUpdateMenu();
			}
		}
	}

	public void deleteAddress() {
		Address addressToDelete = new Address();
		addressModel = new AddressDAOImpl(entityManager, Address.class);
		
		Long id = inputValidAddressId();
		addressToDelete.setAddressId(id);
		System.out.println("You entered the following ID:" + addressToDelete.getAddressId());
		
		currentMenu.displayDeletionConfirmationPrompt(); // Require user confirmation
		boolean yesOrNo = currentMenu.asksUserYesOrNo();
		if (yesOrNo) { // user answered yes
			addressModel.delete(addressToDelete);
			currentMenu.displayDeleteSuccess();
			currentMenu.pressEnterToReturn();
			this.runView();
		} else {
			currentMenu.displayOperationCancelled();
			currentMenu.pressEnterToReturn();
			this.runView();
		}	
	}
	
	private void editPostalCode(Address addressToUpdate) {
		String postalCode = inputPostalCode();
		addressToUpdate.setPostalCode(postalCode);
		addressModel.update(addressToUpdate);
		currentMenu.pressEnterToReturn();
		this.runView();
	}
	
	private void editHouseNumber(Address addressToUpdate) {
		int houseNumber = inputHouseNumber();
		addressToUpdate.setHouseNumber(houseNumber);
		addressModel.update(addressToUpdate);
		currentMenu.pressEnterToReturn();
		this.runView();
	}
	
	private void editStreetName(Address addressToUpdate) {
		String streetName = inputStreetName();
		addressToUpdate.setStreet(streetName);
		addressModel.update(addressToUpdate);
		currentMenu.pressEnterToReturn();
		this.runView();
	}
	
	private void editCity(Address addressToUpdate) {
		String city = inputCity();
		addressToUpdate.setCity(city);
		addressModel.update(addressToUpdate);
		currentMenu.pressEnterToReturn();
		this.runView();
	}
	
	private void editTypeOfAddress(Address addressToUpdate) {
		inputAddressTypeForAddress(addressToUpdate);
		addressModel.update(addressToUpdate);
		currentMenu.pressEnterToReturn();
		this.runView();	
	}
	
	public void goToCustomerMenu() {
		System.out.println("Are you certain you wish to go to the customer menu?");
		boolean leaveMenu = currentMenu.asksUserYesOrNo();
		if(leaveMenu) { // user answered yes
			nextController = new CustomerController(new CustomerView());
		} else {
			currentMenu.pressEnterToReturn();
			this.runView();
		}
	}
	
	public Customer inputCustomerForAddress() {

		System.out.println("Please enter the customer's id: ");
		String userInput = input.nextLine();
		
		Long id = Long.parseLong(userInput);
		customerModel = new CustomerDAOImpl(entityManager, Customer.class);
		return customerModel.findById(Customer.class, id);
	}
	
	public String inputStreetName() {
		System.out.print("Enter a street name:");
		String streetName = input.nextLine();
		if (streetName.isEmpty()) {
			return inputStreetName();
		}
		else {
			return streetName;
		}
	}
	
	public int inputHouseNumber() {
		System.out.print("Enter a house number: ");
		String houseNumberAsString = input.nextLine();
		if(!Validator.isAPositiveOrZeroInt(houseNumberAsString)) {
			return inputHouseNumber();
		}
		return Integer.parseInt(houseNumberAsString);
	}
	
	public String inputHouseNumberAddition() {
		System.out.print("Enter a house number addition:");
		String houseNumberAddition = input.nextLine();
		if(houseNumberAddition.isEmpty()) {
			return inputHouseNumberAddition();
		} else {
			return houseNumberAddition;
		}
	}
	
	public String inputPostalCode() {
		System.out.print("Enter a postal code (format 1111AA):");
		String postalCode = input.nextLine();
		if(!Validator.validatePostalCode(postalCode)) {
			return inputPostalCode();
		} else {
			return postalCode;
		}
	}
	
	public String inputCity() {
		System.out.print("Enter a city:");
		String city = input.nextLine();
		if(city.isEmpty()) { // can't be empty
			return inputCity();
		} else {
			return city;
		}
	}
	
	public Address inputAddressTypeForAddress(Address address) {
		System.out.println("Are you entering a BILLING or DELIVERY address?");
		System.out.println("Enter a \"1\" for BILLING or \"2\" for DELIVERY.");
		String userChoice = input.nextLine();
		while(!Validator.isAnInt(userChoice)) {
			System.out.println("You did not enter a valid choice.");
			System.out.println("Do you wish to try again (Y); or to return to the address menu?");
			boolean tryAgain = currentMenu.asksUserYesOrNo();
			if(tryAgain) {
				return inputAddressTypeForAddress(address);
			} else {
				address = null;
				this.runView();
			}
		}
		switch(Integer.parseInt(userChoice)) {
			case 1: address.setAddressType(AddressType.BILLING);
					break;
			case 2: address.setAddressType(AddressType.DELIVERY);
					break;
		}
		return address;
	}
	
	public Long inputValidAddressId() {
		currentMenu.promptUserForItemId();
		String attemptAtId = input.nextLine();
		while (!Validator.isAnInt(attemptAtId)) {
			System.out.println("You did not enter a valid address id. Please try again:");
			attemptAtId = input.nextLine();
		}
		Long id = Long.valueOf(attemptAtId);
		return id;
	}
}
