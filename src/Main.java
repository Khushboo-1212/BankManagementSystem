import database.DBConnection;
import service.BankService;

public class Main {

  public static void main(String[] args) {

    System.out.println("Bank Management System Started!");

    // Test database connection
    DBConnection.getConnection();

    // Create BankService object
    BankService bankService = new BankService();

    // Deposit ₹2000 into account ID 1
   // bankService.deposit(1, 2000);
    //bankService.withdraw(1, 2000);
    bankService.checkBalance(1);
  }
}