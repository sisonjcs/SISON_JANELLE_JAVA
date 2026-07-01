package com.example.jssison.day3;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {

	// For TXN ID, increments after every successful transaction
	private static final AtomicInteger TXN_COUNTER = new AtomicInteger(1);

	static String nextTxnId() {
		return String.format("TXN-%04d", TXN_COUNTER.getAndIncrement());
	}

	static void addPaymentMethod(Scanner s, List<BaseProcessor> wallet) {
		System.out.println("\nADD PAYMENT METHOD");
		System.out.println("[1] Bank Transfer");
		System.out.println("[2] Credit Card");
		System.out.println("[3] PayPal");
		System.out.print("Enter choice: ");
		String choice = s.nextLine().trim();

		switch (choice) {
			case "1" -> {
				System.out.print("Enter bank account number: ");
				String acct = s.nextLine().trim();
				wallet.add(new BankTransfer(acct));
				System.out.println("Bank Transfer account added: " + acct);
			}
			case "2" -> {
				System.out.print("Enter credit card number (last 4 digits): ");
				String card = s.nextLine().trim();
				wallet.add(new CreditCard("**** **** **** " + card));
				System.out.println("Credit Card added: **** **** **** " + card);
			}
			case "3" -> {
				System.out.print("Enter PayPal email: ");
				String email = s.nextLine().trim();
				wallet.add(new PayPal(email));
				System.out.println("PayPal account added: " + email);
			}
			default -> System.out.println("Invalid choice. No method added.");
		}
	}

	// Handle choosing payment methods for transactions
	static BaseProcessor chooseMethod(Scanner s, List<BaseProcessor> wallet) {
		if (wallet.isEmpty()) {
			System.out.println("\nNo payment methods registered. Please add one first.");
			return null;
		}

		System.out.println("\nSELECT PAYMENT METHOD");
		for (int i = 0; i < wallet.size(); i++) {
			System.out.printf("[%d] %s  (%s)%n", i + 1, wallet.get(i), wallet.get(i).getId());
		}
		System.out.print("Enter choice: ");
		String input = s.nextLine().trim();

		try {
			int i = Integer.parseInt(input) - 1;
			if (i < 0 || i >= wallet.size()) {
				System.out.println("Invalid selection.");
				return null;
			}
			return wallet.get(i);
		} catch (NumberFormatException e) {
			System.out.println("Invalid input.");
			return null;
		}
	}

	static void buyProduct(Scanner s, List<Product> products, List<BaseProcessor> wallet,
			List<TransactionReceipt> transactions) {

		if (products.isEmpty()) {
			System.out.println("\nNo products available.");
			return;
		}

		// Show product list
		System.out.println("\nPRODUCTS");
		for (int i = 0; i < products.size(); i++) {
			Product p = products.get(i);
			System.out.printf("[%d] %-10s | Stock: %2d | Price: Php %.2f%n",
					i + 1, p.getName(), p.getStock(), p.getPrice());
		}
		System.out.println("[0] Cancel");
		System.out.print("Enter product number: ");
		String productInput = s.nextLine().trim();

		int productIdx;
		try {
			productIdx = Integer.parseInt(productInput) - 1;
		} catch (NumberFormatException e) {
			System.out.println("Invalid input.");
			return;
		}

		if (productIdx == -1) return; // user cancelled
		if (productIdx < 0 || productIdx >= products.size()) {
			System.out.println("Invalid product selection.");
			return;
		}

		Product selected = products.get(productIdx);

		// Quantity
		System.out.printf("Enter quantity (available: %d): ", selected.getStock());
		int qty;
		try {
			qty = Integer.parseInt(s.nextLine().trim());
		} catch (NumberFormatException e) {
			System.out.println("Invalid quantity.");
			return;
		}

		// Payment method
		BaseProcessor processor = chooseMethod(s, wallet);
		if (processor == null) return;

		// Execute purchase
		String txnId = nextTxnId();
		TransactionReceipt receipt = selected.buy(qty, txnId, processor.toString());

		if (receipt != null) {
			processor.process();
			transactions.add(receipt);
			System.out.println("\nPurchase successful!");
			receipt.print();
		}
	}

	static void viewTransactions(List<TransactionReceipt> transactions) {
		if (transactions.isEmpty()) {
			System.out.println("\nNo transactions found.");
			return;
		}
		System.out.println("\nTRANSACTION HISTORY (" + transactions.size() + " record(s))");
		for (TransactionReceipt t : transactions) {
			t.print();
		}
	}

	static void viewProducts(List<Product> products) {
		System.out.println("\nPRODUCT LIST");
		for (int i = 0; i < products.size(); i++) {
			Product p = products.get(i);
			System.out.printf("[%d] %-10s | Stock: %2d | Price: Php %.2f%n",
					i + 1, p.getName(), p.getStock(), p.getPrice());
		}
	}

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		String choice = "";

		List<TransactionReceipt> transactions = new ArrayList<>();
		List<BaseProcessor> wallet = new ArrayList<>();

		// Seed products
		List<Product> products = new ArrayList<>();
		products.add(new Product("Apple",  80.00, 5));
		products.add(new Product("Banana", 120.00, 10));
		products.add(new Product("Orange", 200.00, 6));
		products.add(new Product("Mango",  300.00, 7));
		products.add(new Product("Kiwi",   750.00, 2));

		System.out.println("PAYMENT SYSTEM");

		// Menu
		while (!choice.equals("5")) {
			System.out.println("\nMENU");
			System.out.println("[1] Add a payment method");
			System.out.println("[2] Buy something");
			System.out.println("[3] View transactions");
			System.out.println("[4] View products");
			System.out.println("[5] Exit");
			System.out.print("Enter choice: ");

			choice = scanner.nextLine().trim();

			// Handle menu choices
			switch (choice) {
				case "1" -> addPaymentMethod(scanner, wallet);
				case "2" -> buyProduct(scanner, products, wallet, transactions);
				case "3" -> viewTransactions(transactions);
				case "4" -> viewProducts(products);
				case "5" -> {
					System.out.println("\nExiting...");
					scanner.close();
				}
				default -> System.out.println("\nInvalid choice. Try again.");
			}
		}
	}
}
