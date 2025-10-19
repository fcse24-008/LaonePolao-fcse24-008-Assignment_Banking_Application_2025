public class Customer
{
    private String customerID;
    private String title;
    private String firstname;
    private String lastname;
    private String contact;
    private String nationalID;
    private String gender;
    private String dateOfBirth;


        public String getCustomerID() { return customerID; }
        public void setCustomerID(String customerID) { this.customerID = customerID; }

        public String getTitle() {return title;}
        public void setTitle(String title) {this.title = title;}

        public String getFirstname() {return firstname;}
        public void setFirstname(String firstname) {this.firstname = firstname;}

        public String getLastname() {return lastname;}
        public void setLastname(String lastname) {this.lastname = lastname;}

        public String getContact() {return contact;}
        public void setContact(String contact) {this.contact = contact;}

        public String getNationalID() {return nationalID;}
        public void setNationalID(String nationalID) {this.nationalID = nationalID;}

        public String getGender() {return gender;}
        public String getDateOfBirth() {return dateOfBirth;}

        public void setDateOfBirth(String dateOfBirth) {this.dateOfBirth = dateOfBirth;}
        public void setGender(String gender) {this.gender = gender;}


    public void withdrawFunds(double amount)
    {
    }
    public void depositFunds(double amount)
    {
    }
}