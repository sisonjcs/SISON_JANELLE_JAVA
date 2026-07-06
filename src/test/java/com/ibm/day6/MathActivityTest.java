package com.ibm.day6;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class MathActivityTest {
	/**
	 * Testing MathActivity.add() given two floating values (a and b)
	 * Expected to return correct sum value
	 */
	@Test
	void exec001() {
		assertEquals(MathActivity.add(1.0f, 0.5f), 1.5f);
		assertEquals(MathActivity.add(0.0f, -0.5f), -0.5f);
	}
	
	/**
	 * Testing MathActivity.subtract() given two floating values (a and b)
	 * Expected to return correct difference value
	 */
	@Test
	void exec002() {
		assertEquals(MathActivity.subtract(5.1f, 1.1f), 4.0f);
		assertEquals(MathActivity.subtract(0.3f, 1.1f), -0.8f);
		assertEquals(MathActivity.subtract(1.1f, 1.1f), 0.0f);
	}
	
	/**
	 * Testing MathActivity.multiply() given two floating values (a and b)
	 * Expected to return correct product value
	 */
	@Test
	void exec003() {
		assertEquals(MathActivity.multiply(4.0f, 2.0f), 8.0f);
		assertEquals(MathActivity.multiply(0.3f, -1.0f), -0.3f);
		assertEquals(MathActivity.multiply(1.1f, 1f), 1.1f);
	}
	
	/**
	 * Testing MathActivity.divide() given two floating values (a and b)
	 * Expected to return correct quotient value
	 */
	@Test
	void exec004() {
		assertEquals(MathActivity.divide(99f, 9f), 11f);
		assertEquals(MathActivity.divide(8f, 2f), 4f);
		assertEquals(MathActivity.divide(-4f, 2f), -2f);
	}
	
	/**
	 * Testing MathActivity.divide() given two floating values (a and b) wherein b is equal to 0
	 * Expected to throw an ArithmeticException
	 */
	@Test
	void exec005() {
		ArithmeticException exception = assertThrows(ArithmeticException.class, () -> {
			MathActivity.divide(1.5f, 0f);
		});
		assertEquals("b cannot be 0", exception.getMessage());
	}
		
}
