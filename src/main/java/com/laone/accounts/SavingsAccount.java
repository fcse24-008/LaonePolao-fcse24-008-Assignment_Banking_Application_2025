package com.laone.accounts;
import com.laone.system.DBConnector;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.laone.customers.JointCustomer;
import com.laone.customers.BusinessCustomer;
import com.laone.customers.IndividualCustomer;

public class SavingsAccount extends Account
{
    private JointCustomer jointCustomer;
    private BusinessCustomer businessCustomer;
    private IndividualCustomer individualCustomer;

    public JointCustomer getJointCustomer() {return jointCustomer;}
    public void setJointCustomer(JointCustomer jointCustomer) {this.jointCustomer = jointCustomer;}
    public BusinessCustomer getBusinessCustomer() {return businessCustomer;}
    public void setBusinessCustomer(BusinessCustomer businessCustomer) {this.businessCustomer = businessCustomer;}
    public IndividualCustomer getIndividualCustomer() {return individualCustomer;}
    public void setIndividualCustomer(IndividualCustomer individualCustomer) {this.individualCustomer = individualCustomer;}

    @Override
    public String toString()
    {
        return "SavingsAccount= " + "accountID: " + getAccountID() + '\'' + ", balance: " + getBalance() + ", branch: " + getBranch() + '\''  + "jointCustomer=" + jointCustomer.getCustomerID() + ", businessCustomer=" + businessCustomer.getCustomerID() + ", individualCustomer=" + individualCustomer.getCustomerID() + '}';
    }

    public void saveToDatabase()
    {
        Connection conn = DBConnector.connect();
        if (conn != null) {
            try {
                PreparedStatement stmt = conn.prepareStatement(
                        "INSERT INTO saving_account (accountID, balance, branch, JCustomerID, BCustomerID, ICustomerID) VALUES (?, ?, ? ,? ,? ,?)"
                );
                stmt.setInt(1, getAccountID());
                stmt.setDouble(2, getBalance());
                stmt.setString(3, getBranch());
                stmt.setObject(4, jointCustomer != null ? jointCustomer.getCustomerID() : null);
                stmt.setObject(5, businessCustomer != null ? businessCustomer.getCustomerID() : null);
                stmt.setObject(6, individualCustomer != null ? individualCustomer.getCustomerID() : null);

                stmt.executeUpdate();
                stmt.close();
                conn.close();
            } catch (SQLException e) {
                System.out.println("Account DB error: " + e.getMessage());
            }
        }
    }

    public boolean deposit(double amount) {
        if (amount <= 0) {
            System.out.println("❌ Invalid deposit amount.");
            return false;
        }

        this.setBalance(this.getBalance() + amount);

        try (Connection conn = DBConnector.connect();
             PreparedStatement stmt = conn.prepareStatement(
                     "UPDATE saving_account SET balance = ? WHERE accountID = ?")) {
            stmt.setDouble(1, this.getBalance());
            stmt.setInt(2, this.getAccountID());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Deposit DB error: " + e.getMessage());
            return false;
        }
    }

    public boolean withdraw(double amount, String pin) {
        if (amount <= 0) {
            System.out.println("❌ Invalid withdrawal amount.");
            return false;
        }
        if (amount > this.getBalance()) {
            System.out.println("❌ Insufficient funds.");
            return false;
        }

        // Validate PIN against linked customer
        if (!validatePin(pin)) {
            System.out.println("❌ Incorrect PIN.");
            return false;
        }

        this.setBalance(this.getBalance() - amount);

        try (Connection conn = DBConnector.connect();
             PreparedStatement stmt = conn.prepareStatement(
                     "UPDATE saving_account SET balance = ? WHERE accountID = ?")) {
            stmt.setDouble(1, this.getBalance());
            stmt.setInt(2, this.getAccountID());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Withdraw DB error: " + e.getMessage());
            return false;
        }
    }

    private boolean validatePin(String pin) {
        String table = null;
        int custID = -1;

        if (individualCustomer != null) {
            table = "individual_customer";
            custID = individualCustomer.getCustomerID();
        } else if (businessCustomer != null) {
            table = "business_customer";
            custID = businessCustomer.getCustomerID();
        } else if (jointCustomer != null) {
            table = "joint_customer";
            custID = jointCustomer.getCustomerID();
        }

        if (table == null || custID == -1) {
            return false; // no linked customer
        }

        try (Connection conn = DBConnector.connect();
             PreparedStatement stmt = conn.prepareStatement(
                     "SELECT pin FROM " + table + " WHERE CustomerID = ?")) {
            stmt.setInt(1, custID);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("pin").equals(pin);
            }
        } catch (SQLException e) {
            System.out.println("PIN validation error: " + e.getMessage());
        }
        return false;
    }

    public void creditSavingsInterest() {
        double interestRate = 0.0005; // 0.05%
        double interest = this.getBalance() * interestRate;
        this.setBalance(this.getBalance() + interest);

        try (Connection conn = DBConnector.connect();
             PreparedStatement stmt = conn.prepareStatement(
                     "UPDATE saving_account SET balance = ? WHERE accountID = ?")) {
            stmt.setDouble(1, this.getBalance());
            stmt.setInt(2, this.getAccountID());
            stmt.executeUpdate();
            System.out.println("✅ Savings interest credited: " + interest);
        } catch (SQLException e) {
            System.out.println("❌ Error crediting savings interest: " + e.getMessage());
        }
    }

}
