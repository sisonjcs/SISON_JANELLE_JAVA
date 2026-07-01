package com.example.jssison.payment;

public abstract sealed class BaseProcessor implements PaymentMethod permits ElectronicPayment, BankTransfer{
	private final String id;
	
	protected BaseProcessor(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}
	
}
