package com.example.jssison.training;

import java.util.Scanner;

//import org.springframework.boot.SpringApplication;

//@SpringBootApplication
public class TrainingApplication {
	static Zigzag zigzag = new Zigzag();
	static Assignment1 assignment1 = new Assignment1();
	
	public static void main(String[] args) {
		
		boolean inMenu = true;
		Scanner scanner = new Scanner(System.in);
		
		while (inMenu) {
			System.out.println("\nMENU");
			System.out.println("[1] Blackjack");
			System.out.println("[2] Day of the Week");
			System.out.println("[3] Day of the Week - Pyramid Matching");
			System.out.println("[4] Pyramid - For Loop");
			System.out.println("[5] Pyramid - While Loop");
			System.out.println("[6] Pyramid -  Loop");
			System.out.println("[7] Zigzag");
			System.out.println("[8] Exit");
			
			System.out.println("Enter choice: ");
			String choice = scanner.nextLine();
			
			switch (choice) {
				case "1":
					System.out.println("\nBLACKJACK");
					
					System.out.println("Enter first number: ");
					int a = scanner.nextInt();
					scanner.nextLine();
					
					System.out.println("Enter second number: ");
					int b = scanner.nextInt();
					scanner.nextLine();
					int result = assignment1.blackjack(a, b);
					
					System.out.println("\nResult: " + result);
					
					break;
				case "2":
					System.out.println("\nDAY OF THE WEEK");
					assignment1.printDOTW();
					break;
				case "3":
					System.out.println("\nDAY OF THE WEEK - PATTERN MATCHING");
					assignment1.printDOTW();
					break;
				case "4":
					System.out.println("\nPYRAMID - FOR LOOP");
					assignment1.pyramidForLoop();
					break;
				case "5":
					System.out.println("\nPYRAMID - WHILE LOOP");
					assignment1.pyramidWhileLoop();
					break;
				case "6":
					System.out.println("\nPYRAMID - DO WHILE LOOP");
					assignment1.pyramidDoWhileLoop();
					break;
				case "7":
					System.out.println("\nZIGZAG");
					zigzag.zigzag();
					break;
				case "8":
					inMenu = false;
					System.out.println("Exiting...");
					break;
				default:
					System.out.println("Invalid choice");
					break;
			}
				
		}
	}
}
