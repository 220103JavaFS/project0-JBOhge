package com.revature.repository;

import com.revature.model.BankAccount;
import com.revature.model.Transaction;

import java.util.ArrayList;

public interface BankAccountDAO {

    ArrayList<BankAccount> getBankAccounts();

    BankAccount getBankAccountById(int bankAccountId);

    boolean createBankAccount(int applicationId);

    boolean deleteBankAccount(int bankAccountId);

    boolean withdrawFromBankAccount(Transaction transaction);

    boolean depositToBankAccount(Transaction transaction);

    boolean transferToBankAccount(Transaction transaction);

}
