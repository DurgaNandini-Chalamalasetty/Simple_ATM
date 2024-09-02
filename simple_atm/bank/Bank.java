package bank;

import bank.customer.Customer;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Random;

public class Bank {
    private final String name;
    private final List<Customer> customers = new ArrayList<>();
    private final String filename = "customersData.txt";
    Scanner scan = new Scanner(System.in);
    public Bank(String name) 
    {
        this.name = name;
        loadCustomersFromFile();
    }

    public String getName() {
        return name;
    }

    public void createAccount() {
    
        // Input name
        System.out.println("Please enter your name:");
        String name = scan.nextLine();

        // Input initial deposit
        System.out.println("Please enter your initial deposit:");
        double initialDeposit = scan.nextDouble();

        // Validate user input
        if (name.isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty.");
        }
        if (initialDeposit < 0) {
            throw new IllegalArgumentException("Initial deposit must be positive.");
        }

        // Generate unique account number
        String accountNumber = generateAccountNumber();

        // Prompt for and validate PIN
        System.out.println("Please set a 4-digit PIN for your account:");
        int pin = scan.nextInt();
        if (String.valueOf(pin).length() != 4) {
            System.out.println("Invalid PIN length. Please enter a 4-digit PIN.");
            return;
        }

        // Create customer object
        Customer customer = new Customer(name, accountNumber, pin, initialDeposit);
        customers.add(customer);
        saveCustomersToFile();
        

        System.out.println("Account created successfully for " + name + " with account number: " + accountNumber);
    }

    public String generateAccountNumber() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 11; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }

    public Customer getCustomer(String accountNumber) {
        for (Customer customer : customers) {
            if (customer.getAccountNumber().equals(accountNumber)) {
                return customer;
            }
        }
        return null;
    }
    private void loadCustomersFromFile() {
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(filename))) {
            // Read list of customers from file
            customers.addAll((List<Customer>) inputStream.readObject());
        } catch (IOException | ClassNotFoundException e) {
            // Handle file read error
            System.err.println("Error loading customer data: " + e.getMessage());
        }
    }

    private void saveCustomersToFile() {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(filename))) {
            // Write list of customers to file
            outputStream.writeObject(customers);
        } catch (IOException e) {
            // Handle file write error
            System.err.println("Error saving customer data: " + e.getMessage());
        }
    }
    public static void main(String args[]){
        Bank account = new Bank("Capital Bank");
        account.createAccount();
        
    }
}



