package com.example.jssison.training;

import java.util.Scanner;

import com.example.jssison.day2.Book;
import com.example.jssison.day2.Library;

//import org.springframework.boot.SpringApplication;


//@SpringBootApplication
public class TrainingApplication {
	public static void main(String[] args) {
		Library library = new Library();
		
		Scanner scanner = new Scanner(System.in);
		
		String choice = "";
		String title;
		String author;
		
		while (!choice.equals(new String("5"))) {
			System.out.println("\nMENU");
			System.out.println("[1] Add a book");
			System.out.println("[2] Borrow a book");
			System.out.println("[3] Return a book");
			System.out.println("[4] See all books");
			System.out.println("[5] Exit");
			System.out.println("Enter choice: ");
			
			choice = scanner.nextLine();
			
			switch (choice) {
				case "1":
					System.out.println("\nADD A BOOK");
					System.out.println("Enter title: ");
					
					title = scanner.nextLine();
					
					System.out.println("Enter author: ");
					author = scanner.nextLine();
					
					library.addBook(new Book(title, author));
					break;
				case "2":
					System.out.println("\nBORROW A BOOK");
					
					if (!library.getBooks().isEmpty()) {
						System.out.println("Enter title: ");
						
						title = scanner.nextLine();
						
						library.borrowBook(title);						
					} else {
						System.out.println("\nNo books to borrow.");
					}
					
					break;
				case "3":
					System.out.println("\nRETURN A BOOK");
					
					if (!library.getBooks().isEmpty()) {
						System.out.println("Enter title: ");
						
						title = scanner.nextLine();
						
						library.returnBook(title);						
					} else {
						System.out.println("\nNo books to return.");
					}
					
					break;
				case "4":
					System.out.println("\nSEE ALL BOOKS");
					
					if (!library.getBooks().isEmpty()) {
						library.showAllBooks();						
					} else {
						System.out.println("\nNo books to show. Add one!");
					}
					
					break;
				case "5":
					System.out.println("\nExiting");
					break;
				default:
					System.out.println("\nInvalid choice. Try again.");
					break;
			}
		}
		
		scanner.close();
	}
}
