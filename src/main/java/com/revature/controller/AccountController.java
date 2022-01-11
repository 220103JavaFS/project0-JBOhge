package com.revature.controller;

import com.revature.model.Account;
import com.revature.service.AccountService;
import io.javalin.Javalin;

import java.util.ArrayList;

public class AccountController extends Controller{

    private AccountService accountService = new AccountService();

    @Override
    public void addRoutes(Javalin app) {

        //Get All accounts
        app.get("/accounts", ctx -> {
            ArrayList<Account> accounts = accountService.getAccounts();
            ctx.json(accounts);
            ctx.status(200);
        });


    }
}
