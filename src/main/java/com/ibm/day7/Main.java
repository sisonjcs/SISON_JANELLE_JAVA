package com.ibm.day7;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {
	
	// SQL queries
	private final static String ADD = "INSERT INTO student (email, password, firstname, lastname) VALUES (?, ?, ?, ?)";
	private final static String VIEW = "SELECT studentid, email, firstname, lastname FROM student WHERE studentid = ? OR email LIKE ? OR firstname LIKE ? OR lastname LIKE ?";
	private final static String UPDATE_PASSWORD = "UPDATE student SET password = ?, dateupdated = LOCALTIMESTAMP WHERE email = ?";
	private final static String DELETE = "DELETE FROM student WHERE email = ? AND password = ?";
	private final static String CHECK_EMAIL = "SELECT 1 FROM student WHERE email = ?";
	private final static String CHECK_PASSWORD = "SELECT 1 FROM student WHERE email = ? AND password = ?";
	
	// Scanner
	static Scanner scanner = new Scanner(System.in);
	
	public static void main(String[] args) throws SQLException {
		// Database credentials
		String url = "jdbc:postgresql://localhost:5432/postgres";
		String uname = "postgres";
		String password = "pw123";
		
		// Connect to database
		Connection con = DriverManager.getConnection(url, uname, password);
		// Menu choice
		String choice = "";
		
		// Menu loop
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
			
			switch (choice.toUpperCase().trim()) {
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
	
	/**
	 * Helper function
	 * 
	 * For checking if an email already exists in the database.
	 * 
	 * @param email
	 * @param con
	 * @return true if email exists; else false
	 */
	static boolean emailExists(String email, Connection con) {
		try {
			// Execute query
			PreparedStatement ps = con.prepareStatement(CHECK_EMAIL);
			ps.setString(1, email);
			ResultSet rs = ps.executeQuery();
			
			// Check if a result is found
			if (rs.next()) {
				return true;
			}
		} catch (SQLException e) {
			System.out.println("\nERROR: " + e.getMessage());
		}
		return false;
	}
	
	/**
	 * Helper function
	 * 
	 * For checking if a given email and password combination exists
	 * 
	 * @param email
	 * @param password
	 * @param con
	 * @return true if email and password combination exists; else false
	 */
	static boolean passwordMatches(String email, String password, Connection con) {
		try {
			// Execute query
			PreparedStatement ps = con.prepareStatement(CHECK_PASSWORD);
			ps.setString(1, email);
			ps.setString(2, password);
			ResultSet rs = ps.executeQuery();
			
			// Check if a result is found
			if (rs.next()) {
				return true;
			}
		} catch (SQLException e) {
			System.out.println("\nERROR: " + e.getMessage());
		}
		return false;
	}
	
	/**
	 * Adds a student to the database
	 * 
	 * @param con
	 */
	static void addStudent(Connection con) {
		String email, password, confirmPassword, firstname, lastname;
		
		// Ask for email
		System.out.println("\n=== ADD ===");
		System.out.print("Enter Email: ");
		email = scanner.nextLine();
		
		// Check if email exists in db
		if (emailExists(email.trim(), con)) {
			System.out.println("\nERROR: Email already exists.");
			return;
		}
		
		// Ask for password and confirm password
		System.out.print("Enter Password: ");
		password = scanner.nextLine().trim();
		System.out.print("Confirm Password: ");
		confirmPassword = scanner.nextLine().trim();
		
		// Check if passwords match
		if (!password.equals(confirmPassword)) {
			System.out.println("\nERROR: Passwords do not match.");
			return;
		}
		
		// Enter other details
		System.out.print("Enter First name: ");
		firstname = scanner.nextLine().trim();
		System.out.print("Enter Last name: ");
		lastname = scanner.nextLine().trim();
		
		try {
			// Insert student
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
	
	/**
	 * Updates the existing password of a user to a new password
	 * 
	 * @param con
	 */
	static void updatePassword(Connection con) {
		String email, currentPassword, newPassword, confirmNewPassword;
		
		// Ask for email
		System.out.println("\n=== UPDATE PASSWORD ===");
		System.out.print("Enter email: ");
		email = scanner.nextLine().trim();
		
		// Check if email exists in db
		if (!emailExists(email, con)) {
			System.out.println("\nERROR: Email does not exist.");
			return;
		}
		
		// Ask for old password
		System.out.print("Enter current password: ");
		currentPassword = scanner.nextLine().trim();
		
		// Check if email and password matches in db
		if (!passwordMatches(email, currentPassword, con)) {
			System.out.println("\nERROR: Password does not match existing record.");
			return;
		}
		
		// Ask for new password and confirmation
		System.out.print("Enter new password: ");
		newPassword = scanner.nextLine().trim();
		System.out.print("Confirm new password: ");
		confirmNewPassword = scanner.nextLine().trim();
		
		// Check if password matches
		if (!newPassword.equals(confirmNewPassword)) {
			System.out.println("\nERROR: New passwords do not match.");
			return;
		}
		
		try {
			// Update password
			PreparedStatement ps = con.prepareStatement(UPDATE_PASSWORD);	
			ps.setString(1, newPassword);
			ps.setString(2, email);
			
			ps.executeUpdate();
			
			System.out.println("\nINFO: Successfully updated password.");
		} catch (SQLException e) {
			System.out.println("\nERROR: " + e.getMessage());
		}
	}
	
	/**
	 * Deletes a student from the database
	 * 
	 * @param con
	 */
	static void deleteStudent(Connection con) {
		String email, password, confirmPassword;
		
		// Ask for email
		System.out.println("\n=== DELETE ===");
		System.out.print("Enter email: ");
		email = scanner.nextLine().trim();
		
		// Checks if email exists in db
		if (!emailExists(email, con)) {
			System.out.println("\nERROR: Email does not exist.");
			return;
		}
		
		// Ask for password
		System.out.print("Enter password: ");
		password = scanner.nextLine().trim();
		
		// Check if email and password combination exists in db
		if (!passwordMatches(email, password, con)) {
			System.out.println("\nERROR: Password does not match existing record.");
			return;
		}
		
		System.out.print("Confirm password: ");
		confirmPassword = scanner.nextLine().trim();
		
		// Check if passwords match
		if (!password.equals(confirmPassword)) {
			System.out.println("\nERROR: Passwords do not match.");
			return;
		}
		
		try {
			// Delete student from db
			PreparedStatement ps = con.prepareStatement(DELETE);	
			ps.setString(1, email);
			ps.setString(2, password);
			
			ps.executeUpdate();
			
			System.out.println("\nINFO: Successfully deleted student.");
		} catch (SQLException e) {
			System.out.println("\nERROR: " + e.getMessage());
		}
	}
	
	/**
	 * Helper function
	 * 
	 * For printing student details
	 * 
	 * @param s
	 */
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
	
	/**
	 * Displays the selected student's information
	 * 
	 * @param con
	 */
	static void viewStudent(Connection con) {
		String queryString;
		PreparedStatement ps;
		ResultSet rs;
		
		// Ask for a filter string
		System.out.print("Enter queryString (id/email/first name/last name): ");
		queryString = scanner.nextLine().trim();
		
		/**
		 * hasResults : flag for checking if there is a result returned by the query
		 * isFirst    : flag for determining if the current iteration is the first one
		 */
		boolean hasResults = false, isFirst = true;
		try {
			ps = con.prepareStatement(VIEW);
			
			// Search variables
			try {
				ps.setInt(1, Integer.parseInt(queryString));
			} catch (Exception e) {
				// Fallback
				ps.setInt(1, -1);
			}
			
			ps.setString(2, '%' + queryString + '%');
			ps.setString(3, '%' + queryString + '%');
			ps.setString(4, '%' + queryString + '%');
			
			rs = ps.executeQuery();
			
			// Loop until there are no more results
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
}
