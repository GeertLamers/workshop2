package com.rsvier.workshop2.product;

import java.util.ArrayList;

import org.springframework.stereotype.Component;

import com.rsvier.workshop2.view.View;

@Component
public class ProductView extends View<ProductView> {
	
	@Override
	public void displayMessage() {
	}
	
	@Override
	public void displayMenu() { // Constructs the menu
		displayMenuHeader();
		displayDivider();
		displayMenuOptions();
	}
	
	public void displayMenuHeader() {
		System.out.println("Product Menu \n");
	}
	
	public void displayMenuOptions() {
		System.out.print("1. View product inventory \n" +
						 "2. Find a product \n" +
						 "3. Add new product \n" +
						 "4. Update product \n" +
						 "5. Delete product \n" +
						 "9. Return to main menu \n" +
						 "0. Exit program \n" +
						 "\n");
	}
	
	public void displayProductPropertiesHeader() {
		System.out.printf("%-3s %-30s %-8s %-8s %-5s %-15s %-15s %-3s \n",
						   "id",
						   "name",
						   "price",
						   "stock",
						   "year",
						   "country",
						   "variety",
						   "alc. %");
	}
	
	public void displayProductProperties(Product product) {
		System.out.printf("%-3d %-30s %-8s %-8d %-5d %-15s %-15s %-3s \n",
						   product.getProductId(),
						   product.getName(),
						   (product.getPrice()).toString(),
						   product.getStockQuantity(),
						   product.getProducedYear(),
						   product.getCountry(),
						   product.getGrapeVariety(),
						   ((Double)product.getAlcoholPercentage()).toString());
	}
	
	public void displayAllProducts(ArrayList<Product> allProducts) {
		for (Product product : allProducts) {
			displayProductProperties(product);
		}
	}
	
	public void displayProductUpdateMenu() {
		System.out.print("What would you like to update? \n" +
						 "\n" +
						 "1. Name \n" +
						 "2. Price \n" +
						 "3. Stock \n" +
						 "4. Year of production \n" +
						 "5. Country of origin \n" +
						 "6. Grape variety \n" +
						 "7. Alcohol content \n" +
						 "9. Return to the product menu \n" +
						 "0. Exit program \n" +
						 "\n");
	}
}
