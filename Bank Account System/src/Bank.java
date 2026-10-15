import java.util.ArrayList;

public class Bank {
    private String bankName;
    private ArrayList<BankAccount> accounts = new ArrayList<>();

    public Bank(String bankName){
        this.bankName = bankName;
    }

    public SavingsAccount createSavingsAccount(String holder, double initialDeposit){
        if(initialDeposit >= SavingsAccount.getMinimumBalance()) {
            SavingsAccount acc = new SavingsAccount(holder, initialDeposit);
            this.accounts.add(acc);
            System.out.println("Saving account created Successfully");
            return acc;
        }
        else {
            System.out.println("inital deposit must be at least " + SavingsAccount.getMinimumBalance() + "$");
        }
        return null;
    }

    public CheckingAccount createCheckingAccount(String holder, double initialDeposit){
        if(initialDeposit < 0) {
            System.out.println("initial deposit is not valid");
            return null;
        }
        CheckingAccount acc = new CheckingAccount(holder, initialDeposit);
        this.accounts.add(acc);
        System.out.println(acc.getAccountType() +" created Successfully");
        return acc;
    }

    public BankAccount findAccount(String accountNumber){
        for(var acc : this.accounts){
            if (acc.accountId.equals(accountNumber)) return acc;
        }
        System.out.println("Account is not found");
        return null;
    }
    public void displayAllAccounts(){
        System.out.println("=== All Accounts in " + this.bankName + " Bank ===");
        for(var x : this.accounts){
            System.out.println("Account: "+ x.accountId+ "- " + x.accountHolder + "-" + x.getAccountType());
            System.out.println("Balance:" +"$" +x.getBalance());
        }
    }

    public double getTotalBankBalance(){
        double res = 0;
        for(var x : this.accounts) res+=x.getBalance();
        return res;
    }
}
