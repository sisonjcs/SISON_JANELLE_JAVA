package com.example.jssison.day1;

import java.util.Scanner;

public class Zigzag {
	void zigzag() {
		Scanner scanner = new Scanner(System.in);
		
		System.out.println("Enter a number: ");
		int gridSize = scanner.nextInt();
		
		System.out.println("Results: ");
		
		for (int i = 0; i < gridSize; i++) {
			if (i % 2 == 0) {
				for (int j = 1; j <= gridSize; j ++) {
					System.out.print((i * gridSize + j) + "\t");
				}
			} else {
				for (int j = gridSize; j > 0; j--) {
					System.out.print((i * gridSize + j) + "\t");
				}
			}
			System.out.println("\n");
		}
		
	}
}
