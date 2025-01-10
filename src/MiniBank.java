// MiniBank.java
import java.util.Scanner;

/**
 * Main application class providing the user interface and interaction flow.
 */
public class MiniBank {
    private final Bank bank;
    private final Scanner scanner;
    private int loginAttempts;
    private static final int MAX_LOGIN_ATTEMPTS = 3;

    public MiniBank() {
        this.bank = new Bank();
        this.scanner = new Scanner(System.in);
        this.loginAttempts = 0;
    }

    public void start() {
        while (true) {
            displayMainMenu();
            String choice = scanner.nextLine();

            switch (choice) {
                case "1" -> createUser();
                case "2" -> login();
                case "3" -> {
                    System.out.println("\n🏧 Thank you for using MiniBank! Goodbye! 👋");
                    return;
                }
                default -> System.out.println("\n❌ Invalid choice. Please try again.");
            }
        }
    }

    private void displayMainMenu() {
        System.out.println("\n╔════════ MINIBANK ════════╗");
        System.out.println("║ 1. Create User           ║");
        System.out.println("║ 2. Log In                ║");
        System.out.println("║ 3. Quit                  ║");
        System.out.println("╚════════════════════════╝");
        System.out.print("Choose an option: ");
    }

    private void createUser() {
        System.out.print("\n📝 Enter personnummer (YYMMDDXXXX): ");
        String personnummer = scanner.nextLine();
        System.out.print("🔑 Enter PIN (4 digits): ");
        String pin = scanner.nextLine();

        if (bank.createUser(personnummer, pin)) {
            System.out.println("\n✅ User created successfully!");
        } else {
            System.out.println("\n❌ Invalid input or user already exists.");
        }
    }

    private void login() {
        if (loginAttempts >= 3) {
            System.out.println("\n🚫 Too many failed attempts. System locked.");
            System.exit(0);
        }

        System.out.print("\n👤 Enter personnummer: ");
        String personnummer = scanner.nextLine();
        System.out.print("🔑 Enter PIN: ");
        String pin = scanner.nextLine();

        if (bank.login(personnummer, pin)) {
            loginAttempts = 0;
            userMenu();
        } else {
            loginAttempts++;
            System.out.println("\n❌ Invalid credentials. Attempts remaining: " + (3 - loginAttempts));
        }
    }

    private void userMenu() {
        while (true) {
            System.out.println("\n╔═══════ USER MENU ════════╗");
            System.out.println("║ 1. Show Accounts         ║");
            System.out.println("║ 2. Make Transfer         ║");
            System.out.println("║ 3. Log Out              ║");
            System.out.println("╚════════════════════════╝");
            System.out.print("Choose an option: ");

            String choice = scanner.nextLine();
            switch (choice) {
                case "1" -> showAccounts();
                case "2" -> makeTransfer();
                case "3" -> {
                    bank.logout();
                    return;
                }
                default -> System.out.println("\n❌ Invalid choice.");
            }
        }
    }

    private void showAccounts() {
        User user = bank.getCurrentUser();
        System.out.println("\n📊 Your Accounts:");
        System.out.printf("💰 Salary Account (%s): %.2f SEK%n",
                user.getSalaryAccount().getAccountNumber(),
                user.getSalaryAccount().getBalance());
        System.out.printf("💳 Savings Account (%s): %.2f SEK%n",
                user.getSavingsAccount().getAccountNumber(),
                user.getSavingsAccount().getBalance());
    }

    private void makeTransfer() {
        User user = bank.getCurrentUser();
        showAccounts();

        Account sourceAccount = getSourceAccount(user);
        if (sourceAccount == null) return;

        String destAccount = getDestinationAccount();
        if (destAccount == null) return;

        try {
            double amount = getTransferAmount();
            if (bank.transfer(sourceAccount, destAccount, amount)) {
                System.out.println("\n✅ Transfer successful!");
                showAccounts(); // Show updated balances
            } else {
                System.out.println("\n❌ Transfer failed. Please check account number and balance.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("\n❌ " + e.getMessage());
        }
    }

    private Account getSourceAccount(User user) {
        System.out.print("\nSelect source account (1: Salary, 2: Savings): ");
        return switch (scanner.nextLine()) {
            case "1" -> user.getSalaryAccount();
            case "2" -> user.getSavingsAccount();
            default -> null;
        };
    }

    private String getDestinationAccount() {
        System.out.print("Enter destination account number: ");
        String account = scanner.nextLine();
        return account.matches("\\d{4}") ? account : null;
    }

    private double getTransferAmount() {
        System.out.print("Enter amount to transfer: ");
        String input = scanner.nextLine();
        try {
            double amount = Double.parseDouble(input);
            if (amount <= 0) throw new IllegalArgumentException("Amount must be positive");
            return amount;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid amount format");
        }
    }

    public static void main(String[] args) {
        new MiniBank().start();
    }
}