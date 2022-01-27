package com.revature.repository;

import com.revature.model.Account;
import com.revature.model.AllAccount;

import java.util.ArrayList;

public interface AccountDAO {

    boolean addAccount(Account a);

    ArrayList<Account> getAccounts();

    Account getAccountById(int id);

    Account getAccountByUsername(String username);

    boolean updateAccount(Account a);

    boolean deleteAccount(int id);

    AllAccount getAllAccountById(int accountId);

}
