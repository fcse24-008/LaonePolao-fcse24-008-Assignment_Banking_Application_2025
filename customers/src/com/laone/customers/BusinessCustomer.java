package com.laone.customers;
import com.laone.database.DBConnector;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BusinessCustomer extends Customer
{
    private String businessName;
    private String businessAddress;
    private String pin;

    public String getPin() {return pin;}
    public void setPin(String pin) {this.pin = pin;}
    public String getBusinessName() {return businessName;}
    public void setBusinessName(String businessName) {this.businessName = businessName;}
    public String getBusinessAddress() {return businessAddress;}
    public void setBusinessAddress(String businessAddress) {this.businessAddress = businessAddress;}

    @Override
    public String toString() {
                                 return "BusinessCustomer = " + "businessName: " + businessName + '\'' + ", businessAddress: " + businessAddress + '\'' + "customerID: " + getCustomerID() + '\'' + ", firstname: " + getFirstname() + '\'' + ", lastname: " + getLastname() + '\'' + ", contact: " + getContact() + '\'' + ", nationalID: " + getNationalID() + '\'' + ", gender: " + getGender() + '\'' + '}';
                              }

    public void saveToDatabase()
    {
        Connection conn = DBConnector.connect();
        if (conn != null) {
            try {
                PreparedStatement stmt = conn.prepareStatement(
                        "INSERT INTO business_customer (customerID, firstname, lastname , gender, contact, nationalID, business_name, business_address, pin ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)"
                );
                stmt.setInt(1, getCustomerID());
                stmt.setString(2, getFirstname());
                stmt.setString(3, getLastname());
                stmt.setString(4, getGender());
                stmt.setString(5, getContact());
                stmt.setInt(6, getNationalID());
                stmt.setString(7, businessName);
                stmt.setString(8, businessAddress);
                stmt.setString(9, this.pin);
                stmt.executeUpdate();
                conn.close();
            } catch (SQLException e) {
                System.out.println("Customer DB error: " + e.getMessage());
            }
        }
    }
}
