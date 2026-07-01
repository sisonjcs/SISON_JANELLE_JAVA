package com.example.jssison.payment;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
	
	static String chooseMethod(Scanner s) {
		String paymentChoice = "";
		
		System.out.println("\nPAYMENT METHODS");
		System.out.println("[1] Bank Transfer");
		System.out.println("[2] Credit Card");
		System.out.println("[3] PayPal");
		System.out.println("Enter choice (Default: Bank): ");
		
		switch(paymentChoice) {
			case "1": 
				return "Bank Transfer";
			case "2": 
				return "Credit Card";
			case "3": 
				return "PayPal";
			default: 
				return "Bank Transfer";
		}
	}
	
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		String choice = "";
		ArrayList<TransactionReceipt> transactions = new ArrayList<>();
		ArrayList<Product> products = new ArrayList<> ();
		
		//seed products
		products.add(new Product("Apple", 80, 5));
		products.add(new Product("Banana", 120, 10));
		products.add(new Product("Orange", 200, 6));
		products.add(new Product("Mango", 300, 7));
		products.add(new Product("Kiwi", 750, 2));
		
		
		while (!choice.equals("4")) {
			System.out.println("\nMENU");
			System.out.println("[1] Add a payment method");
			System.out.println("[2] Buy something");
			System.out.println("[3] View transactions");
			System.out.println("[4] Exit");
			System.out.println("Enter choice: ");
			
			choice = scanner.nextLine();
			
			switch (choice) {
				case "1":
					
					String productChoice = "";
					int amount;
					
					for (Product product : products) {
						System.out.println("[" + products.indexOf(product) + 1 + "] " + product.getName() + " | Stock: " + product.getStock() + " | Price: " + product.getPrice());
					}
					
					productChoice = scanner.nextLine();
					
					switch (productChoice) {
						case "1":
							System.out.println("Enter amount: ");
							amount = scanner.nextInt();
							
							products.get(0).buy(amount, chooseMethod(scanner));
							break;
						case "2":
							break;
						case "3":
							break;
						case "4":
							break;
						default:
							break;
					}
					
					break;
				case "2":
					
					break;
				case "3":
					if (transactions.isEmpty()) {
						System.out.println("\nNo transactions found.");
					} else {
						for (TransactionReceipt transaction : transactions) {
							System.out.println("Ref #: " + transaction.id());
							System.out.println("Amount: Php " + transaction.amount());
							System.out.println("Paid via: " + transaction.paymentMethod());
						}
					}
					break;
				case "4":
					System.out.println("\nExiting...");
					scanner.close();
					break;
				default:
					System.out.println("\nInvalid choice. Try again.");
					break;
			}
		}
	}
	
	
}
