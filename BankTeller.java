public class BankTeller {
    private String tellerID;
    private String firstname;
    private String lastname;
    private String branch;

    public String getTellerID() {return tellerID;}
    public void setTellerID(String tellerID) {this.tellerID = tellerID;}

    public String getFirstname() {return firstname;}
    public void setFirstname(String firstname) {this.firstname = firstname;}


    public String getLastname() {return lastname;}
    public void setLastname(String lastname) {this.lastname = lastname;}

    public String getBranch() {return branch;}
    public void setBranch(String branch) {this.branch = branch;}

    public void openAccount(Customer customer)
    {

    }

    public void closeAccount(String accountID)
    {

    }

    public void modifyAccount(String accountID, Account updatedAccount)
    {

    }
}
