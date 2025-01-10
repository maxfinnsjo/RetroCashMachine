public class SalaryAccount extends Account {
    private static final double OVERDRAFT_LIMIT = -5000.0;

    @Override
    public boolean withdraw(double amount) {
        if (getBalance() - amount >= OVERDRAFT_LIMIT) {
            super.withdraw(amount);
            return true;
        }
        return false;
    }
}