package com.rsvier.workshop2.view;
import java.io.*;
import java.util.*;

public abstract class View<K> { //parent version
	protected int possibleUserChoice;
	protected int menuChoice;
	protected boolean validUserChoice;
	protected View userMenuChoice;
	
	public abstract void displayMessage(); //This runs when the menu is loaded. Welcome text etc.
	public abstract void displayMenu(); // Displays the relevant menu
	public View() {} //add HashMap info here and load the possibleMenuOptions
	public View getUserMenuChoice() {
		return userMenuChoice;
	}
	
	public String asksUserForUserChoice(ArrayList<String> listOfUsers) {
		String userInput = "";
		boolean validInput = false; // Don't allow the user to exit unless he enters something valid
		outer:
		while (!validInput) {
			System.out.println(listOfUsers);
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			System.out.println("Please select a user or enter 9 to return");
			try{
				userInput = br.readLine();
				System.out.println(userInput);
				if (userInput.equals("9")) {
					validInput = true;
					break;
				}
				for (String user : listOfUsers) {
					if (user.equals(userInput)) {
						validInput = true;
						break outer;
					}
				}
				System.out.println("You entered an invalid input");
			}
			catch(Exception inputIsNotValidError){
					System.out.println("You entered an invalid input"); // This point should not be reachable
			}
		}
		return userInput;
	}	
	
	public boolean asksUserYesOrNo() {
		String userInput = "";
		boolean validInput = false; // Don't allow the user to exit unless he/she enters something valid
		while (!validInput) {
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			try {
				while (true) {
					System.out.println("Please enter \"Y\" or \"N\"");
					userInput = br.readLine();
					userInput = userInput.trim().toLowerCase();
					switch (userInput) {
					case "y":
						return true;
					case "n":
						return false;
					}
				}
			}
			catch (IOException notAValidChoice) {
				notAValidChoice.printStackTrace();
			}
		}
		return false; // This point should not be reachable
	}
	
	public String askUserForMenuChoice() {
		System.out.print("Please enter a choice:");
		String userInput = "";
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		try {
			userInput = br.readLine();
			userInput = userInput.trim();
			if (userInput.equals("0")) {
				System.out.println("You are about to exit the program. Are you certain?");
				if (asksUserYesOrNo()) { // user entered yes
					System.out.println("Program is closing..");
					System.exit(0); // 0 is always exit
				}
			}
		}
		catch (IOException notAValidChoice) {
			notAValidChoice.printStackTrace();
		}
		return userInput;
	}
	
	public void pressEnterToReturn() {
		System.out.println("\nHit \"ENTER\" to return to the previous menu.");
		Scanner input;
		   try {
			   input = new Scanner(System.in);
			   input.nextLine();
		   } catch (Exception e) {
			   e.printStackTrace();
		   }	   
	}
	
	public void promptUserForItemId() {
		System.out.print("Please enter the items's ID-number: ");
	}
	
	public void displayCanFindByIdAndName() {
		System.out.print("Please enter the item's ID-number or name: ");
	}
	
	public void displayCreateSuccess() {
		System.out.println("Creation of item was successful.");
	}
	
	public void displayUpdateSuccess() {
		System.out.println("Update was successful.");
	}
	
	public void displayDeleteSuccess() {
		System.out.println("Deletion was successful.");
	}
	
	public void displayDeletionConfirmationPrompt() {
		System.out.println("WARNING: Permanently removing from database. Are you certain?");
	}
	
	public void displayOperationFailed() {
		System.out.println("Operation failed. Please try again.");
	}
	
	public void displayOperationCancelled() {
		System.out.println("Operation cancelled.");
	}
	
	public void displayItemNotFound() {
		System.out.println("Item was not found in database.");
	}
	
	public void displayDivider() { // Commonly used to segment information for the user
		System.out.println("==================================================" + "\n");
	}
	
	public void displayLongDivider() { // Commonly used to segment information for the user
	System.out.println("=========================================================================================================");
	}
}
