// User.java
/**
 * Represents a bank customer with associated accounts and credentials.
 */
public class User {
    private final String personnummer;
    private final String pin;
    private final SalaryAccount salaryAccount;
    private final SavingsAccount savingsAccount;
    private String name;
    private String email;

    public User(String personnummer, String pin) {
        this.personnummer = personnummer;
        this.pin = pin;
        this.salaryAccount = new SalaryAccount();
        this.savingsAccount = new SavingsAccount();
    }

    public boolean validatePin(String pin) {
        return this.pin.equals(pin);
    }

    // Getters
    public String getPersonnummer() { return personnummer; }
    public SalaryAccount getSalaryAccount() { return salaryAccount; }
    public SavingsAccount getSavingsAccount() { return savingsAccount; }

    // Optional information setters
    public void setName(String name) { this.name = name; }
    public void setEmail(String email) { this.email = email; }
}