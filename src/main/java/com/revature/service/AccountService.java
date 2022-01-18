package com.revature.service;

import com.revature.model.Account;
import com.revature.model.AccountDTO;
import com.revature.repository.AccountDAOImpl;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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

    public boolean addAccount(AccountDTO accountDTO) {
        //generate account password hash and create new account object to insert into DB
        String password = accountDTO.getPassword();
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("SHA-512");
        } catch (NoSuchAlgorithmException e){
            e.printStackTrace();
        }
        byte[] hash = messageDigest.digest(password.getBytes(StandardCharsets.UTF_8));
        Account account = new Account(1, accountDTO.getFirstName(),accountDTO.getLastName(), accountDTO.getUsername(), hash, 1);

        return accountDAO.addAccount(account);
    }
}
