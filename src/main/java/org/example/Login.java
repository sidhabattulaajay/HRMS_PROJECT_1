
package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Login {

    // 1. Login verification
    public static void loginToOrganization(Scanner sc) {

        System.out.println("\n===== Login to Organization =====");

        System.out.print("Enter Email: ");
        String email = sc.nextLine();

        System.out.print("Enter Password: ");
        String password = sc.nextLine();

        String sql = "SELECT o.organization_name " +
                "FROM users u " +
                "JOIN organizations o ON u.organization_id = o.organization_id " +
                "WHERE u.email = ? AND u.password = ?";

        try {
            Connection connection = DBConnection.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setString(1, email);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String organizationName =
                        rs.getString("organization_name");

                System.out.println("Login Successful!");
                System.out.println("Welcome to " + organizationName + "!");

                showOrganizationMenu(sc);

            } else {
                System.out.println("Invalid Credentials");
            }

            rs.close();
            ps.close();
            connection.close();

        } catch (SQLException e) {
            System.out.println("Login error: " + e.getMessage());
        }
    }


    // 2. Login submenu
    public static void showLoginMenu(Scanner sc) {

        while (true) {
            System.out.println("\n===== Login Menu =====");
            System.out.println("1. Login to Organization");
            System.out.println("2. Forgot Password");
            System.out.println("3. Back to Main Menu");
            System.out.println("4. Exit");

            System.out.print("Enter your choice: ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    loginToOrganization(sc);
                    break;

                case 2:
                    forgotPassword(sc);
                    break;

                case 3:
                    return;

                case 4:
                    System.out.println("Exiting HRMS...");
                    sc.close();
                    System.exit(0);
                    return;

                default:
                    System.out.println("Invalid choice!");
            }
        }
    }


    // 3. Forgot password
    public static void forgotPassword(Scanner sc) {

        System.out.println("\n===== Forgot Password =====");

        System.out.print("Enter your registered email: ");
        String email = sc.nextLine();

        System.out.print("Enter your new password: ");
        String newPassword = sc.nextLine();

        String sql = "UPDATE users SET password = ? WHERE email = ?";

        try {
            Connection connection = DBConnection.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setString(1, newPassword);
            ps.setString(2, email);

            int result = ps.executeUpdate();

            if (result > 0) {
                System.out.println("Password updated successfully!");
            } else {
                System.out.println("Email not found!");
            }

            ps.close();
            connection.close();

        } catch (SQLException e) {
            System.out.println("Error updating password: " + e.getMessage());
        }
    }


    // 4. Organization menu after successful login
    public static void showOrganizationMenu(Scanner sc) {

        while (true) {
            System.out.println("\n===== Organization Menu =====");
            System.out.println("1. Employee Management");
            System.out.println("2. Attendance Management");
            System.out.println("3. Salary Management");
            System.out.println("4. Notice / Holiday Management");
            System.out.println("5. Logout");

            System.out.print("Enter your choice: ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    Employee.showEmployeeMenu(sc);
                    break;

                case 2:
                    Attendance.showAttendanceMenu(sc);
                    break;

                case 3:
                    Salary.showSalaryMenu(sc);
                    break;

                case 4:
                    Notice.showNoticeMenu(sc);
                    break;

                case 5:
                    System.out.println("Logged out successfully!");
                    return;

                default:
                    System.out.println("Invalid choice!");
            }
        }
    }
}
