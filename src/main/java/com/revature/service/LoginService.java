package com.revature.service;

import com.revature.model.Account;
import com.revature.repository.AccountDAO;

public class LoginService {

    private AccountDAO accountDAO = new AccountDAO();

    public Account login(String username, String password){
        return AccountDAO.getAccountByUsernamePassword(username, password);
    }
}
