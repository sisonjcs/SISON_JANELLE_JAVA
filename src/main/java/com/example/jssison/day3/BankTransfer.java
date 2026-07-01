package com.example.jssison.day3;

public non-sealed class BankTransfer extends BaseProcessor{
	public BankTransfer(String id) {
		super(id);
	}
	
	@Override
	public void process() {
		System.out.println(getId() + " processed via Bank Transfer");
	}
	
	@Override
	public String toString() {
		return "Bank Transfer";
	}
}
