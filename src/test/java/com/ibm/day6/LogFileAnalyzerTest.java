package com.ibm.day6;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import com.ibm.day5.LogFileAnalyzer;

class LogFileAnalyzerTest {

	private static LogFileAnalyzer logFileAnalyzer;
	
	private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	
	private final PrintStream originalOut = System.out;
	
	@BeforeAll
	void setup() {
		System.setOut(new PrintStream(outputStream));
	}
	
	@ParameterizedTest
	@ValueSource(strings = "server.log")
	void test001_GivenEmptyFile_WhenLogFileAnalyzer_ThenPrintmalformedLogException(String file) {
		logFileAnalyzer = new LogFileAnalyzer(file);
		
		String expected = "Empty file." + System.lineSeparator();
		assertEquals(expected, logFileAnalyzer);
	}

}
