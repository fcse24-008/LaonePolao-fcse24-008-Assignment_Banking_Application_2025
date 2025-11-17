package com.laone.accounts;

public class Account
{
    private int accountID;
    private double balance;
    private String branch;

    public int getAccountID() {return accountID;}
    public double getBalance() {return balance;}
    public String getBranch() {return branch;}


    public void setBranch(String branch) {this.branch = branch;}
    public void setBalance(double balance) {this.balance = balance;}
    public void setAccountID(int accountID) {this.accountID = accountID;}



    @Override
    public String toString() {
                                  return "Account= " + "accountID: " + accountID + '\'' + ", balance: " + balance + ", branch: " + branch + '\'' + '}';
                             }

}


