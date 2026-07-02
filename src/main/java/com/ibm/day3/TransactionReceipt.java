package com.ibm.day3;

public record TransactionReceipt(String id, String product, int qty, double amount, String paymentMethod) {

	public static TransactionReceipt of(String id, String product, int qty, double amount, String paymentMethod) {
		return new TransactionReceipt(id, product, qty, amount, paymentMethod);
	}

	public void print() {
		System.out.println("TRANSACTION RECEIPT");
		System.out.printf("Ref #       : %s%n", id);
		System.out.printf("Product     : %s%n", product);
		System.out.printf("Quantity    : %d%n", qty);
		System.out.printf("Total       : Php %.2f%n", amount);
		System.out.printf("Paid via    : %s%n", paymentMethod);
	}
}
