
package org.example;

import java.util.Scanner;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.math.BigDecimal;

public class Employee {

    // 1. Employee Management Menu
    public static void showEmployeeMenu(Scanner sc) {

        while (true) {
            System.out.println("\n===== Employee Management =====");
            System.out.println("1. Add Employee");
            System.out.println("2. View Employees");
            System.out.println("3. Update Employee");
            System.out.println("4. Delete Employee");
            System.out.println("5. Back");

            System.out.print("Enter your choice: ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    addEmployee(sc);
                    break;

                case 2:
                    viewEmployees(sc);
                    break;

                case 3:
                    updateEmployee(sc);
                    break;

                case 4:
                    deleteEmployee(sc);
                    break;

                case 5:
                    return;

                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    // 2. Add Employee
    public static void addEmployee(Scanner sc) {

        System.out.println("\n===== Add Employee =====");

        System.out.print("Enter Organization ID: ");
        int organizationId = sc.nextInt();
        sc.nextLine();

        System.out.print("Enter Employee Name: ");
        String employeeName = sc.nextLine();

        System.out.print("Enter Email: ");
        String email = sc.nextLine();

        System.out.print("Enter Phone: ");
        String phone = sc.nextLine();

        System.out.print("Enter Department: ");
        String department = sc.nextLine();

        System.out.print("Enter Designation: ");
        String designation = sc.nextLine();

        System.out.print("Enter Joining Date (YYYY-MM-DD): ");
        String joiningDate = sc.nextLine();

        System.out.print("Enter Basic Salary: ");
        BigDecimal basicSalary = new BigDecimal(sc.nextLine());

        String sql = "INSERT INTO employees " +
                "(organization_id, employee_name, email, phone, " +
                "department, designation, joining_date, basic_salary) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            Connection connection = DBConnection.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setInt(1, organizationId);
            ps.setString(2, employeeName);
            ps.setString(3, email);
            ps.setString(4, phone);
            ps.setString(5, department);
            ps.setString(6, designation);
            ps.setDate(7, Date.valueOf(joiningDate));
            ps.setBigDecimal(8, basicSalary);

            int result = ps.executeUpdate();

            if (result > 0) {
                System.out.println("Employee added successfully!");
            }

            ps.close();
            connection.close();

        } catch (SQLException | IllegalArgumentException e) {
            System.out.println("Error adding employee: " + e.getMessage());
        }
    }

    // 3. View Employees
    public static void viewEmployees(Scanner sc) {

        System.out.println("\n===== View Employees =====");

        System.out.print("Enter Organization ID: ");
        int organizationId = sc.nextInt();
        sc.nextLine();

        String sql = "SELECT employee_id, employee_name, email, phone, " +
                "department, designation, joining_date, basic_salary " +
                "FROM employees WHERE organization_id = ?";

        try {
            Connection connection = DBConnection.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setInt(1, organizationId);

            ResultSet rs = ps.executeQuery();
            boolean found = false;

            while (rs.next()) {
                found = true;

                System.out.println("\n--------------------------");
                System.out.println("Employee ID: " + rs.getInt("employee_id"));
                System.out.println("Name: " + rs.getString("employee_name"));
                System.out.println("Email: " + rs.getString("email"));
                System.out.println("Phone: " + rs.getString("phone"));
                System.out.println("Department: " + rs.getString("department"));
                System.out.println("Designation: " + rs.getString("designation"));
                System.out.println("Joining Date: " + rs.getDate("joining_date"));
                System.out.println("Basic Salary: " + rs.getBigDecimal("basic_salary"));
            }

            if (!found) {
                System.out.println("No employees found.");
            }

            rs.close();
            ps.close();
            connection.close();

        } catch (SQLException e) {
            System.out.println("Error viewing employees: " + e.getMessage());
        }
    }

    // 4. Update Employee
    public static void updateEmployee(Scanner sc) {

        System.out.println("\n===== Update Employee =====");

        System.out.print("Enter Employee ID: ");
        int employeeId = sc.nextInt();
        sc.nextLine();

        System.out.print("Enter New Employee Name: ");
        String employeeName = sc.nextLine();

        System.out.print("Enter New Email: ");
        String email = sc.nextLine();

        System.out.print("Enter New Phone: ");
        String phone = sc.nextLine();

        System.out.print("Enter New Department: ");
        String department = sc.nextLine();

        System.out.print("Enter New Designation: ");
        String designation = sc.nextLine();

        System.out.print("Enter New Joining Date (YYYY-MM-DD): ");
        String joiningDate = sc.nextLine();

        System.out.print("Enter New Basic Salary: ");
        BigDecimal basicSalary = new BigDecimal(sc.nextLine());

        String sql = "UPDATE employees SET employee_name = ?, email = ?, " +
                "phone = ?, department = ?, designation = ?, " +
                "joining_date = ?, basic_salary = ? WHERE employee_id = ?";

        try {
            Connection connection = DBConnection.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setString(1, employeeName);
            ps.setString(2, email);
            ps.setString(3, phone);
            ps.setString(4, department);
            ps.setString(5, designation);
            ps.setDate(6, Date.valueOf(joiningDate));
            ps.setBigDecimal(7, basicSalary);
            ps.setInt(8, employeeId);

            int result = ps.executeUpdate();

            if (result > 0) {
                System.out.println("Employee updated successfully!");
            } else {
                System.out.println("Employee ID not found.");
            }

            ps.close();
            connection.close();

        } catch (SQLException | IllegalArgumentException e) {
            System.out.println("Error updating employee: " + e.getMessage());
        }
    }

    // 5. Delete Employee
    public static void deleteEmployee(Scanner sc) {

        System.out.println("\n===== Delete Employee =====");

        System.out.print("Enter Employee ID: ");
        int employeeId = sc.nextInt();
        sc.nextLine();

        String sql = "DELETE FROM employees WHERE employee_id = ?";

        try {
            Connection connection = DBConnection.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setInt(1, employeeId);

            int result = ps.executeUpdate();

            if (result > 0) {
                System.out.println("Employee deleted successfully!");
            } else {
                System.out.println("Employee ID not found.");
            }

            ps.close();
            connection.close();

        } catch (SQLException e) {
            System.out.println("Error deleting employee: " + e.getMessage());
        }
    }
}
