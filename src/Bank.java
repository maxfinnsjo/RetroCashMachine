// Bank.java
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

/**
 * Central bank management system handling users and transactions.
 */
public class Bank {
    private final Map<String, User> users;
    private User currentUser;
    private static final int MAX_TRANSFER_AMOUNT = 1000000;

    public Bank() {
        this.users = new HashMap<>();
        this.currentUser = null;
    }

    /**
     * Creates a new user with validation checks.
     * @return true if user created successfully
     */
    public boolean createUser(String personnummer, String pin) {
        if (!PersonnummerValidator.validate(personnummer)) {
            throw new IllegalArgumentException("Invalid personnummer format");
        }
        if (!pin.matches("\\d{4}")) {
            throw new IllegalArgumentException("PIN must be 4 digits");
        }
        if (users.containsKey(personnummer)) {
            return false;
        }
        users.put(personnummer, new User(personnummer, pin));
        return true;
    }

    public boolean login(String personnummer, String pin) {
        User user = users.get(personnummer);
        if (user != null && user.validatePin(pin)) {
            currentUser = user;
            return true;
        }
        return false;
    }

    public void logout() {
        currentUser = null;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    /**
     * Executes a transfer between accounts with validation.
     * @throws IllegalArgumentException for invalid amounts
     */
    public boolean transfer(Account from, String toAccountNumber, double amount) {
        if (amount <= 0 || amount > MAX_TRANSFER_AMOUNT) {
            throw new IllegalArgumentException("Invalid transfer amount");
        }

        Account toAccount = findAccount(toAccountNumber);
        if (toAccount == null || toAccount == from) {
            return false;
        }

        if (from.withdraw(amount)) {
            toAccount.deposit(amount);
            return true;
        }
        return false;
    }

    private Account findAccount(String accountNumber) {
        return users.values().stream()
                .flatMap(user -> Stream.of(user.getSalaryAccount(), user.getSavingsAccount()))
                .filter(account -> account.getAccountNumber().equals(accountNumber))
                .findFirst()
                .orElse(null);
    }
}
