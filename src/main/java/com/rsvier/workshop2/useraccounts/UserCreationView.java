package com.rsvier.workshop2.useraccounts;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.rsvier.workshop2.view.View;

public class UserCreationView extends View<UserCreationView> {
	
	@Override
	public void displayMessage() {
		System.out.println("Please enter your information below in order to create an account.\n\n"
				+ "Press 0 at any point to exit. \n");
	}
	
	@Override
	public void displayMenu() {
	}
	
	public String askUserForInput(String customerProperty) {
		String userInput = "";
		switch (customerProperty) {
		case "username":
			System.out.println("Please enter your username:");
			break;
		case "password":
			System.out.println("Please enter a password");
			break;
		case "firstName":
			System.out.println("Please enter your first name.");
			break;
		case "lastName":
			System.out.println("Please enter your last name.");
			break;
		case "lastNamePreposition":
			System.out.println("Please enter your last name preposition. Leave blank if not relevant.");
			break;
		case "email":
			System.out.println("Please enter your e-mail address.");
			break;
		case "phoneNumber":
			System.out.println("Please enter your (10 digit) phone number.");
			break;
		default:
			System.out.println("YOUR DATABASE HAS CHANGED (view)");
			System.exit(0);
		}
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		try {
			userInput = br.readLine();
			if (userInput.equals("0")) {
				System.out.println("Program is closing..");
				System.exit(0);
			}
		}
		catch (IOException notAValidChoice) {
			notAValidChoice.printStackTrace();
		}
		return userInput;
	}
}