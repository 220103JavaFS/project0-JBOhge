package com.revature.service;

import com.revature.model.Account;
import com.revature.repository.AccountDAOImpl;

public class LoginService {

    private AccountDAOImpl accountDAO = new AccountDAOImpl();

    public Account login(String username, String password){
        return accountDAO.getAccountByUsernamePassword(username, password);
    }
}
