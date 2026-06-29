package java;

import java.util.Scanner;

public class test {
	static int blackjack(int a, int b) {
		if (a > 21 && b > 21) {
		    return 0;
		}
		    
		if (a > b) {
		    if (a > 21) {
		        return b;
		    }
		    return a;
		} else {
		    if (b > 21) {
		        return a;
		    }
		    return b;
		}
	}
	
	private static void zigzag(int gridSize) {
//		Scanner scanner = new Scanner(System.in);
		
		for (int i = 1; i <= gridSize; i++) {
			if (i % 2 == 0) {
				for (int j = 1; j <= gridSize; j ++) {
					System.out.println(j * i);
				}
			} else {
				for (int j = gridSize + 1; j >= 0; j--) {
					System.out.println(j * i);
				}
			}
			System.out.println("\n");
		}
		
//		scanner.close();
		
	}
	
	public static void main(String[] args) {
		zigzag(5);
	}
}
