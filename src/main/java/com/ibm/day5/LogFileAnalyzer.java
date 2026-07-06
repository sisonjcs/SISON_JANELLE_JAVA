package com.ibm.day5;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LogFileAnalyzer {
	
	String logFile;
	
	public LogFileAnalyzer(String file) {
		logFile = file;
	}
	
	public static void main(String[] args) {
		DateTimeFormatter timestampFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		String logFile = args[0];
		try (
			BufferedReader br = new BufferedReader(new FileReader(logFile));
			BufferedWriter bw = new BufferedWriter(new FileWriter("summary.txt"));
		) {
			// Initialize variables
			int total = 0;
			Map<String, Integer> entries = new HashMap<>();
			List<String> errorMessages = new ArrayList<>();
			LocalDateTime earliest = LocalDateTime.MAX;
			LocalDateTime latest = LocalDateTime.MIN;
			entries.put("INFO", 0);
			entries.put("WARN", 0);
			entries.put("ERROR", 0);
			
			String line;
			// Flag for checking if first line is empty
			boolean isFirstLine = true;
			
			// Read from server.log
			while ((line = br.readLine()) != null) {
				if (!line.startsWith("[") || !line.contains("]")) {
					throw new MalformedLogEntryException("Missing brackets.");
				}
				
				// Extract details and check if present, throw an exception otherwise
				String timestamp = line.substring(0, line.indexOf("]")).replace("]", "").replace("[","");
				
				if (timestamp.isBlank()) {
					throw new MalformedLogEntryException("Missing timestamp");
				}
				
				String noTimestamp = line.substring(line.indexOf("]"));
				
				if (!noTimestamp.contains(":")) {
					throw new MalformedLogEntryException("Missing colon separator.");
				}
				
				String level = noTimestamp.substring(1, noTimestamp.indexOf(":")).strip();
				
				if (level.isBlank()) {
					throw new MalformedLogEntryException("Missing level.");
				}
				
				String message = noTimestamp.substring(noTimestamp.indexOf(":")).replace(": ", "");

				if (message.isBlank()) {
					throw new MalformedLogEntryException("Missing message.");
				}
				
				// Check if level is correct
				if (level.equals("INFO") | level.equals("WARN") | level.equals("ERROR")) {
					entries.computeIfPresent(level, (key, count) -> count += 1);
					total++;
					if (level.equals("ERROR")) {
						errorMessages.add(message);
					}
				} else {
					throw new MalformedLogEntryException("Unrecognized LEVEL found.");
				}
	
				// Parse timestamp and check if latest and earliest timestamp
				LocalDateTime parsedTimestamp = LocalDateTime.parse(timestamp, timestampFormat);
				
				if (parsedTimestamp.isBefore(earliest)) {
					earliest = parsedTimestamp;
				} else if (parsedTimestamp.isAfter(latest)) {
					latest = parsedTimestamp;
				}
				isFirstLine = false;
			}
			
			// Check if file is empty by checking if the flag was updated
			if (isFirstLine) {
				throw new MalformedLogEntryException("Empty file.");
			}
			
			// Write to summary.txt
			bw.write("Log Summary Report\n");
			bw.write("------------------\n");
			bw.write("Total Entries: " + total + "\n");
			bw.write("INFO: " + entries.get("INFO") + "\n");
			bw.write("WARN: " + entries.get("WARN") + "\n");
			bw.write("ERROR: " + entries.get("ERROR") + "\n\n");
			bw.write("Error messages:\n");
			
			for (String msg : errorMessages) {
				bw.write("- " + msg + "\n");
			}
			
			bw.write("\nEarliest timestamp: " + earliest.format(timestampFormat) + "\n");
			bw.write("Latest timestamp: " + latest.format(timestampFormat));
			
			
		} catch (FileNotFoundException e) {
			System.out.println("ERROR: " + e.getMessage());
		} catch (IOException e) {
			System.out.println("ERROR: " + e.getMessage());
		} catch (MalformedLogEntryException e) {
			System.out.println("ERROR: " + e.getMessage());
		}
	}
}
