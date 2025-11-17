package com.laone.customers;
import com.laone.system.DBConnector;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class JointCustomer extends Customer
{
    private String pin;

    public String getPin() {return pin;}
    public void setPin(String pin) {this.pin = pin;}

    @Override
    public String toString() {
                                 return "Joint Customer = " + "customerID: " + getCustomerID() + '\'' + ", firstname: " + getFirstname() + '\'' + ", lastname: " + getLastname() + '\'' + ", contact: " + getContact() + '\'' + ", nationalID: " + getNationalID() + '\'' + ", gender: " + getGender() + '}';
                              }

    public void saveToDatabase()
    {
        Connection conn = DBConnector.connect();
        if (conn != null) {
            try {
                PreparedStatement stmt = conn.prepareStatement(
                        "INSERT INTO joint_customer (customerID, firstname, lastname , gender, contact, nationalID, pin) VALUES (?, ?, ?, ?, ?, ?, ?)"
                );
                stmt.setInt(1, getCustomerID());
                stmt.setString(2, getFirstname());
                stmt.setString(3, getLastname());
                stmt.setString(4, getGender());
                stmt.setString(5, getContact());
                stmt.setInt(6, getNationalID());
                stmt.setString(7, this.pin);
                stmt.executeUpdate();
                conn.close();
            } catch (SQLException e) {
                System.out.println("Customer DB error: " + e.getMessage());
            }
        }
    }
}
