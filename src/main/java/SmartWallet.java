public class SmartWallet {

    private String owner;
    private double balance;
    private String accountType;
    private String status;


    SmartWallet(String owner, String accountType) {
        this.owner = owner;
        this.balance = 0.0;
        this.accountType = accountType;
        this.status = "Activa";
    }

    public boolean deposit(double amount) {
        if (amount <= 0) {
            return false;
        }

        double cashback = 0.0;
        if (amount > 100) {
            cashback = amount * 0.01; // 1% cashback
        }

        double newBalance = balance + amount + cashback;

        if (accountType.equals("Standard") && newBalance > 5000) {
            return false;
        }

        balance = newBalance;
        return true;
    }


    public boolean withdraw(double amount) {
        if (amount <= 0) {
            return false;
        }

        if (amount > balance) {
            return false;
        }

        balance -= amount;

        if (balance == 0) {
            status = "Inactiva";
        }

        return true;
    }

    public String getOwner() {
        return owner;
    }

    public double getBalance() {
        return balance;
    }

    public String getAccountType() {
        return accountType;
    }

    public String getStatus() {
        return status;
    }
}