package com.revature.service;

import com.revature.model.Application;
import com.revature.model.BankAccount;
import com.revature.model.Transaction;
import com.revature.repository.BankAccountDAO;
import com.revature.repository.BankAccountDAOImpl;

import java.util.ArrayList;

public class BankAccountService {

    BankAccountDAO bankAccountDAO = new BankAccountDAOImpl();

    public ArrayList<BankAccount> getBankAccounts() {

        return bankAccountDAO.getBankAccounts();
    }

    public BankAccount getBankAccountById(int bankAccountId) {

        return bankAccountDAO.getBankAccountById(bankAccountId);
    }

    public boolean createBankAccount(int applicationId) {

        return bankAccountDAO.createBankAccount(applicationId);
    }

    public boolean updateBankAccount(BankAccount bankAccount){
        return bankAccountDAO.updateBankAccount(bankAccount);
    }

    public boolean deleteBankAccount(int bankAccountId) {

        return bankAccountDAO.deleteBankAccount(bankAccountId);
    }

    public boolean withdrawFromBankAccount(Transaction transaction) {
        return bankAccountDAO.withdrawFromBankAccount(transaction);
    }

    public boolean depositToBankAccount(Transaction transaction) {
        return bankAccountDAO.depositToBankAccount(transaction);
    }

    public boolean transferToBankAccount(Transaction transaction) {
        return bankAccountDAO.transferToBankAccount(transaction);
    }
}
