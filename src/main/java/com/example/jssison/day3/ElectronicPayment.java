package com.example.jssison.day3;

public abstract sealed class ElectronicPayment extends BaseProcessor permits CreditCard, PayPal{

	protected ElectronicPayment(String id) {
		super(id);
	}
	
}
