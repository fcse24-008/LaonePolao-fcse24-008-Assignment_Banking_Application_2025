public class Account {
    private String accountID;
    private double balance;
    private String branch;

    public String getAccountID() {return accountID;}
    public double getBalance() {return balance;}
    public String getBranch() {return branch;}


    public void setBranch(String branch) {this.branch = branch;}
    public void setBalance(double balance) {this.balance = balance;}
    public void setAccountID(String accountID) {this.accountID = accountID;}

    public void withdrawAmount(double amount) {

    }

    public void depositAmount(double amount) {

    }

    public void transferAmount(Account toAccount, double amount) {

    }

    public void displayTransaction() {

    }
}
