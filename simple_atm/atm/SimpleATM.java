package atm;

import bank.Bank;
import bank.customer.Customer;
import bank.transaction.Transaction;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SimpleATM {

    private final Bank bank;
    private Customer customer;
    private static final List<Transaction> transactions = new ArrayList<>();
    Scanner scan = new Scanner(System.in);
    public SimpleATM(Bank bank) {
        this.bank = bank;
    }

    public static void main(String[] args) throws IOException {
        Bank bank = new Bank("Capital Bank");
        SimpleATM atm = new SimpleATM(bank);
        atm.login();
    }


    private void login() throws IOException {
        String accountNumber;
        int pin;

        System.out.println("Please enter your account number:");
        accountNumber = scan.nextLine();

        System.out.println("Please enter your PIN:");
        pin = scan.nextInt();

        customer = bank.getCustomer(accountNumber);

        if (customer == null || customer.getPin() != pin) {
            System.out.println("Invalid account number or PIN. Please try again.");
            return;
        }

        // User successfully logged in
        System.out.println("Welcome " + customer.getName() + "!");

        // Display main menu
        mainMenu();
    }

    private void mainMenu() throws IOException {
        int choice;

        do {

            System.out.println("\n\nMain Menu:");
            System.out.println("\t1. Check Balance");
            System.out.println("\t2. Deposit");
            System.out.println("\t3. Withdraw");
            System.out.println("\t4. View Transaction History");
            System.out.println("\t5. Logout");
            System.out.println("Enter your choice: ");

            choice = scan.nextInt();

            switch (choice) {
                case 1:
                    checkBalance();
                    break;
                case 2:
                    deposit();
                    break;
                case 3:
                    withdraw();
                    break;
                case 4:
                    viewTransactions();
                    break;
                case 5:
                    logout();
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 5);
    }

    private void checkBalance() {
        System.out.printf("Your current balance is: \u20B9%.2f\n", customer.getBalance());
        
    }   

    private void deposit() throws IOException {
        double amount;

        System.out.println("Enter the amount you want to deposit:");
        amount = scan.nextDouble();

        if (amount <= 0) {
            System.out.println("Invalid amount. Please enter a positive value.");
            return;
        }

                customer.setBalance(customer.getBalance() + amount); // Update customer's balance
                transactions.add(new Transaction("Deposit", amount)); // Add deposit transaction

                System.out.printf("Successfully deposited \u20B9%.2f.\n", amount);
                System.out.printf("Your new balance is: \u20B9%.2f\n", customer.getBalance());
                
                // Optionally write transaction information to file
                saveTransactionReceiptToFile(new Transaction("Deposit", amount), "transactions.txt" );
            }

            private void withdraw() throws IOException {
                double amount;

                System.out.println("Enter the amount you want to withdraw:");
                amount = scan.nextDouble();

                if (amount <= 0) {
                    System.out.println("Invalid amount. Please enter a positive value.");
                    return;
                } else if (amount > customer.getBalance()) {
                    System.out.printf("Insufficient funds. Your current balance is: %.2f", customer.getBalance());
                    return;
                }

                customer.setBalance(customer.getBalance() - amount); // Update customer's balance
                transactions.add(new Transaction("Withdrawal", amount)); // Add withdrawal transaction

                System.out.printf("Successfully withdrew \u20B9%.2f.\n", amount);
                System.out.printf("Your new balance is: \u20B9%.2f\n", customer.getBalance());
                
                // Optionally write transaction information to file
                saveTransactionReceiptToFile(new Transaction("Withdrawal", amount), "transactions.txt" );
            }

            private void viewTransactions() {
                if (transactions.isEmpty()) {
                    System.out.println("No transactions found.");
                    return;
                }
                
                System.out.println("\nTransaction History:");
                for (Transaction transaction : transactions) {
                System.out.printf("%s\n", formatTransactionReceipt(transaction));
                }
                // Save the transaction receipt to a file
                try {
                // Save the transaction receipt to a file
                    if (!transactions.isEmpty()) 
                        saveTransactionReceiptToFile(transactions.get(0), "transactions.txt");
                    } catch (IOException e) {
                        // Handle the exception
                        System.err.println("Error saving transaction receipt: " + e.getMessage());
                    }
            }
                
            
         
             private String formatTransactionReceipt(Transaction transaction) {
                // Calculate the current account balance after the transaction
                double balanceAfterTransaction = customer.getBalance() ;

                // Format the date, account number, and customer name
                String date = LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                String accountNumber = "**** ****  " + customer.getAccountNumber() .substring(8);
                String customerName = customer.getName();
                String time = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
                // Format the transaction amount
                //String transactionAmount = String.format("%.2f", transaction.getAmount());

                // Format the account balance after transaction
                //String accountBalance = String.format("%.2f", balanceAfterTransaction);
                String transactionAmount = String.format("\u20B9%.2f", transaction.getAmount());

                String accountBalance = String.format("\u20B9%.2f", balanceAfterTransaction);

                // Build the formatted receipt
                StringBuilder receipt = new StringBuilder();
                receipt.append("\t\t----------------------------------------\n");
                receipt.append("\t\t\tCAPITAL BANK ATM\n");
                receipt.append("\t\t----------------------------------------\n");
                receipt.append("\t\tDate\t\t: ").append(date).append("\n");
                receipt.append("\t\tTime\t\t: ").append(time).append("\n");
                receipt.append("\t\tAccount Number  : ").append(accountNumber).append("\n");
                receipt.append("\t\tCustomer Name   : ").append(customerName).append("\n");
                receipt.append("\t\tTransaction     : ").append(transaction.getType()).append("\n");
                receipt.append("\t\tAmount\t\t: ").append(transactionAmount).append("\n");
                receipt.append("\t\tBalance\t\t: ").append(accountBalance).append("\n");
                receipt.append("\t\t----------------------------------------\n");
                receipt.append("\t\tThank you for using our ATM services!\n");
                receipt.append("\t\t----------------------------------------\n");
                receipt.append("\n");

                return receipt.toString();
            }
            private void logout() {
                System.out.println("Thank you for using " + bank.getName() + " ATM!");
                System.exit(0);
            }

            private void saveTransactionReceiptToFile(Transaction transaction, String filePath) throws IOException {
                try (FileWriter writer = new FileWriter(filePath, true)) {
                    writer.write(formatTransactionReceipt(transaction));
                    writer.write("\n");
                }
            }

            }
        
    
