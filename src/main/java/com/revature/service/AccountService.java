package com.revature.service;

import com.revature.model.Account;
import com.revature.model.AccountDTO;
import com.revature.model.AllAccount;
import com.revature.repository.AccountDAO;
import com.revature.repository.AccountDAOImpl;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class AccountService {

    private AccountDAO accountDAO = new AccountDAOImpl();

    public AccountService(){

    }

    public AccountService(AccountDAO accountDAO){
        this.accountDAO=accountDAO;
    }


    public ArrayList<Account> getAccounts()  {
        return accountDAO.getAccounts();
    }

    public Account getAccountById(int id){
        return accountDAO.getAccountById(id);
    }

    public Account getAccountByUsername(String username){
        return accountDAO.getAccountByUsername(username);
    }

    public AllAccount getAllAccounts(int accountId){
        return accountDAO.getAllAccountById(accountId);
    }


    public boolean addAccount(AccountDTO accountDTO) {
        Account account = Account.getAccountObject(accountDTO);

        return accountDAO.addAccount(account);
    }

    public boolean updateAccount(AccountDTO accountDTO){
        Account account = Account.getAccountObject(accountDTO);

        return accountDAO.updateAccount(account);
    }

    public boolean deleteAccount(int id){
        return accountDAO.deleteAccount(id);
    }
}
