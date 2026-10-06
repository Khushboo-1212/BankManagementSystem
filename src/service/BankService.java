package service;

import database.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BankService {

    public void deposit(int accountId, double amount) {

        String updateBalance =
                "UPDATE accounts SET balance = balance + ? WHERE id = ?";

        String insertTransaction =
                "INSERT INTO transactions " +
                        "(account_id, transaction_type, amount, description) " +
                        "VALUES (?, 'DEPOSIT', ?, 'Cash deposit')";

        try (Connection connection = DBConnection.getConnection()) {

            // Update account balance
            PreparedStatement balanceStatement =
                    connection.prepareStatement(updateBalance);

            balanceStatement.setDouble(1, amount);
            balanceStatement.setInt(2, accountId);

            balanceStatement.executeUpdate();

            // Save transaction history
            PreparedStatement transactionStatement =
                    connection.prepareStatement(insertTransaction);

            transactionStatement.setInt(1, accountId);
            transactionStatement.setDouble(2, amount);

            transactionStatement.executeUpdate();

            System.out.println("Deposit Successful!");
            System.out.println("Amount Deposited: ₹" + amount);

        } catch (SQLException e) {

            System.out.println("Deposit Failed!");
            e.printStackTrace();
        }
    }
        public void withdraw(int accountId, double amount) {

            String checkBalance =
                    "SELECT balance FROM accounts WHERE id = ?";

            String updateBalance =
                    "UPDATE accounts SET balance = balance - ? WHERE id = ?";

            String insertTransaction =
                    "INSERT INTO transactions " +
                            "(account_id, transaction_type, amount, description) " +
                            "VALUES (?, 'WITHDRAW', ?, 'Cash withdrawal')";

            try (Connection connection = DBConnection.getConnection()) {

                // Check current balance
                PreparedStatement balanceCheck =
                        connection.prepareStatement(checkBalance);

                balanceCheck.setInt(1, accountId);

                var result = balanceCheck.executeQuery();

                if (result.next()) {

                    double currentBalance = result.getDouble("balance");

                    // Check sufficient balance
                    if (currentBalance < amount) {
                        System.out.println("Insufficient Balance!");
                        return;
                    }

                    // Update balance
                    PreparedStatement balanceStatement =
                            connection.prepareStatement(updateBalance);

                    balanceStatement.setDouble(1, amount);
                    balanceStatement.setInt(2, accountId);

                    balanceStatement.executeUpdate();

                    // Save transaction
                    PreparedStatement transactionStatement =
                            connection.prepareStatement(insertTransaction);

                    transactionStatement.setInt(1, accountId);
                    transactionStatement.setDouble(2, amount);

                    transactionStatement.executeUpdate();

                    System.out.println("Withdrawal Successful!");
                    System.out.println("Amount Withdrawn: ₹" + amount);

                } else {
                    System.out.println("Account Not Found!");
                }

            } catch (SQLException e) {

                System.out.println("Withdrawal Failed!");
                e.printStackTrace();
            }
        }
    public void checkBalance(int accountId) {

        String query =
                "SELECT balance FROM accounts WHERE id = ?";

        try (Connection connection = DBConnection.getConnection()) {

            PreparedStatement statement =
                    connection.prepareStatement(query);

            statement.setInt(1, accountId);

            var result = statement.executeQuery();

            if (result.next()) {

                double balance = result.getDouble("balance");

                System.out.println("Current Balance: ₹" + balance);

            } else {

                System.out.println("Account Not Found!");
            }

        } catch (SQLException e) {

            System.out.println("Balance Check Failed!");
            e.printStackTrace();
        }
    }
    }

