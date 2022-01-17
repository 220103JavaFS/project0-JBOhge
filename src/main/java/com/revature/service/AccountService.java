package com.revature.service;

import com.revature.model.Account;
import com.revature.repository.AccountDAOImpl;
import java.util.ArrayList;

public class AccountService {

    private AccountDAOImpl accountDAO = new AccountDAOImpl();

    public ArrayList<Account> getAccounts()  {
        return accountDAO.getAccounts();
    }

    public Account getAccountById(int id){
        return accountDAO.getAccountById(id);
    }

    public Account getAccountByUsername(String username){
        return accountDAO.getAccountByUsername(username);
    }

    public boolean addAccount(Account a) {
        return accountDAO.addAccount(a);
    }
}
