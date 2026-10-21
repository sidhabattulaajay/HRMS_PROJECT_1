
package org.example;

import java.util.Scanner;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.sql.Time;

public class Attendance {

    // 1. Attendance Management Menu
    public static void showAttendanceMenu(Scanner sc) {

        while (true) {
            System.out.println("\n===== Attendance Management =====");
            System.out.println("1. Create Attendance");
            System.out.println("2. View Attendance");
            System.out.println("3. Update Attendance");
            System.out.println("4. Delete Attendance");
            System.out.println("5. Back");

            System.out.print("Enter your choice: ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    createAttendance(sc);
                    break;

                case 2:
                    viewAttendance(sc);
                    break;

                case 3:
                    updateAttendance(sc);
                    break;

                case 4:
                    deleteAttendance(sc);
                    break;

                case 5:
                    return;

                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    // 2. Create Attendance
    public static void createAttendance(Scanner sc) {

        System.out.println("\n===== Create Attendance =====");

        System.out.print("Enter Employee ID: ");
        int employeeId = sc.nextInt();
        sc.nextLine();

        System.out.print("Enter Attendance Date (YYYY-MM-DD): ");
        String attendanceDate = sc.nextLine();

        System.out.print("Enter Status (Present/Absent/Leave/Half-Day): ");
        String status = sc.nextLine();

        System.out.print("Enter Check-in Time (HH:MM:SS, or leave blank): ");
        String checkIn = sc.nextLine();

        System.out.print("Enter Check-out Time (HH:MM:SS, or leave blank): ");
        String checkOut = sc.nextLine();

        System.out.print("Enter Remarks (or leave blank): ");
        String remarks = sc.nextLine();

        String sql = "INSERT INTO attendance " +
                "(employee_id, attendance_date, status, check_in, check_out, remarks) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, employeeId);
            ps.setDate(2, Date.valueOf(attendanceDate));
            ps.setString(3, status);

            if (checkIn.isBlank()) {
                ps.setNull(4, java.sql.Types.TIME);
            } else {
                ps.setTime(4, Time.valueOf(checkIn));
            }

            if (checkOut.isBlank()) {
                ps.setNull(5, java.sql.Types.TIME);
            } else {
                ps.setTime(5, Time.valueOf(checkOut));
            }

            if (remarks.isBlank()) {
                ps.setNull(6, java.sql.Types.VARCHAR);
            } else {
                ps.setString(6, remarks);
            }

            int result = ps.executeUpdate();

            if (result > 0) {
                System.out.println("Attendance created successfully!");
            }

        } catch (SQLException | IllegalArgumentException e) {
            System.out.println("Error creating attendance: " + e.getMessage());
        }
    }

    // 3. View Attendance
    public static void viewAttendance(Scanner sc) {

        System.out.println("\n===== View Attendance =====");
        System.out.println("1. View Attendance by Employee ID");
        System.out.println("2. View All Attendance");

        System.out.print("Enter your choice: ");
        int choice = sc.nextInt();
        sc.nextLine();

        String sql;

        if (choice == 1) {
            System.out.print("Enter Employee ID: ");
            int employeeId = sc.nextInt();
            sc.nextLine();

            sql = "SELECT a.attendance_id, a.employee_id, e.employee_name, " +
                    "a.attendance_date, a.status, a.check_in, a.check_out, a.remarks " +
                    "FROM attendance a JOIN employees e " +
                    "ON a.employee_id = e.employee_id " +
                    "WHERE a.employee_id = ? ORDER BY a.attendance_date";

            try (Connection connection = DBConnection.getConnection();
                 PreparedStatement ps = connection.prepareStatement(sql)) {

                ps.setInt(1, employeeId);
                displayAttendance(ps);

            } catch (SQLException e) {
                System.out.println("Error viewing attendance: " + e.getMessage());
            }

        } else if (choice == 2) {

            sql = "SELECT a.attendance_id, a.employee_id, e.employee_name, " +
                    "a.attendance_date, a.status, a.check_in, a.check_out, a.remarks " +
                    "FROM attendance a JOIN employees e " +
                    "ON a.employee_id = e.employee_id " +
                    "ORDER BY a.attendance_date";

            try (Connection connection = DBConnection.getConnection();
                 PreparedStatement ps = connection.prepareStatement(sql)) {

                displayAttendance(ps);

            } catch (SQLException e) {
                System.out.println("Error viewing attendance: " + e.getMessage());
            }

        } else {
            System.out.println("Invalid choice!");
        }
    }

    // Helper method to display attendance records
    private static void displayAttendance(PreparedStatement ps)
            throws SQLException {

        try (ResultSet rs = ps.executeQuery()) {

            boolean found = false;

            while (rs.next()) {
                found = true;

                System.out.println("\n--------------------------");
                System.out.println("Attendance ID: " +
                        rs.getInt("attendance_id"));
                System.out.println("Employee ID: " +
                        rs.getInt("employee_id"));
                System.out.println("Employee Name: " +
                        rs.getString("employee_name"));
                System.out.println("Date: " +
                        rs.getDate("attendance_date"));
                System.out.println("Status: " +
                        rs.getString("status"));
                System.out.println("Check-in: " +
                        rs.getTime("check_in"));
                System.out.println("Check-out: " +
                        rs.getTime("check_out"));
                System.out.println("Remarks: " +
                        rs.getString("remarks"));
            }

            if (!found) {
                System.out.println("No attendance records found.");
            }
        }
    }

    // 4. Update Attendance
    public static void updateAttendance(Scanner sc) {

        System.out.println("\n===== Update Attendance =====");

        System.out.print("Enter Attendance ID: ");
        int attendanceId = sc.nextInt();
        sc.nextLine();

        System.out.print("Enter New Attendance Date (YYYY-MM-DD): ");
        String attendanceDate = sc.nextLine();

        System.out.print("Enter New Status (Present/Absent/Leave/Half-Day): ");
        String status = sc.nextLine();

        System.out.print("Enter New Check-in Time (HH:MM:SS, or leave blank): ");
        String checkIn = sc.nextLine();

        System.out.print("Enter New Check-out Time (HH:MM:SS, or leave blank): ");
        String checkOut = sc.nextLine();

        System.out.print("Enter New Remarks (or leave blank): ");
        String remarks = sc.nextLine();

        String sql = "UPDATE attendance SET attendance_date = ?, status = ?, " +
                "check_in = ?, check_out = ?, remarks = ? " +
                "WHERE attendance_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setDate(1, Date.valueOf(attendanceDate));
            ps.setString(2, status);

            if (checkIn.isBlank()) {
                ps.setNull(3, java.sql.Types.TIME);
            } else {
                ps.setTime(3, Time.valueOf(checkIn));
            }

            if (checkOut.isBlank()) {
                ps.setNull(4, java.sql.Types.TIME);
            } else {
                ps.setTime(4, Time.valueOf(checkOut));
            }

            if (remarks.isBlank()) {
                ps.setNull(5, java.sql.Types.VARCHAR);
            } else {
                ps.setString(5, remarks);
            }

            ps.setInt(6, attendanceId);

            int result = ps.executeUpdate();

            if (result > 0) {
                System.out.println("Attendance updated successfully!");
            } else {
                System.out.println("Attendance ID not found.");
            }

        } catch (SQLException | IllegalArgumentException e) {
            System.out.println("Error updating attendance: " + e.getMessage());
        }
    }

    // 5. Delete Attendance
    public static void deleteAttendance(Scanner sc) {

        System.out.println("\n===== Delete Attendance =====");

        System.out.print("Enter Attendance ID: ");
        int attendanceId = sc.nextInt();
        sc.nextLine();

        String sql = "DELETE FROM attendance WHERE attendance_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, attendanceId);

            int result = ps.executeUpdate();

            if (result > 0) {
                System.out.println("Attendance deleted successfully!");
            } else {
                System.out.println("Attendance ID not found.");
            }

        } catch (SQLException e) {
            System.out.println("Error deleting attendance: " + e.getMessage());
        }
    }
}
