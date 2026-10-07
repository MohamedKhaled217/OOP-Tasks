
public class SavingsAccount extends BankAccount{
    static private int count = 1;

    private double interestRate = 0.02;
    private static double minimumBalance = 100;

    public static double getMinimumBalance() {
        return minimumBalance;
    }

    public SavingsAccount(String accountHolder, double balance){
        super(accountHolder , balance);
        this.accountId = "SAV-" + String.valueOf(count);
        count++;
    }

    @Override
    public boolean withdraw(double amount) {
        if(this.balance < this.minimumBalance){
            System.out.println("your balance is under the min required");
            return false;
        }
        return super.withdraw(amount);
    }

    public void applyInterest(){
        System.out.println("balance before interest" + this.balance);
        double interest = this.interestRate * this.balance;
        this.balance += interest;
        System.out.println("Interest rate applied");
        System.out.println("balance after interest" + this.balance);
        this.transactions.add("Interest : " + interest);
    }

    @Override
    public String getAccountType(){
        return "Savings Account";
    }
}
