
package org.example;

import java.util.Scanner;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;

public class Notice {

    // 1. Notice / Holiday Management Menu
    public static void showNoticeMenu(Scanner sc) {

        while (true) {
            System.out.println("\n===== Notice / Holiday Management =====");
            System.out.println("1. Create Holiday");
            System.out.println("2. View Holidays");
            System.out.println("3. Update Holiday");
            System.out.println("4. Delete Holiday");
            System.out.println("5. Back");

            System.out.print("Enter your choice: ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    createHoliday(sc);
                    break;
                case 2:
                    viewHolidays(sc);
                    break;
                case 3:
                    updateHoliday(sc);
                    break;
                case 4:
                    deleteHoliday(sc);
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    // 2. Create Holiday
    public static void createHoliday(Scanner sc) {

        System.out.println("\n===== Create Holiday =====");

        System.out.print("Enter Organization ID: ");
        int organizationId = sc.nextInt();
        sc.nextLine();

        System.out.print("Enter Holiday Name: ");
        String holidayName = sc.nextLine();

        System.out.print("Enter Holiday Date (YYYY-MM-DD): ");
        String holidayDate = sc.nextLine();

        String sql = "INSERT INTO holidays " +
                "(organization_id, holiday_name, holiday_date) " +
                "VALUES (?, ?, ?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, organizationId);
            ps.setString(2, holidayName);
            ps.setDate(3, Date.valueOf(holidayDate));

            int result = ps.executeUpdate();

            if (result > 0) {
                System.out.println("Holiday created successfully!");
            }

        } catch (SQLException | IllegalArgumentException e) {
            System.out.println("Error creating holiday: " + e.getMessage());
        }
    }

    // 3. View Holidays
    public static void viewHolidays(Scanner sc) {

        System.out.println("\n===== View Holidays =====");

        System.out.print("Enter Organization ID: ");
        int organizationId = sc.nextInt();
        sc.nextLine();

        String sql = "SELECT holiday_id, holiday_name, holiday_date " +
                "FROM holidays WHERE organization_id = ? " +
                "ORDER BY holiday_date";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, organizationId);

            try (ResultSet rs = ps.executeQuery()) {

                boolean found = false;

                while (rs.next()) {
                    found = true;

                    System.out.println("\n--------------------------");
                    System.out.println("Holiday ID: " +
                            rs.getInt("holiday_id"));
                    System.out.println("Holiday Name: " +
                            rs.getString("holiday_name"));
                    System.out.println("Holiday Date: " +
                            rs.getDate("holiday_date"));
                }

                if (!found) {
                    System.out.println("No holidays found.");
                }
            }

        } catch (SQLException e) {
            System.out.println("Error viewing holidays: " + e.getMessage());
        }
    }

    // 4. Update Holiday
    public static void updateHoliday(Scanner sc) {

        System.out.println("\n===== Update Holiday =====");

        System.out.print("Enter Holiday ID: ");
        int holidayId = sc.nextInt();
        sc.nextLine();

        System.out.print("Enter New Holiday Name: ");
        String holidayName = sc.nextLine();

        System.out.print("Enter New Holiday Date (YYYY-MM-DD): ");
        String holidayDate = sc.nextLine();

        String sql = "UPDATE holidays SET holiday_name = ?, " +
                "holiday_date = ? WHERE holiday_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, holidayName);
            ps.setDate(2, Date.valueOf(holidayDate));
            ps.setInt(3, holidayId);

            int result = ps.executeUpdate();

            if (result > 0) {
                System.out.println("Holiday updated successfully!");
            } else {
                System.out.println("Holiday ID not found.");
            }

        } catch (SQLException | IllegalArgumentException e) {
            System.out.println("Error updating holiday: " + e.getMessage());
        }
    }

    // 5. Delete Holiday
    public static void deleteHoliday(Scanner sc) {

        System.out.println("\n===== Delete Holiday =====");

        System.out.print("Enter Holiday ID: ");
        int holidayId = sc.nextInt();
        sc.nextLine();

        String sql = "DELETE FROM holidays WHERE holiday_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, holidayId);

            int result = ps.executeUpdate();

            if (result > 0) {
                System.out.println("Holiday deleted successfully!");
            } else {
                System.out.println("Holiday ID not found.");
            }

        } catch (SQLException e) {
            System.out.println("Error deleting holiday: " + e.getMessage());
        }
    }
}
