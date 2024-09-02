# Simple ATM Application

A Java-based ATM application with the following features:

## Features
- **Balance Check**
- **Cash Deposit**
- **Cash Withdrawal**
- **View Transaction History**

## Implementation

### Packages
- **bank**: Manages customer accounts and banking operations.
- **atm**: Handles ATM transactions.

### Key Classes
- **Customer**: Stores customer details (name, account number, PIN, balance).
- **Transaction**: Logs transaction details.
- **Bank**: Creates accounts and manages customer data.
- **SimpleATM**: Performs ATM operations.

### Files
- **transactionas.txt**: Logs all transactions.
- **customersData.txt**: Stores customer details.

## Usage
1. **Create an account** using `Bank` class provide your details, and set your own PIN. You'll be assigned a unique 11-digit account number.
2. **Access ATM** via `SimpleATM`, enter your account number and PIN to check balance, deposit, withdraw, or get a receipt.


