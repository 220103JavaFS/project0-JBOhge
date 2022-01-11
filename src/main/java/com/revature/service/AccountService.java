package com.revature.service;

import com.revature.model.Account;
import com.revature.repository.AccountDAO;
import java.util.ArrayList;

public class AccountService {

    private AccountDAO accountDAO = new AccountDAO();

    public ArrayList<Account> getAccounts()  {
        return accountDAO.getAccounts();
    }
}
