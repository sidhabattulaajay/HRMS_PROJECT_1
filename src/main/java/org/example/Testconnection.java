package org.example;

import java.sql.Connection;
import java.sql.SQLException;

public class Testconnection {

 static void main() {

        try (Connection connection = DBConnection.getConnection()) {

            System.out.println("MySQL connected successfully!");

        } catch (SQLException e) {

            System.out.println("Database connection failed!");
            e.printStackTrace();
        }
    }
}
