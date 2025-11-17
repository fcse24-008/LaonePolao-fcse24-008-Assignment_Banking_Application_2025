package com.laone.customers;
import com.laone.system.DBConnector;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Customer
{
    private int customerID;
    private String firstname;
    private String lastname;
    private String contact;
    private int nationalID;
    private String gender;


        public int getCustomerID() { return customerID; }
        public void setCustomerID(int customerID) { this.customerID = customerID; }

        public String getFirstname() {return firstname;}
        public void setFirstname(String firstname) {this.firstname = firstname;}

        public String getLastname() {return lastname;}
        public void setLastname(String lastname) {this.lastname = lastname;}

        public String getContact() {return contact;}
        public void setContact(String contact) {this.contact = contact;}

        public int getNationalID() {return nationalID;}
        public void setNationalID(int nationalID) {this.nationalID = nationalID;}

        public String getGender() {return gender;}
        public void setGender(String gender) {this.gender = gender;}

    

    @Override
    public String toString() {return "Customer = " + "customerID: " + customerID + '\'' + ", firstname: " + firstname + '\'' + ", lastname: " + lastname + '\'' + ", contact: " + contact + '\'' + ", nationalID: " + nationalID + '\'' + ", gender: " + gender + '\'' + '}';
    }

    private boolean customerIDExists(int id, String type) {
        String table = switch (type) {
            case "Individual" -> "individual_customer";
            case "Business" -> "business_customer";
            case "Joint" -> "joint_customer";
            default -> null;
        };
        if (table == null) return false;

        try (Connection conn = DBConnector.connect();
             PreparedStatement stmt = conn.prepareStatement("SELECT customerID FROM " + table + " WHERE customerID = ?")) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            return false;
        }
    }

    public static Customer login(String id, String pin, String type) {
        String table = switch (type) {
            case "Individual" -> "individual_customer";
            case "Business" -> "business_customer";
            case "Joint" -> "joint_customer";
            default -> null;
        };
        if (table == null) return null;

        try (Connection conn = DBConnector.connect();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM " + table + " WHERE CustomerID = ? AND PIN = ?")) {
            stmt.setInt(1, Integer.parseInt(id));
            stmt.setString(2, pin);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Customer c = new Customer();
                c.setCustomerID(rs.getInt("CustomerID"));
                c.setFirstname(rs.getString("firstname"));
                c.setLastname(rs.getString("lastname"));
                // Add other fields if needed
                return c;
            }
        } catch (Exception e) {
            System.out.println("Login error: " + e.getMessage());
        }
        return null;
    }


}
