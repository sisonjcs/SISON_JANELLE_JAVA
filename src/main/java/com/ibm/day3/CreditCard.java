package com.ibm.day3;

public final class CreditCard extends ElectronicPayment {

	public CreditCard(String id) {
		super(id);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void process() {
		System.out.println(getId() + " processed via Credit Card.");
	}
	
	@Override
	public String toString() {
		return "Credit Card";
	}
	
}
