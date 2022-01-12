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
        app.get("/accounts/{id}", ctx -> {
            int id = Integer.parseInt(ctx.pathParam("id"));
            Account a = accountService.getAccountById(id);
            if(a != null){
                ctx.json(a);
                ctx.status(200);
            }
            else {
                ctx.status(404);
            }

        });
        app.post("/accounts/new", ctx -> {
           Account a = ctx.bodyAsClass(Account.class);
            if (accountService.addAccount(a)) {
                ctx.status(201);
            } else {
                ctx.status(400);
            }
        });


    }
}
