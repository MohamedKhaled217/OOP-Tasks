import java.util.ArrayList;

// abstract because in our sys we have [savings,checking] accounts so bank account is not an entity we need in sys
public abstract class BankAccount {
    protected String accountId;
    protected String accountHolder;
    protected double balance; // available in same class , package ,  all child classes
    protected ArrayList<String> transactions = new ArrayList<>(); // protected because the subclasses need it

    public BankAccount(String accountHolder, double balance) {
        this.accountHolder = accountHolder;
        this.balance = balance;
    }

    public void deposit(double amount){
        if(amount < 0){
            return;
        }
        this.balance += amount;
        this.transactions.add("Deposit" + amount);
        System.out.println(amount + " added to " + this.accountId + " successfully");
    }

    public boolean withdraw(double amount){
        if(this.balance > 0 && this.balance > amount){
            System.out.println(amount + " removed from " + this.accountId + " successfully");
            this.balance -= amount;
            this.transactions.add("Withdraw" + amount);
            return true;
        }
        return false;
    }

    public double getBalance(){
        return this.balance;
    }

    public void getAccountInfo(){
        System.out.println("Account info");
    }


    public void addTransaction(String transaction){
        this.transactions.add(transaction);
        System.out.println("Transaction Added to " + this.accountId + " History");
    }

    public void displayTransactionHistory(){
        for(var x : this.transactions){
            System.out.println(x);
        }
    }

    public abstract String getAccountType();
}
