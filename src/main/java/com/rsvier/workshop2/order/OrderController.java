package com.rsvier.workshop2.order;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import com.rsvier.workshop2.controller.AdminMainMenuController;
import com.rsvier.workshop2.controller.Controller;
import com.rsvier.workshop2.controller.UserMainMenuController;
import com.rsvier.workshop2.customer.Address;
import com.rsvier.workshop2.customer.AddressDAO;
import com.rsvier.workshop2.customer.Customer;
import com.rsvier.workshop2.customer.CustomerDAO;
import com.rsvier.workshop2.product.Product;
import com.rsvier.workshop2.product.ProductDAO;
import com.rsvier.workshop2.useraccounts.UserMainMenuView;
import com.rsvier.workshop2.utility.Validator;
import com.rsvier.workshop2.view.AdminMainMenuView;

public class OrderController extends Controller {
	
	private OrderView currentMenu;
	private OrderDAO orderModel;
	private OrderLineItemDAO orderLineModel;
	private CustomerDAO customerModel;
	private AddressDAO addressModel;
	private ProductDAO productModel;
	private Scanner input = new Scanner(System.in);
	
	public OrderController(OrderView theView) {
		this.currentMenu = theView;
	}
	
	@Override
	public void runView() {
		currentMenu.displayMenu();
		int userMenuChoice = Integer.parseInt(currentMenu.askUserForInput());
		switch (userMenuChoice) {
		case 1: findAllOrders();
				break;
		case 2: findOrdersOfCustomer();
				break;
		case 3: findPendingOrdersOnly();
				break;
		case 4: findCompletedOrdersOnly();
				break;
		case 5: findOrder();
				break;
		case 6: addNewOrder();
				break;
		case 7: updateOrder();
				break;
		case 8: deleteOrder();
				break;
		case 9: // Returns to main menu
				if (user.isAdmin()) {
					nextController = new AdminMainMenuController(new AdminMainMenuView());
					nextController.setUser(user);
				}
				else {
					nextController = new UserMainMenuController(new UserMainMenuView());
					nextController.setUser(user);
				}
				break;
		default: System.out.println("Not a valid option.");
				currentMenu.displayMenu();
		}
	}

	public void findAllOrders() {
		orderModel = new OrderDAOImpl();
		
		ArrayList<Order> allOrders = (ArrayList<Order>) orderModel.findAllOrders();
		
		currentMenu.displayOrderPropertiesHeader();
		currentMenu.displayDivider();
		currentMenu.displayAllOrders(allOrders);
		
		currentMenu.pressEnterToReturn();
		this.runView();
	}
	
	public void findOrdersOfCustomer() {
		orderModel = new OrderDAOImpl();
		Customer customer = inputCustomer();
		
		ArrayList<Order> allOrdersOfCustomer = (ArrayList<Order>) orderModel.findAllOrdersOfCustomer(customer);
				
		currentMenu.displayOrderPropertiesHeader();
		currentMenu.displayDivider();
		currentMenu.displayAllOrders(allOrdersOfCustomer);
		
		currentMenu.pressEnterToReturn();
		this.runView();
	}
	
	public void findPendingOrdersOnly() {
		orderModel = new OrderDAOImpl();
		
		ArrayList<Order> pendingOrderList = (ArrayList<Order>) orderModel.findPendingOrders();
		
		currentMenu.displayOrderPropertiesHeader();
		currentMenu.displayDivider();
		currentMenu.displayAllOrders(pendingOrderList);
		
		currentMenu.pressEnterToReturn();
		this.runView();
	}
	
	public void findCompletedOrdersOnly() {
		orderModel = new OrderDAOImpl();
		
		ArrayList<Order> completedOrderList = (ArrayList<Order>) orderModel.findCompletedOrders();
		
		currentMenu.displayOrderPropertiesHeader();
		currentMenu.displayDivider();
		currentMenu.displayAllOrders(completedOrderList);
		
		currentMenu.pressEnterToReturn();
		this.runView();
	}
	
	public void findOrder() {
		orderModel = new OrderDAOImpl()
				;
		Order foundOrder = new Order();
		String findThisOrder = currentMenu.askUserForInput();
		if (Validator.isAnInt(findThisOrder)) {
			// TODO Don't like using a Try-Catch here, improve later
			try { // if user input was an int
				foundOrder = orderModel.findOrderById(Long.valueOf(findThisOrder));
			} catch (Exception ex) {
				System.out.println("Could not find an order with that ID.");
			}
		} 
		currentMenu.displayOrderPropertiesHeader();
		currentMenu.displayDivider();
		currentMenu.displayOrderProperties(foundOrder);
		
		currentMenu.pressEnterToReturn();
		this.runView();
	}
	
	public void addNewOrder() {
		System.out.println("To enter a new order, you must associate an existing customer as a first step.");
		System.out.println("Do you wish to continue at present (y) or return (n)?");
		
		boolean continueAddingTheOrder = currentMenu.asksUserYesOrNo();
		
		if (!continueAddingTheOrder) { // user wants to halt operation
			currentMenu.pressEnterToReturn();
			this.runView();
		} else {	 // create the new order
			orderModel = new OrderDAOImpl();
			Order orderToAdd = new Order();

			Customer customer = inputCustomer();
			orderToAdd.setCustomerOfOrder(customer);

			boolean isShipped = inputShippingStatus();
			orderToAdd.setShipped(isShipped);

			Address shippedToAddress = inputAddressOfCustomer(customer);
			orderToAdd.setShippedTo(shippedToAddress);

			addLineItemToOrder();

			// Creates an order on an order and customer object jointly
			orderModel.createOrder(orderToAdd, customer);
			currentMenu.displayCreateSuccess();
			
			currentMenu.pressEnterToReturn();
			this.runView();
		}
	}
	
	public void updateOrder() {
		currentMenu.displayOrderUpdateMenu();

		String userChoice = input.nextLine(); 
		if(!Validator.validateMenuChoice(userChoice)) { // Necessary to return the menu if input was deemed invalid
			System.out.println("You did not enter a valid number. \n");
			currentMenu.displayOrderUpdateMenu();
		}
		
		int userChoiceNumber = Integer.parseInt(userChoice);
		switch(userChoiceNumber) {
			case 1: editLineItemOfOrder();
					break;
			case 2: addLineItemToOrder();
					break;
			case 3: removeLineItemFromOrder();
					break;
			case 4: editShippingStatusOfOrder();
					break;
			case 5: editCustomerOfOrder();
					break;
			case 6: editAddressOfOrder();
					break;
			case 9:	currentMenu.displayMenu();
					break;
			default: System.out.println("Invalid choice. \n");
					currentMenu.displayOrderUpdateMenu();
		}
	}
	
	public void deleteOrder() {
		orderModel = new OrderDAOImpl();
		Order orderToDelete = new Order();
		
		Long id = inputValidOrderId();
		
		orderToDelete.setOrderId(id);
		if(idIsInDatabase(id)) {
			currentMenu.displayDeletionConfirmationPrompt(); // Require confirmation
			boolean yesOrNo = currentMenu.asksUserYesOrNo();
			if (yesOrNo) { // user answered yes
				// Method also deletes all associated orderLineItems via DB cascade
				// Reliance on this is solution undesirable in a production environment
				// but an acceptable solution for the moment
				orderModel.deleteOrder(orderToDelete);
				currentMenu.displayDeleteSuccess();
				currentMenu.pressEnterToReturn();
				this.runView();
			} else {
				currentMenu.displayOperationCancelled();
				currentMenu.pressEnterToReturn();
				this.runView();
			}
		} else {
			currentMenu.displayItemNotFound();
			currentMenu.pressEnterToReturn();
			this.runView();
		}
	}
	
	/* EDIT ORDER METHODS */
	
	public void editLineItemOfOrder() {
		// TODO write code
	}
	
	public void addLineItemToOrder() {
		// TODO write code
	}
	
	public void removeLineItemFromOrder() {
		// TODO write code
	}
	
	public void editShippingStatusOfOrder() {
		//TODO write code
	}
	
	public void editCustomerOfOrder() {
		//TODO write code
	}
	
	public void editAddressOfOrder() {
		//TODO write code
	}
	
	/* INPUT & HELPER METHODS */
	
	public OrderLineItem createLineItem(Product product, int productQuantity) {
		OrderLineItem lineItem = new OrderLineItem();
		lineItem.setProduct(product);
		lineItem.setProductQuantity(productQuantity);
		return lineItem;
	}
	
	public Customer inputCustomer() {
		System.out.print("Please enter the id of the customer to associate with the current order:");
		Long customerId = input.nextLong();
		return customerModel.findCustomerById(customerId);
	}
	
	public boolean inputShippingStatus() {
		System.out.println("Has the order been shipped yet?");
		boolean shippingStatus = currentMenu.asksUserYesOrNo();
		return shippingStatus;
	}
	
	public Address inputAddressOfCustomer(Customer customer) {
		ArrayList<Address> knownCustomerAddresses = (ArrayList<Address>)addressModel.findAddressesByCustomer(customer);
		currentMenu.displayAddressList();
		System.out.println("Please select the row number (e.g. 1) of the address you want to link to this order shipment:");
		int thisRow = input.nextInt();
		return knownCustomerAddresses.get(thisRow);
	}
	
	public Long inputValidOrderId() {
		currentMenu.promptUserForItemId();
		String attemptAtId = input.nextLine();
		while (!Validator.isAnInt(attemptAtId)) {
			System.out.println("You did not enter a valid order id.");
			attemptAtId = input.nextLine();
		}
		Long id = Long.valueOf(attemptAtId);
		return id;
	}
	
	public boolean idIsInDatabase(Long id) {
		if(orderModel.isOrderStoredWithId(id)) {
			return true;
		} else {
			return false;
		}
	}
}
