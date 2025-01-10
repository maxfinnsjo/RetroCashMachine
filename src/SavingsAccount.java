public class SavingsAccount extends Account {
    private static final double INTEREST_RATE = 0.015; // 1.5% annual interest

    public double calculateInterest() {
        return getBalance() * INTEREST_RATE;
    }
}
