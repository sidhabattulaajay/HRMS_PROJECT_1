package org.example;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;
public class Organization {
public static void createOrganization(Scanner sc){
    System.out.println("\n===== Create New Organization =====");

    System.out.println(" Enter Organization name:");
   String  organizationName = sc.nextLine();

   System.out.println("Enter address:");
   String address = sc.nextLine();
   System.out.println("Enter pan number:");
   String panNumber = sc.nextLine();
   System.out.println("Enter gst number:");
   String gstNumber = sc.nextLine();
   System.out.println("Enter Department :");
   String department= sc.nextLine();
   System.out.println(" Enter employee count:");
   int employeeCount=sc.nextInt();
   sc.nextLine();

    System.out.println("\nOrganization details received!");
    System.out.println("Organization Name: " + organizationName);
    System.out.println("Address: " + address);
    System.out.println("PAN Number: " + panNumber);
    System.out.println("GST Number: " + gstNumber);
    System.out.println("Department: " + department);
    System.out.println("Employee Count: " + employeeCount);


    System.out.println("\nOrganization details received!");

    String sql = "INSERT INTO organizations " +
            "(organization_name, address, pan_number, gst_number, department, employee_count) " +
            "VALUES (?, ?, ?, ?, ?, ?)";


    try {
        Connection connection = DBConnection.getConnection();
        PreparedStatement ps = connection.prepareStatement(sql,  java.sql.Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, organizationName);
        ps.setString(2, address);
        ps.setString(3, panNumber);
        ps.setString(4, gstNumber);
        ps.setString(5, department);
        ps.setInt(6, employeeCount);

        int result = ps.executeUpdate();
        if (result > 0) {
            System.out.println("Organization Created Successfully");

            ResultSet rs= ps.getGeneratedKeys();

            if(rs.next()){
                int organizationId=rs.getInt(1);
                System.out.println("Organization ID: " + organizationId);
                User.createUser(sc, organizationId);
            }
            rs.close();
        }
        ps.close();
        connection.close();
    } catch (SQLException e) {
        System.out.println("Error saving organization: " + e.getMessage());
    }
    }


}
