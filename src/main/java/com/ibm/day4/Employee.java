package com.ibm.day4;

public class Employee {
	private String name;
	private double salary;
	private String department;
	
	// Constructor
	public Employee(String name, String dept, double salary) {
		this.name = name;
		this.salary = salary;
		this.department = dept;
	}
	
	// Getters
	public String getName() {
		return this.name;
	}
	
	public double getSalary() {
		return this.salary;
	}
	
	public String getDepartment() {
		return this.department;
	}
	
	@Override
	public String toString() {
		return this.name + "(" + this.salary + ")";
	}
}
