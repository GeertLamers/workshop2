package com.rsvier.workshop2.product;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Scanner;

import javax.persistence.EntityManager;

import com.rsvier.workshop2.controller.AdminMainMenuController;
import com.rsvier.workshop2.controller.Controller;
import com.rsvier.workshop2.utility.HibernateService;
import com.rsvier.workshop2.utility.Validator;
import com.rsvier.workshop2.view.AdminMainMenuView;

public class ProductController extends Controller {
	
	private ProductView currentMenu;
	private ProductDAOImpl productModel;
	private EntityManager entityManager = HibernateService.getEntityManager();
	private Scanner input = new Scanner(System.in);
	
	public ProductController(ProductView theView) {
		this.currentMenu = theView;
	}

	@Override
	public void runView() {
		currentMenu.displayMenu();
		boolean validChoice = false;
		while (!validChoice) {
			int userMenuChoice = Integer.parseInt(currentMenu.askUserForMenuChoice());
			switch (userMenuChoice) {
				case 1: findAllProducts();
						validChoice = true;
						break;
				case 2: findProduct();
						validChoice = true;
						break;
				case 3: addNewProduct();
						validChoice = true;
						break;
				case 4: updateProduct();
						validChoice = true;
						break;
				case 5: deleteProduct();
						validChoice = true;
						break;
				case 9: // Returns to main menu
						validChoice = true;
						nextController = new AdminMainMenuController(new AdminMainMenuView());
						break;
				default: System.out.println("Not a valid option.");
						break;
				}
		}
	}
	
	public void findAllProducts() {
		productModel = new ProductDAOImpl(entityManager, Product.class);
		
		ArrayList<Product> allProducts = (ArrayList<Product>) productModel.findAll();
		
		currentMenu.displayProductPropertiesHeader();
		currentMenu.displayLongDivider();
		currentMenu.displayAllProducts(allProducts);
		
		currentMenu.pressEnterToReturn();
		this.runView();
	}
	
	public void findProduct() {
		Product foundProduct = new Product();
		productModel = new ProductDAOImpl(entityManager, Product.class);
		
		// TODO currently no findByName method due to GenericDAO implementation > fix when not lazy
		currentMenu.displayCanFindByIdAndName();
		Long findThisProduct = inputValidProductId();
		
		foundProduct = productModel.findById(Product.class, findThisProduct);
		if (foundProduct == null) {
			System.out.println("A customer could not be found with id: " + findThisProduct);
			System.out.println("Please try again with a different id.");
			currentMenu.pressEnterToReturn();
			this.runView();
		} else {
			currentMenu.displayProductPropertiesHeader();
			currentMenu.displayLongDivider();
			currentMenu.displayProductProperties(foundProduct);

			currentMenu.pressEnterToReturn();
			this.runView();
		}
	}
	
	public void addNewProduct() {
		Product productToAdd = new Product();
		productModel = new ProductDAOImpl(entityManager, Product.class);
		
		System.out.println("Please enter product details below:");

		String name = inputName();
		productToAdd.setName(name);
		
		String price = inputPrice();
		productToAdd.setPrice(new BigDecimal(price));
		
		int stockQuantity = inputStockQuantity();
		productToAdd.setStockQuantity(stockQuantity);
		
		int producedYear = inputYear();
		productToAdd.setProducedYear(producedYear);
		
		String country = inputCountry();
		productToAdd.setCountry(country);
		
		String grapeVariety = inputGrapeVariety();
		productToAdd.setGrapeVariety(grapeVariety);
		
		double alcoholPercentage = inputAlcoholPercentage();
		productToAdd.setAlcoholPercentage(alcoholPercentage);
		
		productModel.create(productToAdd);
		currentMenu.displayCreateSuccess();
		
		currentMenu.pressEnterToReturn();
		this.runView();
	}
	
	public void deleteProduct() {
		Product productToDelete = new Product();
		productModel = new ProductDAOImpl(entityManager, Product.class);
		
		Long id = inputValidProductId();
		productToDelete.setProductId(id);
		System.out.println("You entered the following ID:" + productToDelete.getProductId());
		
		currentMenu.displayDeletionConfirmationPrompt(); // Require user confirmation
		boolean yesOrNo = currentMenu.asksUserYesOrNo();
		if (yesOrNo) { // user answered yes
			productModel.delete(productToDelete);
			currentMenu.displayDeleteSuccess();
			currentMenu.pressEnterToReturn();
			this.runView();
		} else {
			currentMenu.displayOperationCancelled();
			currentMenu.pressEnterToReturn();
			this.runView();
		}
	}
	
	public void updateProduct() {
		currentMenu.displayProductUpdateMenu();
		
		String userChoice = input.nextLine(); 
		if(!Validator.validateMenuChoice(userChoice)) { // Necessary to return the menu if input was deemed invalid
			System.out.println("You did not enter a valid number. \n");
			currentMenu.displayProductUpdateMenu();
		}
		
		int userChoiceNumber = Integer.parseInt(userChoice);
		switch(userChoiceNumber) {
			case 1: editProductName();
					break;
			case 2: editProductPrice();
					break;
			case 3: editProductStockQuantity();
					break;
			case 4: editProductYear();
					break;
			case 5: editProductCountryOfOrigin();
					break;
			case 6: editProductGrapeVariety();
					break;
			case 7: editProductAlcoholContent();
					break;
			case 9:	this.runView();
					break;
			default: System.out.println("Invalid choice. \n");
					currentMenu.displayProductUpdateMenu();
		}
	}
	
	/* EDIT PRODUCT METHODS */
	
	public void editProductName() {
		Product productToUpdate = new Product();
		productModel = new ProductDAOImpl(entityManager, Product.class);
		// requires an id from user to identify which product to update
		Long id = inputValidProductId();

		// Populates product with current data
		productToUpdate = productModel.findById(Product.class, id);
			
		String name = inputName();
		productToUpdate.setName(name);
		productModel.update(productToUpdate);
		currentMenu.pressEnterToReturn();
		this.runView();
	}
	
	public void editProductPrice() {
		Product productToUpdate = new Product();
		productModel = new ProductDAOImpl(entityManager, Product.class);

		Long id = inputValidProductId();
	
		productToUpdate = productModel.findById(Product.class, id);
			
		String price = inputPrice();
		productToUpdate.setPrice(new BigDecimal(price));
		productModel.update(productToUpdate);
		currentMenu.pressEnterToReturn();
		this.runView();
	}
	
	public void editProductStockQuantity() {
		Product productToUpdate = new Product();
		productModel = new ProductDAOImpl(entityManager, Product.class);

		Long id = inputValidProductId();

		productToUpdate = productModel.findById(Product.class, id);
			
		int stockQuantity = inputStockQuantity();
		productToUpdate.setStockQuantity(stockQuantity);
		productModel.update(productToUpdate);
		currentMenu.pressEnterToReturn();
		this.runView();
	}
	
	public void editProductYear() {
		Product productToUpdate = new Product();
		productModel = new ProductDAOImpl(entityManager, Product.class);

		Long id = inputValidProductId();

		productToUpdate = productModel.findById(Product.class, id);
			
		int productYear = inputYear();
		productToUpdate.setProducedYear(productYear);
		productModel.update(productToUpdate);
		currentMenu.pressEnterToReturn();
		this.runView();
	}
	
	public void editProductCountryOfOrigin() {
		Product productToUpdate = new Product();
		productModel = new ProductDAOImpl(entityManager, Product.class);

		Long id = inputValidProductId();

		productToUpdate = productModel.findById(Product.class, id);
			
		String country = inputCountry();
		productToUpdate.setCountry(country);
		productModel.update(productToUpdate);
		currentMenu.pressEnterToReturn();
		this.runView();
	}
	
	public void editProductGrapeVariety() {
		Product productToUpdate = new Product();
		productModel = new ProductDAOImpl(entityManager, Product.class);

		Long id = inputValidProductId();

		productToUpdate = productModel.findById(Product.class, id);
			
		String grapeVariety = inputGrapeVariety();
		productToUpdate.setGrapeVariety(grapeVariety);
		productModel.update(productToUpdate);
		currentMenu.pressEnterToReturn();
		this.runView();
	}
	
	public void editProductAlcoholContent() {
		Product productToUpdate = new Product();
		productModel = new ProductDAOImpl(entityManager, Product.class);

		Long id = inputValidProductId();
		
		productToUpdate = productModel.findById(Product.class, id);
			
		System.out.print("Enter an alcohol percentage (e.g. 14.2):");
		String userInputAlcoholPercentage = input.nextLine();
		productToUpdate.setAlcoholPercentage(Double.parseDouble(userInputAlcoholPercentage));
			
		productModel.update(productToUpdate);
		currentMenu.pressEnterToReturn();
		this.runView();
	}
	
	/* INPUT & HELPER METHODS */
	
	public String inputName() {
		System.out.print("Enter a name:");
		String name = input.nextLine();
		if(name.isEmpty()) { // can't be empty
			return inputName();
		} else {
			return name;
		}
	}
	
	public String inputPrice() {
		System.out.print("Enter a price (e.g. 13.37):");
		String priceAsString = input.nextLine();
		if(!Validator.validatePrice(priceAsString)) {
			return inputPrice();
		} else {
			return priceAsString;
		}
	}
	
	public int inputStockQuantity() {
		System.out.print("Enter a new stock amount:");
		String stockAsString = input.nextLine();
		if(!Validator.isAPositiveOrZeroInt(stockAsString)) {
			return inputStockQuantity();
		} else {
			int stockQuantity = Integer.parseInt(stockAsString);
			return stockQuantity;
		}
	}
	
	public int inputYear() {
		System.out.print("Enter a production year:");
		String yearAsString = input.nextLine();
		if(!Validator.isAnInt(yearAsString)) {
			return inputYear();
		} else {
			int year = Integer.parseInt(yearAsString);
			return year;
		}
	}
	
	public String inputCountry() {
		System.out.print("Enter a country:");
		String country = input.nextLine();
		if(!Validator.validateCountry(country)) { 
			return inputCountry();
		} else {
			// Normalize input to eliminate duplication in database due to case sensitivity
			return Character.toUpperCase(country.charAt(0)) + country.substring(1);
		}
	}
	
	public String inputGrapeVariety() {
		System.out.print("Enter a grape variety:");
		String grapeVariety = input.nextLine();
		if(grapeVariety.isEmpty()) { // can't be empty
			return inputCountry();
		} else {
			return grapeVariety;
		}
	}
	
	public double inputAlcoholPercentage() {
		System.out.println("Enter an alcohol percentage:");
		String percentageAsString = input.nextLine();
		if(!Validator.validateAlcoholPercentage(percentageAsString)) {
			return inputAlcoholPercentage();
		} else {
			double alcoholPercentage = Double.parseDouble(percentageAsString);
			return alcoholPercentage;
		}
	}
	
	public Long inputValidProductId() {
		currentMenu.promptUserForItemId();
		String attemptAtId = input.nextLine();
		while (!Validator.isAnInt(attemptAtId)) {
			System.out.println("You did not enter a valid product id. Please try again:");
			attemptAtId = input.nextLine();
		}
		Long id = Long.valueOf(attemptAtId);
		return id;
	}
}
