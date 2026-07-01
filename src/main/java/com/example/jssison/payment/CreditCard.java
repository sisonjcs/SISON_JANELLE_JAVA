package com.example.jssison.payment;

public final class CreditCard extends ElectronicPayment {

	public CreditCard(String id) {
		super(id);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void process() {
		System.out.println(getId() + " processed via Creidt Card.");
	}
	
	@Override
	public String toString() {
		return "Credit Card";
	}
	
}
