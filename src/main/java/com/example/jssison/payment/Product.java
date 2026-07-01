package com.example.jssison.payment;

public class Product {
	private String name;
	private double price;
	private int stock;
	
	public Product(String name, double price, int stock) {
		this.name = name;
		this.price = price;
		this.stock = stock;
	}
	
	TransactionReceipt buy(int amount, String method) {
		if (stock > 0 || amount > stock) {
			stock--;
			return new TransactionReceipt("TXN".concat(name), amount * price, method);
		} else {
			System.out.println("\nNo stock available");
			return null;
		}
	}
	
	//getters
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
