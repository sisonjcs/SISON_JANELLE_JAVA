package com.example.jssison.oop;

public class Book {
	private String title;
	private String author;
	private boolean available;
	
	public Book(String title, String author) {
		this.title = title;
		this.author = author;
		this.available = true;
	}
	
	void borrowBook() {
		if (this.available) {
			this.available = false;
		} else {
			System.out.println("Book is already borrowed");
		}
	}
	
	void returnBook() {
		this.available = true;
	}
	
	void getInfo() {
		System.out.println("\nTitle: " + this.title);
		System.out.println("Author: " + this.author);
		System.out.println("Availability: " + (this.available ? "Available" : "Not available"));
	}
	
	// Getters
	public String getTitle() {
		return this.title;
	}
}
