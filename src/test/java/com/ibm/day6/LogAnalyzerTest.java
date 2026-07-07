package com.ibm.day6;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LogAnalyzerTest {
	
	private static final String FILE_DIR = "src/test/resources/com/ibm/day6/";
	
	private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
	
    @BeforeEach
    void setup() {
    	System.setOut(new PrintStream(outputStream));
    }
    
    @AfterEach
    void teardown() {
    	System.setOut(originalOut);
    	File summary = new File("resources/summary.txt");
    	if (summary.exists()) {
    		summary.delete();
    	}
    }
    
	@Test
	/**
	 * Normal
	 * Given a server.log file that matches the formatting
	 * When LogAnalyzer reads server.log
	 * Then generate summary.txt
	 * @throws IOException
	 */
	void exec001() throws IOException {
		// Expected summary.txt
		String expectedFile = Files.readString(Path.of(FILE_DIR.concat("exec001/summary.txt")));
		
		// server.log file to read
		String logFile = FILE_DIR.concat("exec001/server.log");
		
		// Call LogAnalyzer
		LogAnalyzer.main(new String[] {logFile});
		
		// Actual summary.txt
		String actualFile = Files.readString(Path.of("resources/summary.txt"));
		
		// Expected console output
		String expectedOutput = "Analysis complete. Summary written to summary.txt" + System.lineSeparator();
		
		// Assert equality of summary.txt files and console output
		assertEquals(expectedFile, actualFile);
		assertEquals(expectedOutput, outputStream.toString());
	}
	
	@Test
	/**
	 * Abnormal
	 * Given a non-existent server log file
	 * When LogAnalyzer reads given file
	 * Then throws FileNotFoundException in LogAnalyzer
	 * @throws IOException
	 */
	void exec002() throws IOException {
		// LogAnalyzer is called with a non-existent text file
		LogAnalyzer.main(new String[] {"nofile.txt"});
		
		// Expected console output
		String expectedOutput = "Log file not found." + System.lineSeparator();
		
		// Assert equality of console output
		assertEquals(expectedOutput, outputStream.toString());
		
		// Assert that a NoSuchFileException is thrown when trying to read resources/summary.txt
		assertThrows(NoSuchFileException.class, () -> {
			Files.readString(Path.of(FILE_DIR.concat("resources/summary.txt")));
		});
	}
	
	@Test
	/**
	 * Abnormal
	 * Given non-writeable summary.txt
	 * When LogAnalyzer is called
	 * Then LogAnalyzer throws IOException
	 * @throws IOException
	 */
	void exec003() throws IOException {
		// server.log file to read
		String logFile = FILE_DIR.concat("exec003/server.log");
		
		// summary.txt file to write
		File summary = new File("resources/summary.txt");
		
		// Creates new summary file before locking to read-only
		summary.createNewFile();
		
		// Set summary.txt to read-only
		summary.setReadOnly();
		
		// Call LogAnalyzer
		LogAnalyzer.main(new String[] {logFile});
		
		// Set summary.txt to be writable
		summary.setWritable(true);
		
		// Expected console ouput
		String expectedOutput = 
				"Error writing summary file." 
				+ System.lineSeparator() 
				+ "Analysis complete. Summary written to summary.txt" 
				+ System.lineSeparator();
		
		// Assert equality of console output
		assertEquals(expectedOutput, outputStream.toString());
	
		// Assert that a NoSuchFileException is thrown when trying to read resources/summary.txt
		assertThrows(NoSuchFileException.class, () -> {
			Files.readString(Path.of(FILE_DIR.concat("resources/summary.txt")));
		});
	}
	
	@Test
	/**
	 * Abnormal
	 * Given server.log file with incorrect timestamp formats and non-chronological order of timestamps
	 * When LogAnalyzer is called
	 * Then LogAnalyzer throws multiple MalformedLogEntryExceptions and generates a summary.txt
	 * @throws IOException
	 */
	void exec004() throws IOException {
		// server.log file to read
		String logFile = FILE_DIR.concat("exec004/server.log");
		
		// Expected summary.txt file
		String expectedFile = Files.readString(Path.of(FILE_DIR.concat("exec004/summary.txt")));
		
		// Call LogAnalyzer
		LogAnalyzer.main(new String[] {logFile});
		
		// Actual summary.txt file
		String actualFile = Files.readString(Path.of("resources/summary.txt"));
		
		// Expected console output
		String expectedOutput = 
				"Skipping malformed line: 2024-05-10 09:00:00] INFO: Server started successfully" 
				+ System.lineSeparator() 
				+ "Skipping malformed line: [2024-05-10 09:00:03 INFO: Configuration file loaded"
				+ System.lineSeparator()
				+ "Skipping malformed line: 2024-05-10 09:00:06 INFO: Database connection established"
				+ System.lineSeparator()
				+ "Analysis complete. Summary written to summary.txt" 
				+ System.lineSeparator();
		
		// Assert equality between actual and expected console output
		assertEquals(expectedOutput, outputStream.toString());
		// Assert equality between actual and expected summary.txt files
		assertEquals(expectedFile, actualFile);
	}
	
	@Test
	/**
	 * Abnormal
	 * Given server.log file with invalid log levels
	 * When LogAnalyzer is called
	 * Then LogAnalyzer throws MalformedLogEntryException
	 * @throws IOException
	 */
	void exec005() throws IOException {
		// server.log file to read
		String logFile = FILE_DIR.concat("exec005/server.log");
		
		// Expected summary.txt file
		String expectedFile = Files.readString(Path.of(FILE_DIR.concat("exec005/summary.txt")));
		
		// Call LogAnalyzer
		LogAnalyzer.main(new String[] {logFile});
		
		// Actual summary.txt file
		String actualFile = Files.readString(Path.of("resources/summary.txt"));
		
		// Expected console output
		String expectedOutput = 
				"Skipping malformed line: [2024-05-10 09:00:00] INFORMATION: Server started successfully" 
				+ System.lineSeparator() 
				+ "Skipping malformed line: [2024-05-10 09:00:03] TEST: Configuration file loaded" 
				+ System.lineSeparator()
				+ "Skipping malformed line: [2024-05-10 09:00:06] info: Database connection established" 
				+ System.lineSeparator()
				+ "Skipping malformed line: [2024-05-10 09:00:09] NOMATCH: Listening on port 8080" 
				+ System.lineSeparator()
				+ "Skipping malformed line: [2024-05-10 09:00:12] : User 'admin' logged in" 
				+ System.lineSeparator()
				+ "Analysis complete. Summary written to summary.txt" 
				+ System.lineSeparator();
		
		// Assert equality between actual and expected console output
		assertEquals(expectedOutput, outputStream.toString());
		// Assert equality between actual and expected summary.txt
		assertEquals(expectedFile, actualFile);
	}
	
	@Test
	/**
	 * Abnormal
	 * Given server.log file with missing message
	 * When LogAnalyzer is called
	 * Then LogAnalyzer throws MalformedLogEntryException
	 * @throws IOException
	 */
	void exec006() throws IOException {
		// server.log file to read
		String logFile = FILE_DIR.concat("exec006/server.log");
		
		// Expected summary.txt
		String expectedFile = Files.readString(Path.of(FILE_DIR.concat("exec006/summary.txt")));
		
		// Call LogAnalyzer
		LogAnalyzer.main(new String[] {logFile});
		
		// Actual summary.txt
		String actualFile = Files.readString(Path.of("resources/summary.txt"));
		
		// Expected console output
		String expectedOutput = "Skipping malformed line: [2024-05-10 09:00:00] INFO" + System.lineSeparator() + "Analysis complete. Summary written to summary.txt" + System.lineSeparator();
		
		// Assert equality between expected and actual console output
		assertEquals(expectedOutput, outputStream.toString());
		
		// Assert equality between expected and actual summary.txt
		assertEquals(expectedFile, actualFile);
	}

	@Test
	/**
	 * Abnormal
	 * Given locked (unreadable) server.log file
	 * When LogAnalyzer is called
	 * Then LogAnalyzer throws IOException
	 * @throws IOException
	 */
	void exec007() throws IOException {
		// server.log file to read
		Path logPath = Path.of(FILE_DIR.concat("exec007/server.log"));
		
		// Lock server.log file to prevent reading
		FileChannel.open(logPath, StandardOpenOption.READ, StandardOpenOption.WRITE).lock();
		
		// Call LogAnalyzer 
		LogAnalyzer.main(new String[] {logPath.toString()});

		// Expected console output
		String expectedOutput = "Error reading file." + System.lineSeparator();
		
		// Assert equality between actual and expected console output
		assertEquals(expectedOutput, outputStream.toString());
		
		// Assert that a NoSuchFileException is thrown when trying to read resources/summary.txt
		assertThrows(NoSuchFileException.class, () -> {
			Files.readString(Path.of(FILE_DIR.concat("resources/summary.txt")));
		});
	}
	
	/**
     * Test for covering the constructor
     */
    @Test
    void exec008() {
    	new LogAnalyzer();
    }
}
