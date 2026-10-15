# 🏦 Task-7 Code Review — Banking System

---

## 📁 Project Overview

A banking system with an abstract `BankAccount` base class, two concrete subclasses (`SavingsAccount`, `CheckingAccount`), and a `Bank` class that manages accounts. Demonstrates inheritance, abstraction, polymorphism, and encapsulation.

| File | Role |
|------|------|
| [BankAccount.java](file:///d:/OOP-Tasks/Task-7/src/BankAccount.java) | Abstract base class for all accounts |
| [SavingsAccount.java](file:///d:/OOP-Tasks/Task-7/src/SavingsAccount.java) | Savings account with interest & minimum balance |
| [CheckingAccount.java](file:///d:/OOP-Tasks/Task-7/src/CheckingAccount.java) | Checking account with overdraft & fees |
| [Bank.java](file:///d:/OOP-Tasks/Task-7/src/Bank.java) | Manages a collection of accounts |
| [Main.java](file:///d:/OOP-Tasks/Task-7/src/Main.java) | Demo / driver class |

---

## ✅ What You Did Well

### 1. Good Use of Abstraction
- Making `BankAccount` abstract is the correct design decision. Your comment on line 3 shows you **understand why** it should be abstract — not just how.
- `getAccountType()` as an abstract method forces subclasses to identify themselves. Clean pattern.

### 2. Proper Inheritance Hierarchy
- `SavingsAccount` and `CheckingAccount` both extend `BankAccount` naturally. The **"is-a"** relationship is correct — a savings account *is a* bank account.
- You correctly use `super()` constructor calls and `super.withdraw()` to reuse parent logic instead of duplicating it.

### 3. Method Overriding Done Right
- Both subclasses override `withdraw()` with their own business rules (minimum balance check, overdraft + fees) and delegate to the parent with `super.withdraw()` when appropriate.
- You use the `@Override` annotation consistently — good practice.

### 4. Factory Pattern in Bank
- Account creation is handled through `Bank.createSavingsAccount()` and `Bank.createCheckingAccount()` rather than direct instantiation. This is a solid design choice that centralises validation logic.

### 5. Auto-Generated Account IDs
- Using a `static count` for generating unique IDs (`SAV-1`, `CHECK-1`, etc.) is a clean approach for this scope.

### 6. Transaction History Tracking
- Storing transactions in an `ArrayList<String>` and logging deposits/withdrawals is a nice touch that shows you're thinking beyond the minimum requirements.

---

## ❌ What Needs Improvement

### 1. 🐛 Critical Bug — String Comparison with `==`
```java
// Bank.java, line 37
if (acc.accountId == accountNumber) return acc;
```
This compares **references**, not values. Two different `String` objects with the same characters will return `false`.

**Fix:**
```java
if (acc.accountId.equals(accountNumber)) return acc;
```

> **⛔ CAUTION:** This is a very common Java trap. `==` checks if two references point to the **same object in memory**, not if they have the same content. Always use `.equals()` for String comparison.

---

### 2. 🐛 Bug — Interest Calculated on Already-Updated Balance
In `SavingsAccount.java`, line 28:
```java
this.balance += this.interestRate * this.balance;  // balance is updated here
this.transactions.add("Interest" + this.interestRate * this.balance);  // uses NEW balance
```
The transaction log records the interest based on the **new** balance, not the original. The logged amount is wrong.

**Fix:**
```java
double interest = this.interestRate * this.balance;
this.balance += interest;
this.transactions.add("Interest: " + interest);
```

---

### 3. ⚠️ Broken Encapsulation — Access Modifiers
Several fields use **package-private** (default) access instead of `private`:

| File | Field | Current | Should Be |
|------|-------|---------|-----------|
| `BankAccount.java` line 8 | `transactions` | package-private | `protected` or `private` |
| `Bank.java` line 5 | `accounts` | package-private | `private` |
| `SavingsAccount.java` line 6 | `minimumBalance` | `public static` | `private static` with getter |
| `CheckingAccount.java` line 2 | `count` | `public static` | `private static` |

> **⚠️ IMPORTANT:** Encapsulation is a core OOP pillar. Fields should be `private` by default, exposed only through getters/setters when needed. `minimumBalance` being `public static` means any external code can modify the bank's minimum balance — that's dangerous.

---

### 4. ⚠️ Missing Input Validation
- `deposit()` in `BankAccount.java` accepts **negative amounts** — you could deposit `-1000` and drain the account.
- `withdraw()` condition `this.balance > 0 && this.balance > amount` doesn't handle `amount <= 0`.

**Fix:**
```java
public void deposit(double amount) {
    if (amount <= 0) {
        System.out.println("Deposit amount must be positive");
        return;
    }
    this.balance += amount;
    ...
}
```

---

### 5. ⚠️ Transaction Log Formatting
The transaction strings are missing separators:
```java
this.transactions.add("Deposit" + amount);   // → "Deposit500.0"
this.transactions.add("Withdraw" + amount);  // → "Withdraw200.0"
```
Should be:
```java
this.transactions.add("Deposit: $" + amount);
this.transactions.add("Withdraw: $" + amount);
```

---

### 6. 💡 `getAccountInfo()` Is Empty
`BankAccount.java`, lines 35-37:
```java
public void getAccountInfo(){
    System.out.println("Account info");
}
```
This method prints a static string and provides no actual account info. Either implement it properly or make it abstract.

---

### 7. 💡 `CheckingAccount.withdraw()` — Silent Overdraft
In `CheckingAccount.java`, line 12:
```java
this.balance = -(amount - this.balance);
```
When the withdrawal goes into overdraft, no transaction is logged, and there's no print statement. The parent's `withdraw()` is only called when `balance >= amount`. The overdraft path bypasses all logging.

---

### 8. 💡 Hardcoded Magic Numbers
```java
private double overdraftLimit = 500;    // What is 500?
private double transactionFee = 0.50;   // Why 0.50?
static public double minimumBalance = 100;
```
Consider making these configurable through constructor parameters or at least documenting them with constants:
```java
private static final double DEFAULT_OVERDRAFT_LIMIT = 500.0;
```

---

## 📚 Topics You Should Review

| Topic | Why | Priority |
|-------|-----|----------|
| **String comparison (`.equals()` vs `==`)** | You have a critical bug using `==` for Strings | 🔴 High |
| **Access modifiers (`private`, `protected`, `public`, default)** | Several fields have incorrect visibility | 🔴 High |
| **Input validation & defensive programming** | Negative deposits, zero amounts not handled | 🟡 Medium |
| **`static` modifier semantics** | `public static` fields break encapsulation; `static` vs instance member confusion | 🟡 Medium |
| **Java naming conventions** | `static public` should be `public static` (modifier order) | 🟢 Low |
| **Exception handling** | Currently using `return null` / `return false` — consider throwing exceptions | 🟢 Low |
| **`toString()` override** | Would make `getAccountInfo()` and `displayAllAccounts()` much cleaner | 🟢 Low |

---

## 📊 Overall Rating

| Category | Score | Notes |
|----------|-------|-------|
| **OOP Concepts** | 8 / 10 | Abstraction, inheritance, polymorphism all applied correctly |
| **Encapsulation** | 5 / 10 | Several fields exposed with wrong access modifiers |
| **Code Correctness** | 6 / 10 | String `==` bug, interest calculation bug, missing validations |
| **Code Readability** | 7 / 10 | Clean structure, but some formatting issues and missing separators |
| **Design / Architecture** | 8 / 10 | Factory-style creation, proper hierarchy, transaction tracking |
| **Best Practices** | 6 / 10 | Magic numbers, no input validation, no exceptions |

### **Overall: 6.5 / 10** ⭐

> **📝 NOTE:** Your **understanding of OOP fundamentals is solid** — the class hierarchy, abstraction, and polymorphism usage show you grasp the core concepts. The main areas dragging the score down are **encapsulation violations** and a couple of **bugs** that are common for Java learners. Fix the String comparison issue and tighten your access modifiers, and this jumps to an **8/10** easily.

---

*Review generated on October 7, 2026*
