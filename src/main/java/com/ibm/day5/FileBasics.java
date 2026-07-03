package com.ibm.day5;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileBasics {
	
	public static void main(String[] args) {
		Map<String, List<String>> students = new HashMap<>();
		
		try (BufferedReader br = new BufferedReader(new FileReader("student.csv"));
			BufferedWriter bw = new BufferedWriter(new FileWriter("student.json"));
		) {			
			String line;
			while ((line = br.readLine()) != null) {
				String[] data = line.split(",");
				
				if (data.length != 3) {
					throw new Exception("Incomplete data. Cannot write to JSON file.");
				}
				
				students.computeIfAbsent(data[0], k -> new ArrayList<>()).add(data[1]);
				students.computeIfAbsent(data[0], k -> new ArrayList<>()).add(data[2]);
			}
			
			if (students.isEmpty()) {
				throw new Exception("Empty file.");
			}
			
			int count = 0;
			bw.write("[\n");
			for (String key : students.keySet()) {
				bw.write("\t{\n");
				bw.write("\t\t\"id\": " +  "\"" + key + "\"" + ",\n");
				bw.write("\t\t\"name\": " + students.get(key).get(0) + ",\n");
				bw.write("\t\t\"course\": " + students.get(key).get(1) + "\n");
				bw.write("\t}" + (count == students.size() - 1 ? "\n" : ",\n"));
				count++;
			}
			bw.write("]");
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("ERROR: File not found. " + e.getMessage());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("ERROR: " + e.getMessage());
		} catch (Exception e) {
			System.out.println("ERROR: " + e.getMessage());
		}
	}
}
