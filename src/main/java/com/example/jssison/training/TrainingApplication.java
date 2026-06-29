package com.example.jssison.training;

import java.util.Scanner;

//import org.springframework.boot.SpringApplication;

//@SpringBootApplication
public class TrainingApplication {
	static Zigzag zigzag = new Zigzag();
	static Assignment1 assignment1 = new Assignment1();
	
	public static void main(String[] args) {
		
		boolean inMenu = true;
		
		while (inMenu) {
			Scanner scanner = new Scanner(System.in);
			
			System.out.println("MENU");
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
					System.out.println("BLACKJACK");
					
					int a = scanner.nextInt();
					int b = scanner.nextInt();
					assignment1.blackjack(a, b);
					
					scanner.close();
					break;
				case "2":
					System.out.println("DAY OF THE WEEK");
					assignment1.printDOTW();
					break;
				case "3":
					System.out.println("DAY OF THE WEEK - PATTERN MATCHING");
					assignment1.printDOTW();
					break;
				case "4":
					System.out.println("PYRAMID - FOR LOOP");
					assignment1.pyramidForLoop();
					break;
				case "5":
					System.out.println("PYRAMID - WHILE LOOP");
					assignment1.pyramidWhileLoop();
					break;
				case "6":
					System.out.println("PYRAMID - DO WHILE LOOP");
					assignment1.pyramidDoWhileLoop();
					break;
				case "7":
					System.out.println("ZIGZAG");
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
