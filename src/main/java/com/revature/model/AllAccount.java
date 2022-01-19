package com.revature.model;

import java.util.ArrayList;

public class AllAccount {

    private Account account;
    private ArrayList<BankAccount> bankAccountList;

    public AllAccount() {

    }

    public AllAccount(Account account, ArrayList<BankAccount> bankAccountList) {
        this.account = account;
        this.bankAccountList = bankAccountList;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public ArrayList<BankAccount> getBankAccountList() {
        return bankAccountList;
    }

    public void setBankAccountList(ArrayList<BankAccount> bankAccountList) {
        this.bankAccountList = bankAccountList;
    }
}

