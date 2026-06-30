package com.example.jssison.oop;

import java.util.ArrayList;

public class Library {
	ArrayList<Book> books;
	
	public Library() {
		this.books = new ArrayList<Book>();
	}
	
	public void addBook(Book b) {
		books.add(b);
	}
	
	public void showAllBooks() {
		for (Book book : books) {
			book.getInfo();
			System.out.println();
		}
	}
	
	public void borrowBook(String title) {
		for (Book book : books) {
			if (book.getTitle().equals(title)) {
				book.borrowBook();
			}
		}
	}
	
	public void returnBook(String title) {
		for (Book book : books) {
			if (book.getTitle().equals(title)) {
				book.returnBook();
			}
		}
	}
	
	// Getter
	public ArrayList<Book> getBooks() {
		return books;
	}
	
}
