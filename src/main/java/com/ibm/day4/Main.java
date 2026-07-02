package com.ibm.day4;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class Main {
	public static void main(String[] args) {
		List<Employee> employees = new ArrayList<>();
		employees.add(new Employee("Alice", "IT", 55000));
        employees.add(new Employee("Bob", "Finance", 60000));
        employees.add(new Employee("Alice", "HR", 52000)); // duplicate name
        employees.add(new Employee("Ken", "IT", 60000));
        employees.add(new Employee("Maria", "HR", 50000));
        employees.add(new Employee("John", "Finance", 70000));
        employees.add(new Employee("Ken", "Finance", 65000)); // duplicate name
        employees.add(new Employee("Lara", "IT", 62000));
        employees.add(new Employee("Sam", "HR", 48000));
        employees.add(new Employee("Bob", "IT", 59000)); // duplicate name
	
        Set<String> uniqueEmployees = new HashSet<>();
        for (Employee emp : employees) {
        	uniqueEmployees.add(emp.getName());
        }
        
        // Print unique employees
        System.out.println("UNIQUE EMPLOYEE NAMES");
        for (String emp : uniqueEmployees) {
        	System.out.println("- " + emp);
        }
        
        Map<String, List<Employee>> employeesByDepartment = new HashMap<>();
        for (Employee emp : employees) {
        	employeesByDepartment.computeIfAbsent(emp.getDepartment(), k -> new ArrayList<>()).add(emp);
        }
        
        System.out.println("\nEMPLOYEES GROUPED BY DEPARTMENT");
        for ( String dept : employeesByDepartment.keySet()) {
        	System.out.println(dept);
        	for (Employee emp : employeesByDepartment.get(dept)) {
        		System.out.println("- " + emp.toString());
        	}
        }
        
        // Get highest paid employee in each dept
        System.out.println("\nHIGHEST PAID BY DEPT");
        for (String dept : employeesByDepartment.keySet()) {
        	Employee highestPaid = new Employee("", "", 0);
        	System.out.println(dept);
        	for (Employee emp : employeesByDepartment.get(dept)) {
        		if (emp.getSalary() > highestPaid.getSalary()) {
        			highestPaid = emp;
        		}
        	}
        	System.out.println("- " + highestPaid.toString());
        }
        
        // Sort all emps by salary (descending)
        Collections.sort(employees, (e1, e2) -> Double.compare(e2.getSalary(), e1.getSalary()));
        Set<Double> uniqueSalaries = new TreeSet<>();
        
        System.out.println("\nSALARIES IN DESC ORDER");
        for (Employee emp : employees) {
        	uniqueSalaries.add(emp.getSalary());
        	System.out.println("- " + emp.toString());
        }
        
        System.out.println("\nSALARY IN ORDER");
        for (double sal : uniqueSalaries) {
        	System.out.println("- " + sal);
        }
	}
}
