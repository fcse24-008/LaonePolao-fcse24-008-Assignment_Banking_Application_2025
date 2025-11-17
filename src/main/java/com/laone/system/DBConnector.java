package com.laone.system;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnector {
    private static final String URL = "jdbc:mysql://localhost:3306/assignment_banking_app";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public static Connection connect() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            System.out.println("Connection failed: " + e.getMessage());
            return null;
        }
    }

    public static void testConnection() {
        Connection conn = connect();
        if (conn != null) {
            System.out.println("✅ connected!");
            try {
                conn.close();
            } catch (SQLException e) {
                System.out.println("Error closing connection: " + e.getMessage());
            }
        } else {
            System.out.println("❌ DBConnector is not connected!");
        }
    }


}
