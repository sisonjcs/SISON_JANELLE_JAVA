package com.ibm.day3;

public final class PayPal extends ElectronicPayment {

	public PayPal(String id) {
		super(id);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void process() {
		System.out.println(getId() + " processed via PayPal");
	}
	
	@Override
	public String toString() {
		return "PayPal";
	}

}
