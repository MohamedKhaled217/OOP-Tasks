//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        // Create a bank
        Bank bank = new Bank("First National Bank");

// Create accounts
        SavingsAccount savings =
                bank.createSavingsAccount("Alice Johnson", 1000.00);

        CheckingAccount checking =
                bank.createCheckingAccount("Bob Smith", 500.00);

// Display all accounts
        bank.displayAllAccounts();

// Deposit money
        savings.deposit(500.00);
        System.out.println("Alice's balance: $" + savings.getBalance());

// Withdraw money
        boolean success = savings.withdraw(200.00);

        if (success) {
            System.out.println("Withdrawal successful!");
        }

// Try to withdraw below minimum balance
        success = savings.withdraw(1500.00);

        if (!success) {
            System.out.println(
                    "Withdrawal failed: Would exceed minimum balance requirement"
            );
        }

// Apply interest to savings account
        savings.applyInterest();

        System.out.println(
                "After interest: $" + savings.getBalance()
        );

// Checking account with overdraft
        checking.withdraw(800.00);  // Uses overdraft

        System.out.println(
                "Bob's balance: $" + checking.getBalance()
        );

// Display transaction history
        savings.displayTransactionHistory();

// Display total bank balance
        System.out.println(
                "Total bank balance: $" + bank.getTotalBankBalance()
        );
    }
}