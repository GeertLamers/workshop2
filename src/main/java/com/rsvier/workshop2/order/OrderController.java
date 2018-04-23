package com.rsvier.workshop2.order;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.persistence.EntityManager;

import com.rsvier.workshop2.controller.AdminMainMenuController;
import com.rsvier.workshop2.controller.Controller;
import com.rsvier.workshop2.controller.UserMainMenuController;
import com.rsvier.workshop2.customer.*;
import com.rsvier.workshop2.product.Product;
import com.rsvier.workshop2.product.ProductDAOImpl;
import com.rsvier.workshop2.useraccounts.Account.OwnerType;
import com.rsvier.workshop2.useraccounts.UserMainMenuView;
import com.rsvier.workshop2.utility.HibernateService;
import com.rsvier.workshop2.utility.Validator;
import com.rsvier.workshop2.view.AdminMainMenuView;

public class OrderController extends Controller {
	
	private OrderDAOImpl orderModel;
	private OrderView currentMenu;
	private OrderLineItemDAOImpl orderLineModel;
	private CustomerDAOImpl customerModel;
	private ProductDAOImpl productModel;
	private AddressDAOImpl addressModel;
	private EntityManager entityManager = HibernateService.getEntityManager();
	private Scanner input = new Scanner(System.in);
	
	public OrderController(OrderView theView) {
		this.currentMenu = theView;
	}
	
	@Override
	public void runView() {
		currentMenu.displayMenu();
		boolean validChoice = false;
		while (!validChoice) {
			int userMenuChoice = Integer.parseInt(currentMenu.askUserForMenuChoice());
			switch (userMenuChoice) {
				case 1: findAllOrders();
						validChoice = false;
						break;
				case 2: findOrdersOfCustomer();
						validChoice = false;
						break;
				case 3: findPendingOrdersOnly();
						validChoice = false;
						break;
				case 4: findCompletedOrdersOnly();
						validChoice = false;
						break;
				case 5: findOrder();
						validChoice = false;
						break;
				case 6: addNewOrder();
						validChoice = true;
						break;
				case 7: updateOrder();
						validChoice = false;
						break;
				case 8: deleteOrder();
						validChoice = false;
						break;
				case 9: // Returns to main menu
						validChoice = true;
						if (loggedInUser.getOwnerType() == OwnerType.ADMIN) {
							nextController = new AdminMainMenuController(new AdminMainMenuView());
						} else {
							nextController = new UserMainMenuController(new UserMainMenuView());
						}
						break;
				default: System.out.println("Not a valid option.");
						break;
			}
		}
	}

	public void findAllOrders() {
		orderModel = new OrderDAOImpl(entityManager, Order.class);
		
		List<Order> allOrders = orderModel.findAll();
		
		currentMenu.displayOrderPropertiesHeader();
		currentMenu.displayDivider();
		currentMenu.displayAllOrders(allOrders);
		
		currentMenu.pressEnterToReturn();
		this.runView();
	}
	
	public void findOrdersOfCustomer() {
		orderModel = new OrderDAOImpl(entityManager, Order.class);
		// The inputCustomer method handles nullchecking
		Customer customer = inputCustomer();
		List<Order> allOrdersOfCustomer = orderModel.findAllOrdersOfCustomer(customer);
		if (allOrdersOfCustomer == null) {
			System.out.println("No orders were found for customer with id: " + customer.getCustomerId());
			System.out.println("You can't retrieve that which does not exist.. so add an order first!");
			currentMenu.pressEnterToReturn();
			this.runView();
		} else {		
			currentMenu.displayOrderPropertiesHeader();
			currentMenu.displayDivider();
			currentMenu.displayAllOrders(allOrdersOfCustomer);

			currentMenu.pressEnterToReturn();
			this.runView();
		}
	}
	
	public void findPendingOrdersOnly() {
		orderModel = new OrderDAOImpl(entityManager, Order.class);
		
		List<Order> pendingOrderList = orderModel.findPendingOrdersOnly();
		
		currentMenu.displayOrderPropertiesHeader();
		currentMenu.displayDivider();
		currentMenu.displayAllOrders(pendingOrderList);
		
		currentMenu.pressEnterToReturn();
		this.runView();
	}
	
	public void findCompletedOrdersOnly() {
		orderModel = new OrderDAOImpl(entityManager, Order.class);
		
		List<Order> completedOrderList = orderModel.findCompletedOrdersOnly();
		
		currentMenu.displayOrderPropertiesHeader();
		currentMenu.displayDivider();
		currentMenu.displayAllOrders(completedOrderList);
		
		currentMenu.pressEnterToReturn();
		this.runView();
	}
	
	// This singular order retrieval method always displays the order level data AND order line level data
	
	public void findOrder() {
		orderModel = new OrderDAOImpl(entityManager, Order.class);
				
		Long findThisOrder = inputValidOrderId();
		Order foundOrder = orderModel.findById(Order.class, findThisOrder);
		if (foundOrder == null) {
			System.out.println("A order could not be found with id: " + foundOrder);
			System.out.println("Please try again with a different id.");
			currentMenu.pressEnterToReturn();
			this.runView();
		}  else {
			currentMenu.displayOrderWithItemDetails(foundOrder);

			currentMenu.pressEnterToReturn();
			this.runView();
		}
	}
	
	public void addNewOrder() {
		System.out.println("To enter a new order, you must associate an existing customer as a first step.");
		System.out.println("Do you wish to continue at present (y) or add a customer first (n)?");
		
		boolean continueAddingTheOrder = currentMenu.asksUserYesOrNo();
		
		if (!continueAddingTheOrder) { // user wants to halt operation
			currentMenu.pressEnterToReturn();
			this.runView();
		} else {	 // create the new order
			orderModel = new OrderDAOImpl(entityManager, Order.class);
			Order orderToAdd = new Order();

			Customer customer = inputCustomer();
			orderToAdd.setCustomerOfOrder(customer);

			boolean isShipped = inputShippingStatus();
			orderToAdd.setShipped(isShipped);

			Address shippedToAddress = inputAddressOfCustomer(customer);
			orderToAdd.setShippedTo(shippedToAddress);

			List<OrderLineItem> lineItems = inputLineItems();
			orderToAdd.setOrderItems(lineItems);

			orderModel.create(orderToAdd);
			currentMenu.displayCreateSuccess();
			
			currentMenu.pressEnterToReturn();
			this.runView();
		}
	}

	public void updateOrder() {
		Order orderToUpdate = provideAnOrderToUpdate();
		
		// Show the order details to the user first so he/she understands best what to update
		currentMenu.displayOrderPropertiesHeader();
		currentMenu.displayLongDivider();
		currentMenu.displayOrderWithItemDetails(orderToUpdate);
		
		currentMenu.displayOrderUpdateMenu();
		
		boolean validChoice = false;
		while (!validChoice) {
			int userMenuChoice = Integer.parseInt(currentMenu.askUserForMenuChoice());
			switch (userMenuChoice) {
				case 1: editLineItemOfOrder(orderToUpdate);
						validChoice = true;
						break;
				case 2: addLineItemToOrder(orderToUpdate);
						validChoice = true;
						break;
				case 3: removeLineItemFromOrder(orderToUpdate);
						validChoice = true;
						break;
				case 4: editShippingStatusOfOrder(orderToUpdate);
						validChoice = true;
						break;
				case 5: editCustomerOfOrder(orderToUpdate);
						validChoice = true;
						break;
				case 6: editAddressOfOrder(orderToUpdate);
						validChoice = true;
						break;
				case 9:	currentMenu.displayMenu();
						validChoice = true;
						break;
				default: System.out.println("Not a valid option. \n");
						break;
			}
		}
	}

	public void deleteOrder() {
		orderModel = new OrderDAOImpl(entityManager, Order.class);
		Order orderToDelete = new Order();
		
		Long id = inputValidOrderId();
		
		orderToDelete.setOrderId(id);
		System.out.println("You entered the following ID:" + orderToDelete.getOrderId());
		
		currentMenu.displayDeletionConfirmationPrompt(); // Require confirmation
		boolean yesOrNo = currentMenu.asksUserYesOrNo();
		if (yesOrNo) { // user answered yes
			// Method also deletes all associated orderLineItems via DB cascade
			// Reliance on this is solution undesirable in a production environment, but an acceptable solution for the moment
			orderModel.delete(orderToDelete);
			currentMenu.displayDeleteSuccess();
			currentMenu.pressEnterToReturn();
			this.runView();
		} else {
			currentMenu.displayOperationCancelled();
			currentMenu.pressEnterToReturn();
			this.runView();
		}
	}
	
	/* EDIT ORDER METHODS */
	
	public Order provideAnOrderToUpdate() {
		Order orderToUpdate = new Order();
		orderModel = new OrderDAOImpl(entityManager, Order.class);
		
		Long id = inputValidOrderId();
		
		orderToUpdate = orderModel.findById(Order.class, id);
		if(orderToUpdate == null) {
			System.out.println("No order was found with id " + id + ".");
			System.out.println("Returning to the order menu.");
			currentMenu.pressEnterToReturn();
			this.runView();
		}
		return orderToUpdate;
	}
	
	public void editLineItemOfOrder(Order orderToUpdate) {
		currentMenu.displayOrderPropertiesHeader();
		currentMenu.displayLongDivider();
		currentMenu.displayOrderWithItemDetails(orderToUpdate);
		
		System.out.println("Select the row you want to update (e.g. 2): ");
		int rowToUpdate = input.nextInt();
		List<OrderLineItem> itemsInOrder = orderToUpdate.getItemsInOrder();
		OrderLineItem lineItemToUpdate = itemsInOrder.get(rowToUpdate);
		
		
	}
	
	public void addLineItemToOrder(Order orderToUpdate) {
		OrderLineItem orderLineToAdd = new OrderLineItem();
		
		orderToUpdate.getItemsInOrder().add(orderLineToAdd);
		
		orderModel.update(orderToUpdate);
	}
	
	public void removeLineItemFromOrder(Order orderToUpdate) {
		//TODO add code
		orderModel.update(orderToUpdate);
	}
	
	public void editShippingStatusOfOrder(Order orderToUpdate) {
		orderToUpdate.setOrderId(inputValidOrderId());
		orderToUpdate.setShipped(inputShippingStatus());
		orderModel.update(orderToUpdate);
	}
	
	public void editCustomerOfOrder(Order orderToUpdate) {
		customerModel = new CustomerDAOImpl(entityManager, Customer.class);
		
		orderModel.update(orderToUpdate);
	}
	
	public void editAddressOfOrder(Order orderToUpdate) {
		addressModel = new AddressDAOImpl(entityManager, Address.class);
		
		orderModel.update(orderToUpdate);	
	}
	
	/* INPUT & HELPER METHODS */
	
	public OrderLineItem createLineItem(Product product, int productQuantity) {
		OrderLineItem lineItem = new OrderLineItem();
		lineItem.setProduct(product);
		lineItem.setProductQuantity(productQuantity);
		return lineItem;
	}
	
	private List<OrderLineItem> inputLineItems() {
		boolean userWantsToAddAnotherLineItem = true;
		List<OrderLineItem> listOfLineItems = new ArrayList<OrderLineItem>();
		
		// Continue to add new items to the order unless the user stops the loop
		do {
			Product product = inputProductForLineItem();
			int quantity = inputQuantityForLineItem();
			OrderLineItem newLineItem = createLineItem(product, quantity);
			listOfLineItems.add(newLineItem);
			
			// Checking with the user whether they want to add another item to this order
			System.out.println("Would you like to add another item?");
			userWantsToAddAnotherLineItem = currentMenu.asksUserYesOrNo();
		} while(userWantsToAddAnotherLineItem);
		
		return listOfLineItems;
	}
	
	public Product inputProductForLineItem() {
		System.out.print("Please enter the id of this line item's product: ");
		Long productId = input.nextLong();
		Product product = productModel.findById(Product.class, productId);
		return product;
	}
	
	public int inputQuantityForLineItem() {
		System.out.println("Please enter the amount of products for this line item: ");
		int quantity = input.nextInt();
		return quantity;
	}
	
	public Customer inputCustomer() {
		System.out.print("Please enter the id of the customer to associate with the current order:");
		Long customerId = input.nextLong();
		Customer customer = customerModel.findById(Customer.class, customerId);
		if(customer == null) {
			currentMenu.displayOperationFailed();
			System.out.println("No customer was found with id " + customerId);
			System.out.println("Would you like to try again or return to the order menu?");
			boolean tryAddingACustomer = currentMenu.asksUserYesOrNo();
			if(tryAddingACustomer) {
				return inputCustomer();
			} else {
				currentMenu.pressEnterToReturn();
				this.runView();
			}
		}
		return customer;
	}
	
	public boolean inputShippingStatus() {
		System.out.println("Has the order been shipped yet?");
		boolean shippingStatus = currentMenu.asksUserYesOrNo();
		return shippingStatus;
	}
	
	public Address inputAddressOfCustomer(Customer customer) {
		addressModel = new AddressDAOImpl(entityManager, Address.class);
		ArrayList<Address> knownCustomerAddresses = (ArrayList<Address>)addressModel.findAddressesByCustomer(customer);
		// This is pretty dirty... shame on me
		(new AddressView()).displayAllAddresses(knownCustomerAddresses);
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
}
