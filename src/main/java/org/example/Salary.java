
package org.example;

import java.util.Scanner;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.math.BigDecimal;

public class Salary {

    // 1. Salary Management Menu
    public static void showSalaryMenu(Scanner sc) {

        while (true) {
            System.out.println("\n===== Salary Management =====");
            System.out.println("1. Create Salary");
            System.out.println("2. View Salary");
            System.out.println("3. Update Salary");
            System.out.println("4. Delete Salary");
            System.out.println("5. Back");

            System.out.print("Enter your choice: ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    createSalary(sc);
                    break;

                case 2:
                    viewSalary(sc);
                    break;

                case 3:
                    updateSalary(sc);
                    break;

                case 4:
                    deleteSalary(sc);
                    break;

                case 5:
                    return;

                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    // 2. Create Salary
    public static void createSalary(Scanner sc) {

        System.out.println("\n===== Create Salary =====");

        System.out.print("Enter Employee ID: ");
        int employeeId = sc.nextInt();
        sc.nextLine();

        System.out.print("Enter Basic Salary: ");
        BigDecimal salary = new BigDecimal(sc.nextLine());

        String sql = "UPDATE employees SET basic_salary = ? " +
                "WHERE employee_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setBigDecimal(1, salary);
            ps.setInt(2, employeeId);

            int result = ps.executeUpdate();

            if (result > 0) {
                System.out.println("Salary created successfully!");
            } else {
                System.out.println("Employee ID not found.");
            }

        } catch (SQLException | NumberFormatException e) {
            System.out.println("Error creating salary: " + e.getMessage());
        }
    }

    // 3. View Salary
    public static void viewSalary(Scanner sc) {

        System.out.println("\n===== View Salary =====");

        System.out.print("Enter Employee ID: ");
        int employeeId = sc.nextInt();
        sc.nextLine();

        String sql = "SELECT employee_id, employee_name, basic_salary " +
                "FROM employees WHERE employee_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, employeeId);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    System.out.println("\nEmployee ID: " +
                            rs.getInt("employee_id"));
                    System.out.println("Employee Name: " +
                            rs.getString("employee_name"));

                    BigDecimal salary = rs.getBigDecimal("basic_salary");

                    if (salary == null) {
                        System.out.println("No salary has been assigned.");
                    } else {
                        System.out.println("Basic Salary: " + salary);
                    }
                } else {
                    System.out.println("Employee ID not found.");
                }
            }

        } catch (SQLException e) {
            System.out.println("Error viewing salary: " + e.getMessage());
        }
    }

    // 4. Update Salary
    public static void updateSalary(Scanner sc) {

        System.out.println("\n===== Update Salary =====");

        System.out.print("Enter Employee ID: ");
        int employeeId = sc.nextInt();
        sc.nextLine();

        System.out.print("Enter New Basic Salary: ");
        BigDecimal salary = new BigDecimal(sc.nextLine());

        String sql = "UPDATE employees SET basic_salary = ? " +
                "WHERE employee_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setBigDecimal(1, salary);
            ps.setInt(2, employeeId);

            int result = ps.executeUpdate();

            if (result > 0) {
                System.out.println("Salary updated successfully!");
            } else {
                System.out.println("Employee ID not found.");
            }

        } catch (SQLException | NumberFormatException e) {
            System.out.println("Error updating salary: " + e.getMessage());
        }
    }

    // 5. Delete Salary
    public static void deleteSalary(Scanner sc) {

        System.out.println("\n===== Delete Salary =====");

        System.out.print("Enter Employee ID: ");
        int employeeId = sc.nextInt();
        sc.nextLine();

        String sql = "UPDATE employees SET basic_salary = NULL " +
                "WHERE employee_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, employeeId);

            int result = ps.executeUpdate();

            if (result > 0) {
                System.out.println("Salary deleted successfully!");
            } else {
                System.out.println("Employee ID not found.");
            }

        } catch (SQLException e) {
            System.out.println("Error deleting salary: " + e.getMessage());
        }
    }
}
