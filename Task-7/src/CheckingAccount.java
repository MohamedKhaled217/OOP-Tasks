public class CheckingAccount extends BankAccount{
    private static  int count = 1;

    private double overdraftLimit = 500;
    private double transactionFee = 0.50;

    @Override
    public boolean withdraw(double amount) {
        if(this.chargeTransactionFee()){
            if(this.balance + 500 < amount) return false;
            if(this.balance >= amount) return super.withdraw(amount);
            this.balance = -(amount - this.balance);
            return true;
        }
        return false;
    }

    public CheckingAccount(String accountHolder, double balance){
        super(accountHolder , balance);
        this.accountId = "CHECK-" + String.valueOf(count);
        count++;
    }

    public boolean chargeTransactionFee(){
        if(this.balance - this.transactionFee < -500) return false;
        this.balance -= this.transactionFee;
        return true;
    }

    @Override
    public String getAccountType(){
        return "Checking Account";
    }

}
