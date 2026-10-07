package service;

import database.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BankService {

    public void deposit(int accountId, double amount) {

        if (amount <= 0) {
            System.out.println("Invalid Deposit Amount!");
            return;
        }

        String updateBalance =
                "UPDATE accounts SET balance = balance + ? WHERE id = ?";

        String insertTransaction =
                "INSERT INTO transactions " +
                        "(account_id, transaction_type, amount, description) " +
                        "VALUES (?, 'DEPOSIT', ?, 'Cash deposit')";

        try (Connection connection = DBConnection.getConnection()) {

            PreparedStatement balanceStatement =
                    connection.prepareStatement(updateBalance);

            balanceStatement.setDouble(1, amount);
            balanceStatement.setInt(2, accountId);

            int rowsUpdated =
                    balanceStatement.executeUpdate();

            if (rowsUpdated == 0) {
                System.out.println("Account Not Found!");
                return;
            }

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

        // Amount validation
        if (amount <= 0) {
            System.out.println("Invalid Withdrawal Amount!");
            return;
        }

        String checkBalance =
                "SELECT balance FROM accounts WHERE id = ?";

        String updateBalance =
                "UPDATE accounts SET balance = balance - ? WHERE id = ?";

        String insertTransaction =
                "INSERT INTO transactions " +
                        "(account_id, transaction_type, amount, description) " +
                        "VALUES (?, 'WITHDRAW', ?, 'Cash withdrawal')";

        try (Connection connection = DBConnection.getConnection()) {

            PreparedStatement balanceCheck =
                    connection.prepareStatement(checkBalance);

            balanceCheck.setInt(1, accountId);

            var result = balanceCheck.executeQuery();

            if (result.next()) {

                double currentBalance =
                        result.getDouble("balance");

                // Insufficient balance check
                if (currentBalance < amount) {
                    System.out.println("Insufficient Balance!");
                    return;
                }

                PreparedStatement balanceStatement =
                        connection.prepareStatement(updateBalance);

                balanceStatement.setDouble(1, amount);
                balanceStatement.setInt(2, accountId);

                balanceStatement.executeUpdate();

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
    public void transactionHistory(int accountId) {

        String query =
                "SELECT transaction_type, amount, description, transaction_date " +
                        "FROM transactions " +
                        "WHERE account_id = ? " +
                        "ORDER BY transaction_date DESC";

        try (Connection connection = DBConnection.getConnection()) {

            PreparedStatement statement =
                    connection.prepareStatement(query);

            statement.setInt(1, accountId);

            var result = statement.executeQuery();

            System.out.println("===== TRANSACTION HISTORY =====");

            while (result.next()) {

                String type =
                        result.getString("transaction_type");

                double amount =
                        result.getDouble("amount");

                String description =
                        result.getString("description");

                String date =
                        result.getString("transaction_date");

                System.out.println(
                        type + " | ₹" + amount +
                                " | " + description +
                                " | " + date
                );
            }

        } catch (SQLException e) {

            System.out.println("Transaction History Failed!");
            e.printStackTrace();
        }
    }
    public void addCustomer(String name, String email, String phone, String address) {

        String query =
                "INSERT INTO customers (name, email, phone, address) " +
                        "VALUES (?, ?, ?, ?)";

        try (Connection connection = DBConnection.getConnection()) {

            PreparedStatement statement =
                    connection.prepareStatement(query);

            statement.setString(1, name);
            statement.setString(2, email);
            statement.setString(3, phone);
            statement.setString(4, address);

            statement.executeUpdate();

            System.out.println("Customer Added Successfully!");

        } catch (SQLException e) {

            System.out.println("Customer Addition Failed!");
            e.printStackTrace();
        }
    }
    public void viewCustomers() {

        String query =
                "SELECT id, name, email, phone, address " +
                        "FROM customers";

        try (Connection connection = DBConnection.getConnection()) {

            PreparedStatement statement =
                    connection.prepareStatement(query);

            var result = statement.executeQuery();

            System.out.println("===== CUSTOMER LIST =====");

            while (result.next()) {

                int id = result.getInt("id");
                String name = result.getString("name");
                String email = result.getString("email");
                String phone = result.getString("phone");
                String address = result.getString("address");

                System.out.println(
                        "ID: " + id +
                                " | Name: " + name +
                                " | Email: " + email +
                                " | Phone: " + phone +
                                " | Address: " + address
                );
            }

        } catch (SQLException e) {

            System.out.println("Customer Fetch Failed!");
            e.printStackTrace();
        }
    }
    public void searchCustomer(int customerId) {

        String query =
                "SELECT id, name, email, phone, address " +
                        "FROM customers WHERE id = ?";

        try (Connection connection = DBConnection.getConnection()) {

            PreparedStatement statement =
                    connection.prepareStatement(query);

            statement.setInt(1, customerId);

            var result = statement.executeQuery();

            if (result.next()) {

                System.out.println("===== CUSTOMER FOUND =====");

                System.out.println("ID: " +
                        result.getInt("id"));

                System.out.println("Name: " +
                        result.getString("name"));

                System.out.println("Email: " +
                        result.getString("email"));

                System.out.println("Phone: " +
                        result.getString("phone"));

                System.out.println("Address: " +
                        result.getString("address"));

            } else {

                System.out.println("Customer Not Found!");
            }

        } catch (SQLException e) {

            System.out.println("Customer Search Failed!");
            e.printStackTrace();
        }
    }
    public void updateCustomer(
            int customerId,
            String name,
            String email,
            String phone,
            String address) {

        String query =
                "UPDATE customers " +
                        "SET name = ?, email = ?, phone = ?, address = ? " +
                        "WHERE id = ?";

        try (Connection connection = DBConnection.getConnection()) {

            PreparedStatement statement =
                    connection.prepareStatement(query);

            statement.setString(1, name);
            statement.setString(2, email);
            statement.setString(3, phone);
            statement.setString(4, address);
            statement.setInt(5, customerId);

            int rowsUpdated = statement.executeUpdate();

            if (rowsUpdated > 0) {

                System.out.println("Customer Updated Successfully!");

            } else {

                System.out.println("Customer Not Found!");
            }

        } catch (SQLException e) {

            System.out.println("Customer Update Failed!");
            e.printStackTrace();
        }
    }
    public void deleteCustomer(int customerId) {

        String query =
                "DELETE FROM customers WHERE id = ?";

        try (Connection connection = DBConnection.getConnection()) {

            PreparedStatement statement =
                    connection.prepareStatement(query);

            statement.setInt(1, customerId);

            int rowsDeleted = statement.executeUpdate();

            if (rowsDeleted > 0) {

                System.out.println("Customer Deleted Successfully!");

            } else {

                System.out.println("Customer Not Found!");
            }

        } catch (SQLException e) {

            System.out.println("Customer Deletion Failed!");
            e.printStackTrace();
        }
    }
    // ================= ACCOUNT MANAGEMENT =================

    // 1. CREATE ACCOUNT
    public void createAccount(
            int customerId,
            String accountNumber,
            String accountType,
            double balance) {

        String query =
                "INSERT INTO accounts " +
                        "(customer_id, account_number, account_type, balance) " +
                        "VALUES (?, ?, ?, ?)";

        try (Connection connection = DBConnection.getConnection()) {

            PreparedStatement statement =
                    connection.prepareStatement(query);

            statement.setInt(1, customerId);
            statement.setString(2, accountNumber);
            statement.setString(3, accountType);
            statement.setDouble(4, balance);

            statement.executeUpdate();

            System.out.println("Account Created Successfully!");

        } catch (SQLException e) {

            System.out.println("Account Creation Failed!");
            e.printStackTrace();
        }
    }


    // 2. VIEW ALL ACCOUNTS
    public void viewAccounts() {

        String query =
                "SELECT id, customer_id, account_number, " +
                        "account_type, balance " +
                        "FROM accounts";

        try (Connection connection = DBConnection.getConnection()) {

            PreparedStatement statement =
                    connection.prepareStatement(query);

            var result = statement.executeQuery();

            System.out.println("===== ACCOUNT LIST =====");

            while (result.next()) {

                int id = result.getInt("id");
                int customerId = result.getInt("customer_id");
                String accountNumber =
                        result.getString("account_number");
                String accountType =
                        result.getString("account_type");
                double balance =
                        result.getDouble("balance");

                System.out.println(
                        "ID: " + id +
                                " | Customer ID: " + customerId +
                                " | Account No: " + accountNumber +
                                " | Type: " + accountType +
                                " | Balance: ₹" + balance
                );
            }

        } catch (SQLException e) {

            System.out.println("Account Fetch Failed!");
            e.printStackTrace();
        }
    }


    // 3. SEARCH ACCOUNT
    public void searchAccount(int accountId) {

        String query =
                "SELECT id, customer_id, account_number, " +
                        "account_type, balance " +
                        "FROM accounts WHERE id = ?";

        try (Connection connection = DBConnection.getConnection()) {

            PreparedStatement statement =
                    connection.prepareStatement(query);

            statement.setInt(1, accountId);

            var result = statement.executeQuery();

            if (result.next()) {

                System.out.println("===== ACCOUNT FOUND =====");

                System.out.println(
                        "Account ID: " +
                                result.getInt("id")
                );

                System.out.println(
                        "Customer ID: " +
                                result.getInt("customer_id")
                );

                System.out.println(
                        "Account Number: " +
                                result.getString("account_number")
                );

                System.out.println(
                        "Account Type: " +
                                result.getString("account_type")
                );

                System.out.println(
                        "Balance: ₹" +
                                result.getDouble("balance")
                );

            } else {

                System.out.println("Account Not Found!");
            }

        } catch (SQLException e) {

            System.out.println("Account Search Failed!");
            e.printStackTrace();
        }
    }


    // 4. UPDATE ACCOUNT
    public void updateAccount(
            int accountId,
            String accountNumber,
            String accountType) {

        String query =
                "UPDATE accounts " +
                        "SET account_number = ?, account_type = ? " +
                        "WHERE id = ?";

        try (Connection connection = DBConnection.getConnection()) {

            PreparedStatement statement =
                    connection.prepareStatement(query);

            statement.setString(1, accountNumber);
            statement.setString(2, accountType);
            statement.setInt(3, accountId);

            int rowsUpdated =
                    statement.executeUpdate();

            if (rowsUpdated > 0) {

                System.out.println(
                        "Account Updated Successfully!"
                );

            } else {

                System.out.println("Account Not Found!");
            }

        } catch (SQLException e) {

            System.out.println("Account Update Failed!");
            e.printStackTrace();
        }
    }


    // 5. DELETE ACCOUNT
    public void deleteAccount(int accountId) {

        String query =
                "DELETE FROM accounts WHERE id = ?";

        try (Connection connection = DBConnection.getConnection()) {

            PreparedStatement statement =
                    connection.prepareStatement(query);

            statement.setInt(1, accountId);

            int rowsDeleted =
                    statement.executeUpdate();

            if (rowsDeleted > 0) {

                System.out.println(
                        "Account Deleted Successfully!"
                );

            } else {

                System.out.println("Account Not Found!");
            }

        } catch (SQLException e) {

            System.out.println("Account Deletion Failed!");
            e.printStackTrace();
        }
    }
    }


