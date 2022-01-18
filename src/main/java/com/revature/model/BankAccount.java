package com.revature.model;

public class BankAccount {
    private int bankAccountId;
    private int accountId;
    private int balance;

    public BankAccount(int bankAccountId, int accountId, int balance) {
        this.bankAccountId = bankAccountId;
        this.accountId = accountId;
        this.balance = balance;
    }

    public int getBankAccountId() {
        return bankAccountId;
    }

    public void setBankAccountId(int bankAccountId) {
        this.bankAccountId = bankAccountId;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }
}
