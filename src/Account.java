// Account.java
/**
 * Abstract base class representing a bank account.
 * Provides core functionality for account operations and management.
 */
public abstract class Account {
    private final String accountNumber;
    private double balance;
    private static int accountCounter = 1000;
    protected static final double MINIMUM_BALANCE = 0.0;

    public Account() {
        this.accountNumber = generateAccountNumber();
        this.balance = MINIMUM_BALANCE;
    }

    /**
     * Generates a unique account number.
     * @return Formatted 4-digit account number
     */
    private String generateAccountNumber() {
        return String.format("%04d", ++accountCounter);
    }

    // Getters
    public String getAccountNumber() { return accountNumber; }
    public double getBalance() { return balance; }

    /**
     * Deposits money into the account.
     * @param amount Amount to deposit
     * @throws IllegalArgumentException if amount is negative
     */
    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Deposit amount must be positive");
        }
        balance += amount;
    }

    /**
     * Withdraws money from the account.
     * @param amount Amount to withdraw
     * @return true if withdrawal successful, false otherwise
     */
    public boolean withdraw(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Withdrawal amount must be positive");
        }
        if (amount <= balance) {
            balance -= amount;
            return true;
        }
        return false;
    }
}