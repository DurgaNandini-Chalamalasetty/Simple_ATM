package bank.customer;

import java.io.Serializable;
public class Customer implements Serializable{
    private final String name;
    private final String accountNumber;
    private final int pin;
    private double balance;

    public Customer(String name, String accountNumber, int pin, double balance) {
        this.name = name;
        this.accountNumber = accountNumber;
        this.pin = pin;
        this.balance = balance;
    }

    public String getName() {
        return name;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public int getPin() {
        return pin;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}

