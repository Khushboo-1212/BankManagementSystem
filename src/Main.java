import database.DBConnection;
import service.BankService;

public class Main {

  public static void main(String[] args) {

    System.out.println("Bank Management System Started!");

    // Test database connection
    DBConnection.getConnection();

    // Create BankService object
    BankService bankService = new BankService();
    // Add Customer
    bankService.addCustomer(
            "Amit Kumar",
            "amit123@gmail.com",
            "9876543211",
            "Patna"
    );

    // Deposit ₹2000 into account ID 1
   // bankService.deposit(1, 2000);
    //bankService.withdraw(1, 2000);
    bankService.checkBalance(1);
    // Transaction History
    bankService.transactionHistory(1);
  }
}