import database.DBConnection;
import service.BankService;

public class Main {

  public static void main(String[] args) {

    System.out.println("Bank Management System Started!");

    // Test database connection
    DBConnection.getConnection();

    // Create BankService object
    BankService bankService = new BankService();
    // ================= ACCOUNT MANAGEMENT TEST =================

// Create Account
    /*bankService.createAccount(
            3,
            "1000000002",
            "SAVINGS",
            10000
    );*/

// View Accounts
    bankService.viewAccounts();

// Search Account
    bankService.searchAccount(2);

// Update Account
    bankService.updateAccount(
            2,
            "1000000007",
            "CURRENT"
    );

// Delete Account
bankService.deleteAccount(2);
  //  bankService.deleteCustomer(5);
   /* bankService.updateCustomer(
            3,
            "Amit Kumar",
            "amit@gmail.com",
            "9999999999",
            "Delhi"
    );*/
    //bankService.viewCustomers();
   // bankService.searchCustomer(3);
    // Add Customer
    /*bankService.addCustomer(
            "Amit Kumar",
               "amit123@gmail.com",
            "9876543211",
            "Patna"
    );*/

    // Deposit ₹2000 into account ID 1
   // bankService.deposit(1, 2000);
    //bankService.withdraw(1, 2000);
   // bankService.checkBalance(1);
    // Transaction History
    //bankService.transactionHistory(1);
  }
}