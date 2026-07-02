package com.ibm.day3;

public class Product {
	private String name;
	private double price;
	private int stock;

	public Product(String name, double price, int stock) {
		this.name = name;
		this.price = price;
		this.stock = stock;
	}

	public TransactionReceipt buy(int qty, String txnId, String method) {
		// Check if quantity is valid
		if (qty <= 0) {
			System.out.println("\nQuantity must be at least 1.");
			return null;
		}
		if (stock <= 0 || qty > stock) {
			System.out.println("\nInsufficient stock. Available: " + stock);
			return null;
		}
		stock -= qty;
		return TransactionReceipt.of(txnId, name, qty, qty * price, method);
	}

	// getters
	public String getName() {
		return name;
	}

	public double getPrice() {
		return price;
	}

	public int getStock() {
		return stock;
	}
}
