package com.example.jssison.training;

import java.util.*;

public class Assignment1 {
	enum Days {
		MONDAY,
		TUESDAY,
		WEDNESDAY,
		THURSDAY,
		FRIDAY,
		SATURDAY,
		SUNDAY
	};
	
	// Blackjack activity
	int blackjack(int a, int b) {
		if (a > 21 && b > 21) {
			return 0;
		}
		
		if (a > b) {
			if (a > 21) {
				return b;
			}
			return b;
		} else {
			if (b > 21) {
				return a;
			}
			return b;
		}
	}
	
	void printDOTW() {
		Scanner scanner = new Scanner(System.in);
		
		System.out.println("Enter number: ");
		String dayNumber = scanner.nextLine();
		
		System.out.println("Day of the week");
		switch (dayNumber) {
			case "1":
				System.out.println(Days.MONDAY);
				break;
			case "2":
				System.out.println(Days.TUESDAY);
				break;
			case "3":
				System.out.println(Days.WEDNESDAY);
				break;
			case "4":
				System.out.println(Days.THURSDAY);
				break;
			case "5":
				System.out.println(Days.FRIDAY);
				break;
			case "6":
				System.out.println(Days.SATURDAY);
				break;
			case "7":
				System.out.println(Days.SUNDAY);
				break;
			default:
				System.out.println("Invalid day number");
				break;
		}
		
		scanner.close();
	}
	
	// Switch problem (print day of the week based on the number input)
	void printDOTWPatternMatching() {
		Scanner scanner = new Scanner(System.in);
		
		System.out.println("Enter number: ");
		String dayNumber = scanner.nextLine();
		
		System.out.println("Day of The Week: ");
		switch (dayNumber) {
			case "1" -> System.out.println(Days.MONDAY);
			case "2" -> System.out.println(Days.TUESDAY);
			case "3" -> System.out.println(Days.WEDNESDAY);
			case "4" -> System.out.println(Days.THURSDAY);
			case "5" -> System.out.println(Days.FRIDAY);
			case "6" -> System.out.println(Days.SATURDAY);
			case "7" -> System.out.println(Days.SUNDAY);
			default -> System.out.println("Invalid day number");
			
		}
		
		scanner.close();
	}
	
	void pyramidForLoop() {
		Scanner scanner = new Scanner(System.in);
		
		System.out.println("Enter n: ");
		int n = scanner.nextInt();
		scanner.nextLine();
		
		// while invalid n
		while (n > 20 || n < 1) {
			System.out.println("Invalid! Choose from 1 - 20");
			System.out.println("Enter n: ");
			n = scanner.nextInt();
			scanner.nextLine();
		}
		
		for (int i = 1; i <= n; i++) {
			for (int j=1; j <= i; j++) {
				System.out.print(j + "\t");
			}
			System.out.println();
		}
		
		scanner.close();
	}
	
	void pyramidWhileLoop() {
		Scanner scanner = new Scanner(System.in);
		
		System.out.println("Enter n: ");
		int n = scanner.nextInt();
		scanner.nextLine();
		
		// while invalid n
		while (n > 20 || n < 1) {
			System.out.println("Invalid! Choose from 1 - 20");
			System.out.println("Enter n: ");
			n = scanner.nextInt();
			scanner.nextLine();
		}
		
		int i=1;
		while (i <= n) {
			int j=1;
			while (j <= i) {
				System.out.print(j + "\t");
				j++;
			}
			System.out.println("");
			i++;
		}
		
		scanner.close();
	}
	
	void pyramidDoWhileLoop() {
		Scanner scanner = new Scanner(System.in);
		
		System.out.println("Enter n: ");
		int n = scanner.nextInt();
		scanner.nextLine();
		
		// while invalid n
		while (n > 20 || n < 1) {
			System.out.println("Invalid! Choose from 1 - 20");
			System.out.println("Enter n: ");
			n = scanner.nextInt();
			scanner.nextLine();
		}
		
		int i=1;
		
		do {
			int j=1;
			do {
				System.out.print(j + "\t");
				j++;
			}while (j <= i);
			System.out.println();
			i++;
		} while (i <= n);
		
		scanner.close();
	}
}
