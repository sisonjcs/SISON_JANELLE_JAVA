package com.ibm.day6;

public class MathActivity {
	public static float add(float a, float b) {
		return a+b;
	}
	
	public static float subtract(float a, float b) {
		return a-b;
	}
	
	public static float multiply(float a, float b) {
		return a*b;
	}
	
	public static float divide(float a, float b) throws ArithmeticException {
		if (b == 0) {
			throw new ArithmeticException("b cannot be 0");
		}
		
		return a/b;
	}
}
