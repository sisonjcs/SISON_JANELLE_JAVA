package com.ibm.day6;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
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
    		summary.setWritable(true);
    	}
    }
    
    @Test
    void coverConstructor() {
    	new LogAnalyzer();
    }
    
	@Test
//	@Disabled
	//Normal
	void exec001() throws IOException {
		String expectedFile = Files.readString(Path.of(FILE_DIR.concat("exec001/summary.txt")));
		
		String logFile = FILE_DIR.concat("exec001/server.log");
		LogAnalyzer.main(new String[] {logFile});
		
		String actualFile = Files.readString(Path.of("resources/summary.txt"));
		assertEquals(expectedFile, actualFile);
		
		String expectedOutput = "Analysis complete. Summary written to summary.txt" + System.lineSeparator();
		assertEquals(expectedOutput, outputStream.toString());
	}
	
	@Test
//	@Disabled
	//Log file not found
	void exec002() throws IOException {
		LogAnalyzer.main(new String[] {"nofile.txt"});
		String expectedOutput = "Log file not found." + System.lineSeparator();

		assertEquals(expectedOutput, outputStream.toString());
	}
	
	@Test
//	@Disabled
	// IOException (broken)
	void exec003() throws IOException {
		String logFile = FILE_DIR.concat("exec003/server.log");
		
		File summary = new File("resources/summary.txt");
		summary.setReadOnly();
		
		LogAnalyzer.main(new String[] {logFile});
		summary.setWritable(true);
		String expectedOutput = "Error writing summary file." + System.lineSeparator() + "Analysis complete. Summary written to summary.txt" + System.lineSeparator();
		assertEquals(expectedOutput, outputStream.toString());
	}
	
	@Test
//	@Disabled
	// missing timestamp brackets
	void exec004() throws IOException {
		String logFile = FILE_DIR.concat("exec004/server.log");
		String expectedFile = Files.readString(Path.of(FILE_DIR.concat("exec004/summary.txt")));
		
		LogAnalyzer.main(new String[] {logFile});
		
		String actualFile = Files.readString(Path.of("resources/summary.txt"));
		
		String expectedOutput = 
				"Skipping malformed line: 2024-05-10 09:00:00] INFO: Server started successfully" 
				+ System.lineSeparator() 
				+ "Skipping malformed line: [2024-05-10 09:00:03 INFO: Configuration file loaded"
				+ System.lineSeparator()
				+ "Skipping malformed line: 2024-05-10 09:00:06 INFO: Database connection established"
				+ System.lineSeparator()
				+ "Analysis complete. Summary written to summary.txt" 
				+ System.lineSeparator();
		
		assertEquals(expectedOutput, outputStream.toString());
		assertEquals(expectedFile, actualFile);
	}
	
	@Test
//	@Disabled
	// invalid log level
	// should throw
	void exec005() throws IOException {
		String logFile = FILE_DIR.concat("exec005/server.log");
		String expectedFile = Files.readString(Path.of(FILE_DIR.concat("exec005/summary.txt")));
		
		LogAnalyzer.main(new String[] {logFile});
		
		String actualFile = Files.readString(Path.of("resources/summary.txt"));
		String expectedOutput = "Skipping malformed line: [2024-05-10 09:00:00] INFORMATION: Server started successfully" + System.lineSeparator() + "Analysis complete. Summary written to summary.txt" + System.lineSeparator();
		assertEquals(expectedOutput, outputStream.toString());
		assertEquals(expectedFile, actualFile);
	}
	
	@Test
//	@Disabled
	void exec006() throws IOException {
		String logFile = FILE_DIR.concat("exec006/server.log");
		String expectedFile = Files.readString(Path.of(FILE_DIR.concat("exec006/summary.txt")));
		
		LogAnalyzer.main(new String[] {logFile});
		
		String actualFile = Files.readString(Path.of("resources/summary.txt"));
		String expectedOutput = "Skipping malformed line: [2024-05-10 09:00:00] INFO" + System.lineSeparator() + "Analysis complete. Summary written to summary.txt" + System.lineSeparator();
		assertEquals(expectedOutput, outputStream.toString());
		assertEquals(expectedFile, actualFile);
	}

	@Test
	void exec007() throws IOException {
		Path logPath = Path.of(FILE_DIR.concat("exec007/server.log"));
		
		FileChannel.open(logPath, StandardOpenOption.READ, StandardOpenOption.WRITE).lock();
		
		LogAnalyzer.main(new String[] {logPath.toString()});

		String expectedOutput = "Error reading file." + System.lineSeparator();
		assertEquals(expectedOutput, outputStream.toString());
	}
}
