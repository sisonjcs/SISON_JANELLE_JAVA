package com.ibm.day7;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {
	
	private final static String ADD= "INSERT INTO student (email, password, firstname, lastname) VALUES (?, ?, ?, ?)";
	private final static String VIEW_BY_STUDENTID = "SELECT * FROM student WHERE studentid = ?";
	private final static String VIEW_BY_EMAIL = "SELECT * FROM student WHERE email = ?";
	private final static String VIEW_BY_FIRSTNAME = "SELECT * FROM student WHERE firstname = ?";
	private final static String VIEW_BY_LASTNAME = "SELECT * FROM student WHERE lastname = ?";
	private final static String UPDATE_PASSWORD = "UPDATE student SET password = ? WHERE email = ?";
	private final static String DELETE = "DELETE FROM student WHERE email = ? AND password = ?";
	private final static String CHECK_EMAIL = "SELECT * FROM student WHERE email = ?";
	private final static String CHECK_PASSWORD = "SELECT * FROM student WHERE email = ? AND password = ?";
	
	static Scanner scanner = new Scanner(System.in);
	
	public static void main(String[] args) throws SQLException {
		String url = "jdbc:postgresql://localhost:5432/postgres";
		String uname = "postgres";
		String password = "pw123";
		
		Connection con = DriverManager.getConnection(url, uname, password);
		
		String choice = "";
		
		while (!choice.equals("Q")) {
			System.out.println("\n=== MENU ===");
			System.out.println("[A]dd");
			System.out.println("[V]iew");
			System.out.println("[U]pdate Password");
			System.out.println("[D]elete");
			System.out.println("[Q]uit");
			System.out.println("============");
			System.out.print("Enter choice: ");
			choice = scanner.nextLine();
			
			switch (choice.toUpperCase()) {
				case "A" -> addStudent(con);
				case "V" -> viewStudent(con);
				case "U" -> updatePassword(con);
				case "D" -> deleteStudent(con);
				case "Q" -> {
					scanner.close();
					con.close();
					System.out.println("\nExiting...");
				}
				default -> System.out.println("\nERROR: Invalid Choice.");
			}
		}
	}
	
	static boolean emailExists(String email, Connection con) {
		try {
			PreparedStatement ps = con.prepareStatement(CHECK_EMAIL);
			ps.setString(1, email);
			ResultSet rs = ps.executeQuery();
			
			if (rs.next() && rs.getString("email").equals(email)) {
				return true;
			}
		} catch (SQLException e) {
			System.out.println("\nERROR: " + e.getMessage());
		}
		return false;
	}
	
	static boolean passwordMatches(String email, String password, Connection con) {
		try {
			PreparedStatement ps = con.prepareStatement(CHECK_PASSWORD);
			ps.setString(1, email);
			ps.setString(2, password);
			ResultSet rs = ps.executeQuery();
			
			if (rs.next() && rs.getString("password").equals(password)) {
				return true;
			}
		} catch (SQLException e) {
			System.out.println("\nERROR: " + e.getMessage());
		}
		return false;
	}
	
	static void addStudent(Connection con) {
		String email, password, confirmPassword, firstname, lastname;
		
		System.out.println("\n=== ADD ===");
		System.out.print("Enter Email: ");
		email = scanner.nextLine();
		
		if (emailExists(email, con)) {
			System.out.println("\nERROR: Email already exists.");
			return;
		}
		
		System.out.print("Enter Password: ");
		password = scanner.nextLine();
		System.out.print("Confirm Password: ");
		confirmPassword = scanner.nextLine();
		
		if (!password.equals(confirmPassword)) {
			System.out.println("\nERROR: Passwords do not match.");
			return;
		}
		
		System.out.print("Enter First name: ");
		firstname = scanner.nextLine();
		System.out.print("Enter Last name: ");
		lastname = scanner.nextLine();
		
		try {
			PreparedStatement ps = con.prepareStatement(ADD);
			ps.setString(1, email);
			ps.setString(2, password);
			ps.setString(3, firstname);
			ps.setString(4, lastname);
			
			ps.executeUpdate();
			
			System.out.println("\nINFO: Successfully added new student.");
		} catch (SQLException e) {
			System.out.println("\nERROR: " + e.getMessage());
		}
	}
	
	static void updatePassword(Connection con) {
		String email, oldPassword, newPassword, confirmNewPassword;
		
		System.out.println("\n=== UPDATE PASSWORD ===");
		System.out.print("Enter email: ");
		email = scanner.nextLine();
		
		if (!emailExists(email, con)) {
			System.out.println("\nERROR: Email does not exist.");
			return;
		}
		
		System.out.print("Enter old password: ");
		oldPassword = scanner.nextLine();
		
		if (!passwordMatches(email, oldPassword, con)) {
			System.out.println("\nERROR: Password does not match existing record.");
			return;
		}
		
		System.out.print("Enter new password: ");
		newPassword = scanner.nextLine();
		System.out.print("Confirm new password: ");
		confirmNewPassword = scanner.nextLine();
		
		if (!newPassword.equals(confirmNewPassword)) {
			System.out.println("\nERROR: New passwords do not match.");
			return;
		}
		
		try {
			PreparedStatement ps = con.prepareStatement(UPDATE_PASSWORD);	
			ps.setString(1, newPassword);
			ps.setString(2, email);
			
			ps.executeUpdate();
			
			System.out.println("\nINFO: Successfully updated password.");
		} catch (SQLException e) {
			System.out.println("\nERROR: " + e.getMessage());
		}
	}
	
	static void deleteStudent(Connection con) {
		String email, password, confirmPassword;
		
		System.out.println("\n=== DELETE ===");
		System.out.print("Enter email: ");
		email = scanner.nextLine();
		
		if (!emailExists(email, con)) {
			System.out.println("\nERROR: Email does not exist.");
			return;
		}
		
		System.out.print("Enter password: ");
		password = scanner.nextLine();
		
		if (!passwordMatches(email, password, con)) {
			System.out.println("\nERROR: Password does not match existing record.");
			return;
		}
		
		System.out.print("Confirm password: ");
		confirmPassword = scanner.nextLine();
		
		if (!password.equals(confirmPassword)) {
			System.out.println("\nERROR: Passwords do not match.");
			return;
		}
		
		try {
			PreparedStatement ps = con.prepareStatement(DELETE);	
			ps.setString(1, email);
			ps.setString(2, password);
			
			ps.executeUpdate();
			
			System.out.println("\nINFO: Successfully deleted student.");
		} catch (SQLException e) {
			System.out.println("\nERROR: " + e.getMessage());
		}
	}
	
	static void printDetails(ResultSet s) {
		
		try {
			int id = s.getInt("studentid");
			String email = s.getString("email");
			String firstName = s.getString("firstname");
			String lastName = s.getString("lastname");
			
			System.out.println("\nStudent ID: " + id);
			System.out.println("Email: " + email);
			System.out.println("First name: " + firstName);
			System.out.println("Last name: " + lastName);
			
		} catch (SQLException e) {
			System.out.println("\nERROR: " + e.getMessage());
		}
	}
	
	static void viewStudent(Connection con) {
		String id, email, firstName, lastName, choice = "";
		PreparedStatement ps;
		ResultSet rs;
		
		System.out.println("\n=== VIEW ===");
		System.out.println("[1] Find by Student ID");
		System.out.println("[2] Find by Email");
		System.out.println("[3] Find by First name");
		System.out.println("[4] Find by Last name");
		System.out.println("[0] Back");
		System.out.print("Enter choice: ");
		choice = scanner.nextLine();
		
		switch (choice) {
			case "1" -> {
				System.out.print("Enter student ID: ");
				id = scanner.nextLine();
				try {
					ps = con.prepareStatement(VIEW_BY_STUDENTID);
					ps.setInt(1, Integer.parseInt(id));
					
					rs = ps.executeQuery();
					if (rs.next()) {
						System.out.println("\n=== RESULTS ===");
						printDetails(rs);						
					} else {
						System.out.println("\nINFO: No student found");
					}
					
				} catch (SQLException e) {
					System.out.println("\nERROR: " + e.getMessage());
				}
			}
			case "2" -> {
				System.out.print("Enter email: ");
				email = scanner.nextLine();
				try {
					ps = con.prepareStatement(VIEW_BY_EMAIL);
					ps.setString(1, email);
					
					rs = ps.executeQuery();
					if (rs.next()) {
						System.out.println("\n=== RESULTS ===");
						printDetails(rs);						
					} else {
						System.out.println("\nINFO: No student found");
					}
				} catch (SQLException e) {
					System.out.println("\nERROR: " + e.getMessage());
				}
				
			}
			case "3" -> {
				System.out.print("Enter first name: ");
				firstName = scanner.nextLine();
				boolean hasResults = false, isFirst = true;
				try {
					ps = con.prepareStatement(VIEW_BY_FIRSTNAME);
					ps.setString(1, firstName);
					
					rs = ps.executeQuery();
					
					
					while (rs.next()) {
						if (isFirst) {
							System.out.print("\n=== RESULTS ===");
							isFirst = false;
						}
						printDetails(rs);
						hasResults = true;
					}
					
					if (!hasResults) {
						System.out.println("\nINFO: No student/s found");
					}
					
				} catch (SQLException e) {
					System.out.println("\nERROR: " + e.getMessage());
				}
				
			}
			case "4" -> {
				System.out.print("Enter last name: ");
				lastName = scanner.nextLine();
				boolean hasResults = false, isFirst = true;
				
				try {
					ps = con.prepareStatement(VIEW_BY_LASTNAME);
					ps.setString(1, lastName);
					
					rs = ps.executeQuery();
					
					while (rs.next()) {
						if (isFirst) {
							System.out.print("\n=== RESULTS ===");
							isFirst = false;
						}
						printDetails(rs);
						hasResults = true;
					}
					
					if (!hasResults) {
						System.out.println("\nINFO: No student/s found");
					}
					
				} catch (SQLException e) {
					System.out.println("\nERROR: " + e.getMessage());
				}
				
			}
			case "0" -> {
				return;
			}
			default -> System.out.println("\nERROR: Invalid choice.");
			
		}
		
	}
}
