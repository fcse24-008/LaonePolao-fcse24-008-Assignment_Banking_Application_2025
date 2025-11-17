package com.laone.teller;
import com.laone.accounts.InvestmentAccount;
import com.laone.accounts.SavingsAccount;
import com.laone.accounts.Cheque;
import com.laone.customers.BusinessCustomer;
import com.laone.customers.IndividualCustomer;
import com.laone.customers.JointCustomer;
import com.laone.system.DBConnector;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BankTeller {
    private String tellerID;
    private String firstname;
    private String lastname;
    private String branch;
    private String pin;

    public String getTellerID() {return tellerID;}
    public void setTellerID(String tellerID) {this.tellerID = tellerID;}
    public String getFirstname() {return firstname;}
    public void setFirstname(String firstname) {this.firstname = firstname;}
    public String getLastname() {return lastname;}
    public void setLastname(String lastname) {this.lastname = lastname;}
    public String getBranch() {return branch;}
    public void setBranch(String branch) {this.branch = branch;}
    public String getPin() {return pin;}
    public void setPin(String pin) {this.pin = pin;}

    public void saveToDatabase() {
        Connection conn = DBConnector.connect();
        if (conn != null) {
            try {
                PreparedStatement stmt = conn.prepareStatement(
                        "INSERT INTO bank_teller (tellerID, FirstName, LastName, BRANCH, pin) VALUES (?, ?, ?, ?, ?)"
                );
                stmt.setString(1, this.tellerID);
                stmt.setString(2, this.firstname);
                stmt.setString(3, this.lastname);
                stmt.setString(4, this.branch);
                stmt.setString(5, this.pin);
                stmt.executeUpdate();
                stmt.close();
                conn.close();
            } catch (SQLException e) {
                System.out.println("Teller DB error: " + e.getMessage());
            }
        }
    }

    public void addIndividualCustomer(IndividualCustomer individualcustomer) {
        individualcustomer.saveToDatabase();
    }
    public void addBusinessCustomer(BusinessCustomer businesscustomer) {
        businesscustomer.saveToDatabase();
    }
    public void addJointCustomer(JointCustomer jointcustomer) {
        jointcustomer.saveToDatabase();
    }

    public void addInvestmentAccount(InvestmentAccount investmentaccount) {
        investmentaccount.saveToDatabase();
    }
    public void addSavingsAccount(SavingsAccount savingsaccount) {
        savingsaccount.saveToDatabase();
    }
    public void addCheque(Cheque cheque) {
        cheque.saveToDatabase();
    }

    @Override
    public String toString() {
                               return "BankTeller= " + "tellerID: " + tellerID + '\'' + ", firstname: " + firstname + '\'' + ", lastname: " + lastname + '\'' + ", branch: " + branch + '\'' + '}';
                             }

    public static BankTeller login(String enteredID, String enteredPin) {
        Connection conn = DBConnector.connect();
        if (conn != null) {
            try {
                PreparedStatement stmt = conn.prepareStatement(
                        "SELECT * FROM bank_teller WHERE tellerID = ?"
                );
                stmt.setString(1, enteredID);
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    String storedPin = rs.getString("pin");


                    if (enteredPin.equals(storedPin)) {
                        BankTeller teller = new BankTeller();
                        teller.setTellerID(rs.getString("tellerID"));
                        teller.setFirstname(rs.getString("FirstName"));
                        teller.setLastname(rs.getString("LastName"));
                        teller.setBranch(rs.getString("branch"));

                        rs.close();
                        stmt.close();
                        conn.close();
                        return teller;
                    }
                }
                rs.close();
                stmt.close();
                conn.close();
            } catch (SQLException e) {
                System.out.println("Login error: " + e.getMessage());
            }
        }

        return null; // Login FAILED TRY AGAIN
    }
    public static boolean tellerIDExists(String tellerID) {
        Connection conn = DBConnector.connect();
        if (conn != null) {
            try {
                PreparedStatement stmt = conn.prepareStatement(
                        "SELECT tellerID FROM bank_teller WHERE tellerID = ?"
                );
                stmt.setString(1, tellerID);
                ResultSet rs = stmt.executeQuery();
                boolean exists = rs.next(); // true if found
                rs.close();
                stmt.close();
                conn.close();
                return exists;
            } catch (SQLException e) {
                System.out.println("ID check error: " + e.getMessage());
            }
        }
        return false;
    }

}


