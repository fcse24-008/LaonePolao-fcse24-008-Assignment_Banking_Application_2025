/* Label labelname = new Label("textprompt");
  Button buttonname = new Button("textprompt");
hbox/vbow bow name= new vbox (attributes like buttons and other hbows or vboxes)

pos includes center top and bottom
can be combined with left ringht and centre

*/

package com.laone.system;
import com.laone.accounts.Cheque;
import com.laone.accounts.InvestmentAccount;
import com.laone.accounts.SavingsAccount;
import com.laone.customers.BusinessCustomer;
import com.laone.customers.IndividualCustomer;
import com.laone.customers.JointCustomer;
import com.laone.database.DBConnector;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import static com.laone.database.DBConnector.testConnection;
import static javafx.application.Application.launch;
import com.laone.teller.BankTeller;
import com.laone.customers.Customer;
import com.laone.accounts.Account;
import javafx.util.Pair;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BankingSystem extends Application
{

    private Stage primaryStage;
    private Scene welcomeScene;


    @Override
        public void start(Stage welcomeStage) {
             this.primaryStage = welcomeStage;
            Label welcomelabelA = new Label("WELCOME TO THE ASSIGNMENT BANKING APP");
            Label welcomelabelB = new Label("To proceed please select one below");

            Button lgnBankTeller = new Button("Continue as teller");
            lgnBankTeller.setPadding(new Insets(40));
            lgnBankTeller.setOnAction(e -> tellerLoginPrompt());

            Button lgnCustomer = new Button("login into your customer profile");
            lgnCustomer.setPadding(new Insets(40));

            lgnCustomer.setOnAction(e ->showCustomerLoginForm());

            HBox welcomeStageBttnLO = new HBox(25, lgnCustomer, lgnBankTeller);
            welcomeStageBttnLO.setAlignment(Pos.CENTER);
            VBox welcomestageMainLO =new VBox(30 , welcomelabelA ,welcomelabelB , welcomeStageBttnLO);
            welcomestageMainLO.setAlignment(Pos.TOP_CENTER);
            welcomestageMainLO.setPadding(new Insets(40));

            StackPane root = new StackPane(welcomestageMainLO);
            welcomeScene = new Scene(root, 800, 600);
            primaryStage = welcomeStage;
            primaryStage.setScene(welcomeScene);
            primaryStage.setTitle("Assignment Banking System");
            primaryStage.show();

        }

           private void tellerLoginPrompt() {

                                         Stage loginStage = new Stage();
                                         loginStage.setTitle("Login as teller");

                                         Label tellerIDLab = new Label("Your Teller ID:");
                                         TextField tellerIDfield = new TextField();

                                         Label tellerPinLab = new Label("PIN:");
                                         PasswordField tellerPinField = new PasswordField();
        Button backBtn = new Button("Back");
        backBtn.setOnAction(e -> primaryStage.setScene(welcomeScene));

        Label statusLabel = new Label();
                                        Button loginBtn = new Button("Login");



                                                 loginBtn.setOnAction(e -> {

                                                                                      String enteredID = tellerIDfield.getText().trim();
                                                                                      String enteredPin = tellerPinField.getText().trim();

                                                                                       BankTeller bankTeller = BankTeller.login(enteredID, enteredPin);
                                                                                       if (bankTeller != null) {
                                                                                       statusLabel.setText("✅ Welcome " + bankTeller.getFirstname() + "!");
                                                                                       loginStage.close();
                                                                                       launchTellerDashboard(bankTeller);
                                                                                       } else {
                                                                                       statusLabel.setText("❌ Invalid ID or PIN. Try again.");
                                                                                      }

                                               });

        VBox layout = new VBox(15, tellerIDLab, tellerIDfield, tellerPinLab, tellerPinField, loginBtn,backBtn, statusLabel);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));

        Scene tellerLoginScene = new Scene(layout, 800, 600);
        primaryStage.setScene(tellerLoginScene);
    }

           private void launchTellerDashboard(BankTeller bankTeller) {
        Stage tellerDashboardStage = new Stage();
        tellerDashboardStage.setTitle("Teller Dashboard");

        Label tellerWelcomeMsg = new Label("Welcome, " + bankTeller.getFirstname() + " " + bankTeller.getLastname());
        Label tellerBranch = new Label("Branch: " + bankTeller.getBranch());

        Button addNewTeller = new Button("ADD NEW TELLERS");
        addNewTeller.setPadding(new Insets(20));
        addNewTeller.setOnAction(e -> showAddBTellerForm());

        Button backBtn = new Button("LOG OUT");
        backBtn.setOnAction(e -> primaryStage.setScene(welcomeScene));
        backBtn.setAlignment(Pos.CENTER);

        Button addNewCustomers = new Button("ADD NEW CUSTOMERS");
        addNewCustomers.setPadding(new Insets(20));
        addNewCustomers.setOnAction(e -> showAddCustomerForm());

        Button assignAccountsBtn = new Button("ASSIGN ACCOUNTS");
        assignAccountsBtn.setPadding(new Insets(20));
        assignAccountsBtn.setOnAction(e -> showAssignAccountForm());

        Button Search = new Button("SEARCH CUSTOMER DATABASE");
        Search.setPadding(new Insets(20));
        Search.setOnAction(e -> showCustomerSearchForm());


        HBox telllerbttnLO = new HBox(15, addNewTeller , addNewCustomers,assignAccountsBtn ,Search);
        telllerbttnLO.setAlignment(Pos.CENTER);
        VBox layout = new VBox(20, tellerWelcomeMsg, tellerBranch, telllerbttnLO,backBtn);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(30));

        Scene tellerdashboardScene = new Scene(layout, 800, 600);
        primaryStage.setScene(tellerdashboardScene);

    }

               private void showAddBTellerForm() {
        Stage formStage = new Stage();
        formStage.setTitle("Add New Teller");

        TextField idField = new TextField();
        idField.setPromptText("Teller ID");
        TextField fnameField = new TextField();
        fnameField.setPromptText("First Name");
        TextField lnameField = new TextField();
        lnameField.setPromptText("Last Name");
        TextField branchField = new TextField();
        branchField.setPromptText("Branch");
        PasswordField pinField = new PasswordField();
        pinField.setPromptText("PIN");
        Button saveBtn = new Button("Save Teller");
        Label statusLabel = new Label();

        saveBtn.setOnAction(e -> {
            String id = idField.getText().trim();
            String fname = fnameField.getText().trim();
            String lname = lnameField.getText().trim();
            String branch = branchField.getText().trim();
            String pin = pinField.getText().trim();

            // Check for empty fields
            if (id.isEmpty() || fname.isEmpty() || lname.isEmpty() || branch.isEmpty() || pin.isEmpty()) {
                statusLabel.setText("! All fields must be filled.");
                return;
            }

            // Validate Teller ID: exactly 4 digits
            if (!id.matches("\\d{4}")) {
                statusLabel.setText("! Teller ID must be exactly 4 digits.");
                return;
            }

            // Validate PIN: 4 or 5 digits only
            if (!pin.matches("\\d{4,5}")) {
                statusLabel.setText("! PIN must be 4 or 5 digits.");
                return;
            }
            if (BankTeller.tellerIDExists(id)) {
                statusLabel.setText("❌ Teller ID already exists. Choose a different one.");
                return;
            }


            BankTeller teller = new BankTeller();
            teller.setTellerID(id);
            teller.setFirstname(fname);
            teller.setLastname(lname);
            teller.setBranch(branch);
            teller.setPin(pin);

            try {
                teller.saveToDatabase();
                statusLabel.setText("✅ Teller saved successfully!");
            } catch (Exception ex) {
                statusLabel.setText("! Error saving teller: " + ex.getMessage());
            }
        });


        VBox layout = new VBox(10, idField, fnameField, lnameField, branchField, pinField, saveBtn, statusLabel);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));

        formStage.setScene(new Scene(layout, 350, 400));
        formStage.show();
    }

               private String searchCustomer (String type, String id) {
        String table = switch (type) {
            case "Individual" -> "individual_customer";
            case "Business" -> "business_customer";
            case "Joint" -> "joint_customer";
            default -> null;
        };
        if (table == null) return "❌ Invalid customer type.";

        try (Connection conn = DBConnector.connect();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM " + table + " WHERE customerID = ?")) {
            stmt.setInt(1, Integer.parseInt(id));
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return "✅ Found: " + rs.getString("firstname") + " " + rs.getString("lastname");
            } else {
                return "❌ No customer found.";
            }
        } catch (Exception e) {
            return "⚠️ Error: " + e.getMessage();
        }
    }

               private void showCustomerSearchForm() {
        Stage searchStage = new Stage();
        searchStage.setTitle("Search Customer");

        ComboBox<String> typeBox = new ComboBox<>();
        typeBox.getItems().addAll("Individual", "Business", "Joint");
        typeBox.setPromptText("Select Customer Type");

        TextField idField = new TextField();
        idField.setPromptText("Enter Customer ID");

        Button searchBtn = new Button("Search");
        Label resultLabel = new Label();

        searchBtn.setOnAction(e -> {
            String type = typeBox.getValue();
            String id = idField.getText().trim();
            String result = searchCustomer(type, id);
            resultLabel.setText(result);
        });

        VBox layout = new VBox(10, typeBox, idField, searchBtn, resultLabel);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));

        searchStage.setScene(new Scene(layout, 350, 300));
        searchStage.show();
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

               private void showAddCustomerForm() {
        Stage formStage = new Stage();
        formStage.setTitle("Add New Customer");

        // Labels and Fields
        Label fnameLabel = new Label("First Name:");
        TextField fnameField = new TextField();

        Label lnameLabel = new Label("Last Name:");
        TextField lnameField = new TextField();

        Label contactLabel = new Label("Contact:");
        TextField contactField = new TextField();

        Label nationalIDLabel = new Label("National ID:");
        TextField nationalIDField = new TextField();

        Label genderLabel = new Label("Gender (M/F):");
        ComboBox<String> genderBox = new ComboBox<>();
        genderBox.getItems().addAll("M", "F");

        Label typeLabel = new Label("Customer Type:");
        ComboBox<String> typeBox = new ComboBox<>();
        typeBox.getItems().addAll("Individual", "Business", "Joint");

        Label pinLabel = new Label("PIN:");
        PasswordField pinField = new PasswordField();

        Button saveBtn = new Button("Save");
        Label statusLabel = new Label();

        Label bsNameLabel = new Label("Business Name:");
        TextField bsNameField = new TextField();

        Label bsAddressLabel = new Label("Business Address:");
        TextField bsAddressField = new TextField();



        saveBtn.setOnAction(e -> {
            String fname = fnameField.getText().trim();
            String lname = lnameField.getText().trim();
            String contact = contactField.getText().trim();
            String nationalID = nationalIDField.getText().trim();
            String gender = genderBox.getValue();
            String type = typeBox.getValue();
            String pin = pinField.getText().trim();

            if (fname.isEmpty() || lname.isEmpty() || contact.isEmpty() || nationalID.isEmpty() || gender == null || type == null || pin.isEmpty()) {
                statusLabel.setText("⚠️ Fill all fields.");
                return;
            }

            if (!nationalID.matches("\\d+")) {
                statusLabel.setText("❌ National ID must be digits only.");
                return;
            }

            int custID = Integer.parseInt(nationalID);
            if (customerIDExists(custID, type)) {
                statusLabel.setText("❌ Customer ID already exists.");
                return;
            }

            if (type.equals("Individual")) {
                IndividualCustomer c = new IndividualCustomer();
                c.setCustomerID(custID);
                c.setFirstname(fname);
                c.setLastname(lname);
                c.setContact(contact);
                c.setGender(gender);
                c.setPin(pin);
                c.saveToDatabase();
            } else if (type.equals("Business")) {
                BusinessCustomer c = new BusinessCustomer();
                c.setCustomerID(custID);
                c.setFirstname(fname);
                c.setLastname(lname);
                c.setContact(contact);
                c.setGender(gender);
                c.setPin(pin);
                c.setBusinessName(bsNameField.getText().trim());
                c.setBusinessAddress(bsAddressField.getText().trim());
                c.saveToDatabase();
            } else if (type.equals("Joint")) {
                JointCustomer c = new JointCustomer();
                c.setCustomerID(custID);
                c.setFirstname(fname);
                c.setLastname(lname);
                c.setContact(contact);
                c.setGender(gender);
                c.setPin(pin);
                c.saveToDatabase();
            }

            statusLabel.setText("✅ Customer saved.");
        });

        VBox layout = new VBox(10,
                fnameLabel, fnameField,
                lnameLabel, lnameField,
                contactLabel, contactField,
                nationalIDLabel, nationalIDField,
                bsNameLabel ,bsNameField,
                bsAddressLabel, bsAddressField,
                genderLabel, genderBox,
                typeLabel, typeBox,
                pinLabel, pinField,
                saveBtn, statusLabel

        );

        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(30));
        formStage.setScene(new Scene(layout, 600, 800));
        formStage.show();
    }

               private boolean accountIDExists(int id) {
        try (Connection conn = DBConnector.connect();
             PreparedStatement stmt = conn.prepareStatement("SELECT accountID FROM saving_account WHERE accountID = ?")) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            return false;
        }
    }

               private void showAssignAccountForm() {
        Stage formStage = new Stage();
        formStage.setTitle("Assign Account to Customer");

        // Labels and Fields
        Label customerIDLabel = new Label("Customer ID:");
        TextField customerIDField = new TextField();

        Label customerTypeLabel = new Label("Customer Type:");
        ComboBox<String> customerTypeBox = new ComboBox<>();
        customerTypeBox.getItems().addAll("Individual", "Business", "Joint");

        Label accountIDLabel = new Label("Account ID:");
        TextField accountIDField = new TextField();

        Label balanceLabel = new Label("Initial Balance:");
        TextField balanceField = new TextField();

        Label branchLabel = new Label("Branch:");
        TextField branchField = new TextField();

        Label accountTypeLabel = new Label("Account Type:");
        ComboBox<String> accountTypeBox = new ComboBox<>();
        accountTypeBox.getItems().addAll("Savings", "Cheque", "Investment");

        Button assignBtn = new Button("Assign Account");
        Label statusLabel = new Label();

        assignBtn.setOnAction(e -> {
            String custIDStr = customerIDField.getText().trim();
            String custType = customerTypeBox.getValue();
            String accIDStr = accountIDField.getText().trim();
            String balanceStr = balanceField.getText().trim();
            String branch = branchField.getText().trim();
            String accType = accountTypeBox.getValue();

            // Validation
            if (custIDStr.isEmpty() || custType == null || accIDStr.isEmpty() || balanceStr.isEmpty() || branch.isEmpty() || accType == null) {
                statusLabel.setText("⚠️ Fill all fields.");
                return;
            }

            if (!custIDStr.matches("\\d+") || !accIDStr.matches("\\d+")) {
                statusLabel.setText("❌ IDs must be digits.");
                return;
            }

            int customerID = Integer.parseInt(custIDStr);
            int accountID = Integer.parseInt(accIDStr);

            if (!customerIDExists(customerID, custType)) {
                statusLabel.setText("❌ Customer ID not found in " + custType + " records.");
                return;
            }

            if (accountIDExists(accountID)) {
                statusLabel.setText("❌ Account ID already exists.");
                return;
            }

            double balance;
            try {
                balance = Double.parseDouble(balanceStr);
            } catch (NumberFormatException ex) {
                statusLabel.setText("❌ Invalid balance.");
                return;
            }

            // Create and assign account
            if (accType.equals("Savings")) {
                SavingsAccount sa = new SavingsAccount();
                sa.setAccountID(accountID);
                sa.setBalance(balance);
                sa.setBranch(branch);

                if (custType.equals("Individual")) {
                    IndividualCustomer i = new IndividualCustomer();
                    i.setCustomerID(customerID);
                    sa.setIndividualCustomer(i);
                } else if (custType.equals("Business")) {
                    BusinessCustomer b = new BusinessCustomer();
                    b.setCustomerID(customerID);
                    sa.setBusinessCustomer(b);
                } else if (custType.equals("Joint")) {
                    JointCustomer j = new JointCustomer();
                    j.setCustomerID(customerID);
                    sa.setJointCustomer(j);
                }

                sa.saveToDatabase();
            }

            else if (accType.equals("Cheque")) {
                Cheque chq = new Cheque();
                chq.setAccountID(accountID);
                chq.setBalance(balance);
                chq.setBranch(branch);

                if (custType.equals("Individual")) {
                    IndividualCustomer i = new IndividualCustomer();
                    i.setCustomerID(customerID);
                    chq.setIndividualCustomer(i);
                } else if (custType.equals("Business")) {
                    BusinessCustomer b = new BusinessCustomer();
                    b.setCustomerID(customerID);
                    chq.setBusinessCustomer(b);
                } else if (custType.equals("Joint")) {
                    JointCustomer j = new JointCustomer();
                    j.setCustomerID(customerID);
                    chq.setJointCustomer(j);
                }

                chq.saveToDatabase();
            }

            else if (accType.equals("Investment")) {
                InvestmentAccount inv = new InvestmentAccount();
                inv.setAccountID(accountID);
                inv.setBalance(balance);
                inv.setBranch(branch);

                if (custType.equals("Individual")) {
                    IndividualCustomer i = new IndividualCustomer();
                    i.setCustomerID(customerID);
                    inv.setIndividualCustomer(i);
                } else if (custType.equals("Business")) {
                    BusinessCustomer b = new BusinessCustomer();
                    b.setCustomerID(customerID);
                    inv.setBusinessCustomer(b);
                } else if (custType.equals("Joint")) {
                    JointCustomer j = new JointCustomer();
                    j.setCustomerID(customerID);
                    inv.setJointCustomer(j);
                }

                inv.saveToDatabase();
            }

            statusLabel.setText("✅ Account assigned successfully.");
        });

        VBox layout = new VBox(10,
                customerIDLabel, customerIDField,
                customerTypeLabel, customerTypeBox,
                accountIDLabel, accountIDField,
                balanceLabel, balanceField,
                branchLabel, branchField,
                accountTypeLabel, accountTypeBox,
                assignBtn, statusLabel
        );

        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));
        formStage.setScene(new Scene(layout, 400, 650));
        formStage.show();
    }

    private void showCustomerLoginForm() {
        Stage loginStage = new Stage();
        loginStage.setTitle("Customer Login");

        Label idLabel = new Label("Your Profile ID:");
        TextField idField = new TextField();

        Label pinLabel = new Label("PIN:");
        PasswordField pinField = new PasswordField();

        Label typeLabel = new Label("Customer Type:");
        ComboBox<String> typeBox = new ComboBox<>();
        typeBox.getItems().addAll("Individual", "Business", "Joint");

        Button loginBtn = new Button("Login");
        Label statusLabel = new Label();

        loginBtn.setOnAction(e -> {
            String id = idField.getText().trim();
            String pin = pinField.getText().trim();
            String type = typeBox.getValue();

            if (id.isEmpty() || pin.isEmpty() || type == null) {
                statusLabel.setText("⚠️ Fill all fields.");
                return;
            }

            Customer customer = Customer.login(id, pin, type);
            if (customer != null) {
                statusLabel.setText("✅ Welcome " + customer.getFirstname() + "!");
                loginStage.close();
                launchCustomerDashboard(customer);
            } else {
                statusLabel.setText("❌ Invalid credentials.");
            }
        });

        VBox layout = new VBox(10, idLabel, idField, pinLabel, pinField, typeLabel, typeBox, loginBtn, statusLabel);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));

        loginStage.setScene(new Scene(layout, 400, 400));
        loginStage.show();
    }


    private void launchCustomerDashboard(Customer customer) {
        Stage dashboardStage = new Stage();
        dashboardStage.setTitle("Customer Dashboard");

        Label welcomeLabel = new Label("Welcome, " + customer.getFirstname() + " " + customer.getLastname());
        Label idLabel = new Label("Customer ID: " + customer.getCustomerID());

        // Auto-credit monthly interest for Savings and Investment accounts when dashboard loads
        try (Connection conn = DBConnector.connect()) {
            // Savings accounts
            PreparedStatement stmt1 = conn.prepareStatement(
                    "SELECT accountID, balance, branch, JCustomerID, BCustomerID, ICustomerID " +
                            "FROM saving_account WHERE JCustomerID = ? OR BCustomerID = ? OR ICustomerID = ?"
            );
            stmt1.setInt(1, customer.getCustomerID());
            stmt1.setInt(2, customer.getCustomerID());
            stmt1.setInt(3, customer.getCustomerID());
            ResultSet rs1 = stmt1.executeQuery();

            while (rs1.next()) {
                SavingsAccount sa = new SavingsAccount();
                sa.setAccountID(rs1.getInt("accountID"));
                sa.setBalance(rs1.getDouble("balance"));
                sa.setBranch(rs1.getString("branch"));

                int jId = rs1.getInt("JCustomerID");
                int bId = rs1.getInt("BCustomerID");
                int iId = rs1.getInt("ICustomerID");

                if (iId != 0) { IndividualCustomer i = new IndividualCustomer(); i.setCustomerID(iId); sa.setIndividualCustomer(i); }
                if (bId != 0) { BusinessCustomer b = new BusinessCustomer(); b.setCustomerID(bId); sa.setBusinessCustomer(b); }
                if (jId != 0) { JointCustomer j = new JointCustomer(); j.setCustomerID(jId); sa.setJointCustomer(j); }

                sa.creditSavingsInterest();
            }

            // Investment accounts
            PreparedStatement stmt2 = conn.prepareStatement(
                    "SELECT accountID, balance, branch, JCustomerID, BCustomerID, ICustomerID " +
                            "FROM investment_account WHERE JCustomerID = ? OR BCustomerID = ? OR ICustomerID = ?"
            );
            stmt2.setInt(1, customer.getCustomerID());
            stmt2.setInt(2, customer.getCustomerID());
            stmt2.setInt(3, customer.getCustomerID());
            ResultSet rs2 = stmt2.executeQuery();

            while (rs2.next()) {
                InvestmentAccount inv = new InvestmentAccount();
                inv.setAccountID(rs2.getInt("accountID"));
                inv.setBalance(rs2.getDouble("balance"));
                inv.setBranch(rs2.getString("branch"));

                int jId = rs2.getInt("JCustomerID");
                int bId = rs2.getInt("BCustomerID");
                int iId = rs2.getInt("ICustomerID");

                if (iId != 0) { IndividualCustomer i = new IndividualCustomer(); i.setCustomerID(iId); inv.setIndividualCustomer(i); }
                if (bId != 0) { BusinessCustomer b = new BusinessCustomer(); b.setCustomerID(bId); inv.setBusinessCustomer(b); }
                if (jId != 0) { JointCustomer j = new JointCustomer(); j.setCustomerID(jId); inv.setJointCustomer(j); }

                inv.creditInvestmentInterest();
            }
        } catch (SQLException e) {
            System.out.println("⚠️ Error auto-crediting interest: " + e.getMessage());
        }

        VBox accountSections = new VBox(20);
        accountSections.setPadding(new Insets(20));
        accountSections.setAlignment(Pos.TOP_LEFT);

        accountSections.getChildren().addAll(
                getSavingsAccounts(customer.getCustomerID()),
                getChequeAccounts(customer.getCustomerID()),
                getInvestmentAccounts(customer.getCustomerID())
        );

        Button logoutBtn = new Button("Log Out");
        logoutBtn.setOnAction(e -> {
            dashboardStage.close();
            primaryStage.setScene(welcomeScene);
        });

        VBox layout = new VBox(20, welcomeLabel, idLabel, accountSections, logoutBtn);
        layout.setAlignment(Pos.TOP_CENTER);
        layout.setPadding(new Insets(30));

        ScrollPane scrollPane = new ScrollPane(layout);
        scrollPane.setFitToWidth(true);

        Scene CDashBoardScene = new Scene(layout, 800, 600);
        primaryStage.setScene(CDashBoardScene);
    }

    private VBox getSavingsAccounts(int customerID) {
        VBox sectionBox = new VBox(10);
        sectionBox.setPadding(new Insets(10));

        Label sectionLabel = new Label("Savings Accounts");
        sectionLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
        Label statusLabel = new Label();
        sectionBox.getChildren().addAll(sectionLabel, statusLabel);

        try (Connection conn = DBConnector.connect();
             PreparedStatement stmt = conn.prepareStatement(
                     "SELECT accountID, balance, branch, JCustomerID, BCustomerID, ICustomerID " +
                             "FROM saving_account WHERE JCustomerID = ? OR BCustomerID = ? OR ICustomerID = ?"
             )) {
            stmt.setInt(1, customerID);
            stmt.setInt(2, customerID);
            stmt.setInt(3, customerID);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                SavingsAccount sa = new SavingsAccount();
                sa.setAccountID(rs.getInt("accountID"));
                sa.setBalance(rs.getDouble("balance"));
                sa.setBranch(rs.getString("branch"));

                int jId = rs.getInt("JCustomerID");
                int bId = rs.getInt("BCustomerID");
                int iId = rs.getInt("ICustomerID");

                if (iId != 0) { IndividualCustomer i = new IndividualCustomer(); i.setCustomerID(iId); sa.setIndividualCustomer(i); }
                if (bId != 0) { BusinessCustomer b = new BusinessCustomer(); b.setCustomerID(bId); sa.setBusinessCustomer(b); }
                if (jId != 0) { JointCustomer j = new JointCustomer(); j.setCustomerID(jId); sa.setJointCustomer(j); }

                String formattedBalance = String.format("%.2f", sa.getBalance());
                Label accLabel = new Label("ID: " + sa.getAccountID() + " | Balance: " + formattedBalance + " | Branch: " + sa.getBranch());

                Button depositBtn = new Button("Deposit");
                depositBtn.setOnAction(e -> {
                    TextInputDialog dialog = new TextInputDialog();
                    dialog.setHeaderText("Enter deposit amount:");
                    dialog.showAndWait().ifPresent(input -> {
                        try {
                            double amount = Double.parseDouble(input);
                            if (amount <= 0) {
                                statusLabel.setText("❌ Invalid amount. Enter a positive number.");
                                return;
                            }
                            if (sa.deposit(amount)) {
                                String formattedBalance2 = String.format("%.2f", sa.getBalance());
                                accLabel.setText("ID: " + sa.getAccountID() + " | Balance: " + formattedBalance2 + " | Branch: " + sa.getBranch());
                                statusLabel.setText("✅ Deposit successful.");
                            } else {
                                statusLabel.setText("⚠️ Deposit failed. Try again.");
                            }
                        } catch (NumberFormatException ex) {
                            statusLabel.setText("❌ Invalid amount. Enter a number.");
                        }
                    });
                });

                Button withdrawBtn = new Button("Withdraw");
                withdrawBtn.setOnAction(e -> {
                    Dialog<Pair<String, String>> dialog = new Dialog<>();
                    dialog.setTitle("Withdraw Funds");

                    Label amountLabel = new Label("Amount:");
                    TextField amountField = new TextField();
                    Label pinLabel = new Label("PIN:");
                    PasswordField pinField = new PasswordField();

                    GridPane grid = new GridPane();
                    grid.add(amountLabel, 0, 0);
                    grid.add(amountField, 1, 0);
                    grid.add(pinLabel, 0, 1);
                    grid.add(pinField, 1, 1);
                    dialog.getDialogPane().setContent(grid);

                    ButtonType okBtn = new ButtonType("Withdraw", ButtonBar.ButtonData.OK_DONE);
                    dialog.getDialogPane().getButtonTypes().addAll(okBtn, ButtonType.CANCEL);

                    dialog.setResultConverter(btn -> {
                        if (btn == okBtn) return new Pair<>(amountField.getText(), pinField.getText());
                        return null;
                    });

                    dialog.showAndWait().ifPresent(pair -> {
                        try {
                            double amount = Double.parseDouble(pair.getKey());
                            String pin = pair.getValue();

                            if (amount <= 0) {
                                statusLabel.setText("❌ Invalid amount. Enter a positive number.");
                                return;
                            }

                            boolean ok = sa.withdraw(amount, pin);
                            if (ok) {
                                String formattedBalance2 = String.format("%.2f", sa.getBalance());
                                accLabel.setText("ID: " + sa.getAccountID() + " | Balance: " + formattedBalance2 + " | Branch: " + sa.getBranch());
                                statusLabel.setText("✅ Withdrawal successful.");
                            } else {
                                // Your withdraw() should print specific reason; we show generic error on GUI
                                statusLabel.setText("❌ Withdrawal failed (check PIN, funds).");
                            }
                        } catch (NumberFormatException ex) {
                            statusLabel.setText("❌ Invalid amount. Enter a number.");
                        }
                    });
                });

                HBox accRow = new HBox(10, accLabel, depositBtn, withdrawBtn);
                accRow.setAlignment(Pos.CENTER_LEFT);
                sectionBox.getChildren().add(accRow);
            }

        } catch (SQLException e) {
            sectionBox.getChildren().add(new Label("⚠️ Error loading savings accounts: " + e.getMessage()));
        }

        return sectionBox;
    }
    private VBox getChequeAccounts(int customerID) {
        VBox sectionBox = new VBox(10);
        sectionBox.setPadding(new Insets(10));

        Label sectionLabel = new Label("Cheque Accounts");
        sectionLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
        Label statusLabel = new Label();
        sectionBox.getChildren().addAll(sectionLabel, statusLabel);

        try (Connection conn = DBConnector.connect();
             PreparedStatement stmt = conn.prepareStatement(
                     "SELECT accountID, balance, branch, JCustomerID, BCustomerID, ICustomerID " +
                             "FROM cheque WHERE JCustomerID = ? OR BCustomerID = ? OR ICustomerID = ?"
             )) {
            stmt.setInt(1, customerID);
            stmt.setInt(2, customerID);
            stmt.setInt(3, customerID);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Cheque c = new Cheque();
                c.setAccountID(rs.getInt("accountID"));
                c.setBalance(rs.getDouble("balance"));
                c.setBranch(rs.getString("branch"));

                int jId = rs.getInt("JCustomerID");
                int bId = rs.getInt("BCustomerID");
                int iId = rs.getInt("ICustomerID");

                if (iId != 0) { IndividualCustomer i = new IndividualCustomer(); i.setCustomerID(iId); c.setIndividualCustomer(i); }
                if (bId != 0) { BusinessCustomer b = new BusinessCustomer(); b.setCustomerID(bId); c.setBusinessCustomer(b); }
                if (jId != 0) { JointCustomer j = new JointCustomer(); j.setCustomerID(jId); c.setJointCustomer(j); }

                Label accLabel = new Label("ID: " + c.getAccountID() + " | Balance: " + c.getBalance() + " | Branch: " + c.getBranch());

                Button depositBtn = new Button("Deposit");
                depositBtn.setOnAction(e -> {
                    TextInputDialog dialog = new TextInputDialog();
                    dialog.setHeaderText("Enter deposit amount:");
                    dialog.showAndWait().ifPresent(input -> {
                        try {
                            double amount = Double.parseDouble(input);
                            if (amount <= 0) { statusLabel.setText("❌ Invalid amount. Enter a positive number."); return; }
                            if (c.deposit(amount)) {
                                accLabel.setText("ID: " + c.getAccountID() + " | Balance: " + c.getBalance() + " | Branch: " + c.getBranch());
                                statusLabel.setText("✅ Deposit successful.");
                            } else {
                                statusLabel.setText("⚠️ Deposit failed. Try again.");
                            }
                        } catch (NumberFormatException ex) {
                            statusLabel.setText("❌ Invalid amount. Enter a number.");
                        }
                    });
                });

                Button withdrawBtn = new Button("Withdraw");
                withdrawBtn.setOnAction(e -> {
                    Dialog<Pair<String, String>> dialog = new Dialog<>();
                    dialog.setTitle("Withdraw Funds");

                    Label amountLabel = new Label("Amount:");
                    TextField amountField = new TextField();
                    Label pinLabel = new Label("PIN:");
                    PasswordField pinField = new PasswordField();

                    GridPane grid = new GridPane();
                    grid.add(amountLabel, 0, 0);
                    grid.add(amountField, 1, 0);
                    grid.add(pinLabel, 0, 1);
                    grid.add(pinField, 1, 1);
                    dialog.getDialogPane().setContent(grid);

                    ButtonType okBtn = new ButtonType("Withdraw", ButtonBar.ButtonData.OK_DONE);
                    dialog.getDialogPane().getButtonTypes().addAll(okBtn, ButtonType.CANCEL);

                    dialog.setResultConverter(btn -> btn == okBtn ? new Pair<>(amountField.getText(), pinField.getText()) : null);

                    dialog.showAndWait().ifPresent(pair -> {
                        try {
                            double amount = Double.parseDouble(pair.getKey());
                            String pin = pair.getValue();
                            if (amount <= 0) { statusLabel.setText("❌ Invalid amount. Enter a positive number."); return; }

                            if (c.withdraw(amount, pin)) {
                                accLabel.setText("ID: " + c.getAccountID() + " | Balance: " + c.getBalance() + " | Branch: " + c.getBranch());
                                statusLabel.setText("✅ Withdrawal successful.");
                            } else {
                                statusLabel.setText("❌ Withdrawal failed (check PIN, funds).");
                            }
                        } catch (NumberFormatException ex) {
                            statusLabel.setText("❌ Invalid amount. Enter a number.");
                        }
                    });
                });

                HBox accRow = new HBox(10, accLabel, depositBtn, withdrawBtn);
                accRow.setAlignment(Pos.CENTER_LEFT);
                sectionBox.getChildren().add(accRow);
            }

        } catch (SQLException e) {
            sectionBox.getChildren().add(new Label("⚠️ Error loading cheque accounts: " + e.getMessage()));
        }

        return sectionBox;
    }
    private VBox getInvestmentAccounts(int customerID) {
        VBox sectionBox = new VBox(10);
        sectionBox.setPadding(new Insets(10));

        Label sectionLabel = new Label("Investment Accounts");
        sectionLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
        Label statusLabel = new Label();
        sectionBox.getChildren().addAll(sectionLabel, statusLabel);

        try (Connection conn = DBConnector.connect();
             PreparedStatement stmt = conn.prepareStatement(
                     "SELECT accountID, balance, branch, JCustomerID, BCustomerID, ICustomerID " +
                             "FROM investment_account WHERE JCustomerID = ? OR BCustomerID = ? OR ICustomerID = ?"
             )) {
            stmt.setInt(1, customerID);
            stmt.setInt(2, customerID);
            stmt.setInt(3, customerID);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                InvestmentAccount inv = new InvestmentAccount();
                inv.setAccountID(rs.getInt("accountID"));
                inv.setBalance(rs.getDouble("balance"));
                inv.setBranch(rs.getString("branch"));

                int jId = rs.getInt("JCustomerID");
                int bId = rs.getInt("BCustomerID");
                int iId = rs.getInt("ICustomerID");

                if (iId != 0) { IndividualCustomer i = new IndividualCustomer(); i.setCustomerID(iId); inv.setIndividualCustomer(i); }
                if (bId != 0) { BusinessCustomer b = new BusinessCustomer(); b.setCustomerID(bId); inv.setBusinessCustomer(b); }
                if (jId != 0) { JointCustomer j = new JointCustomer(); j.setCustomerID(jId); inv.setJointCustomer(j); }

                String formattedBalance = String.format("%.2f", inv.getBalance());
                Label accLabel = new Label("ID: " + inv.getAccountID() + " | Balance: " +formattedBalance + " | Branch: " + inv.getBranch());

                Button depositBtn = new Button("Deposit");
                depositBtn.setOnAction(e -> {
                    TextInputDialog dialog = new TextInputDialog();
                    dialog.setHeaderText("Enter deposit amount:");
                    dialog.showAndWait().ifPresent(input -> {
                        try {
                            double amount = Double.parseDouble(input);
                            if (amount <= 0) { statusLabel.setText("❌ Invalid amount. Enter a positive number."); return; }
                            if (inv.deposit(amount)) {
                                String formattedBalance2 = String.format("%.2f", inv.getBalance());
                                accLabel.setText("ID: " + inv.getAccountID() + " | Balance: " + formattedBalance2 + " | Branch: " + inv.getBranch());
                                statusLabel.setText("✅ Deposit successful.");
                            } else {
                                statusLabel.setText("⚠️ Deposit failed. Try again.");
                            }
                        } catch (NumberFormatException ex) {
                            statusLabel.setText("❌ Invalid amount. Enter a number.");
                        }
                    });
                });

                Button withdrawBtn = new Button("Withdraw");
                withdrawBtn.setOnAction(e -> {
                    Dialog<Pair<String, String>> dialog = new Dialog<>();
                    dialog.setTitle("Withdraw Funds");

                    Label amountLabel = new Label("Amount:");
                    TextField amountField = new TextField();
                    Label pinLabel = new Label("PIN:");
                    PasswordField pinField = new PasswordField();

                    GridPane grid = new GridPane();
                    grid.add(amountLabel, 0, 0);
                    grid.add(amountField, 1, 0);
                    grid.add(pinLabel, 0, 1);
                    grid.add(pinField, 1, 1);
                    dialog.getDialogPane().setContent(grid);

                    ButtonType okBtn = new ButtonType("Withdraw", ButtonBar.ButtonData.OK_DONE);
                    dialog.getDialogPane().getButtonTypes().addAll(okBtn, ButtonType.CANCEL);

                    dialog.setResultConverter(btn -> btn == okBtn ? new Pair<>(amountField.getText(), pinField.getText()) : null);

                    dialog.showAndWait().ifPresent(pair -> {
                        try {
                            double amount = Double.parseDouble(pair.getKey());
                            String pin = pair.getValue();
                            if (amount <= 0) { statusLabel.setText("❌ Invalid amount. Enter a positive number."); return; }

                            if (inv.withdraw(amount, pin)) {
                                String formattedBalance2 = String.format("%.2f", inv.getBalance());
                                accLabel.setText("ID: " + inv.getAccountID() + " | Balance: " + formattedBalance2 + " | Branch: " + inv.getBranch());
                                statusLabel.setText("✅ Withdrawal successful.");
                            } else {
                                statusLabel.setText("❌ Withdrawal failed (check PIN, funds).");
                            }
                        } catch (NumberFormatException ex) {
                            statusLabel.setText("❌ Invalid amount. Enter a number.");
                        }
                    });
                });

                HBox accRow = new HBox(10, accLabel, depositBtn, withdrawBtn);
                accRow.setAlignment(Pos.CENTER_LEFT);
                sectionBox.getChildren().add(accRow);
            }

        } catch (SQLException e) {
            sectionBox.getChildren().add(new Label("⚠️ Error loading investment accounts: " + e.getMessage()));
        }

        return sectionBox;
    }



















    public static void main(String[] args) {
        testConnection();
        launch(args);

    }

}


