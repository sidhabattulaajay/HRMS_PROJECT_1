package org.example;

import com.sun.jdi.InconsistentDebugInfoException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;


public class User {

    public static void createUser(Scanner sc, int organizationId){
         System.out.println("Enter User Full Name:");
         String fullName = sc.nextLine();
         System.out.println("Enter Login Email:");
         String email = sc.nextLine();
         System.out.println("Enter Password");
         String password = sc.nextLine();

        String sql = "INSERT INTO users"+"(organization_id , full_name, email, password)"+"VALUES(?,?,?,?)";

        try{
            Connection connection = DBConnection.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);


            ps.setInt(1,organizationId);
            ps.setString(2,fullName);
            ps.setString(3,email);
            ps.setString(4,password);

            int result = ps.executeUpdate();
            if (result>0){
                System.out.println(" User account created Successfully" );
            }
            ps.close();
            connection.close();
            }catch(SQLException e){
            System.out.println("error creating user account"+ e.getMessage());




        }

    }

}
