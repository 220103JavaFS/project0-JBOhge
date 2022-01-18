package com.revature.controller;

import com.revature.Role;
import com.revature.model.Account;
import com.revature.model.AccountDTO;
import com.revature.service.AccountService;
import io.javalin.Javalin;


import java.util.ArrayList;


public class AccountController extends Controller {

    private AccountService accountService = new AccountService();

    @Override
    public void addRoutes(Javalin app) {

        //Get All accounts
        app.get("/accounts", ctx -> {

            ArrayList<Account> accounts = accountService.getAccounts();

            if(accounts.isEmpty()){
                ctx.status(401);
            }
            else{
                ctx.json(accounts);
                ctx.status(200);
            }

        }, Role.EMPLOYEE);

        app.get("/accounts/{id}", ctx -> {
            //TODO: Check that user should have access to this page
            int id = Integer.parseInt(ctx.pathParam("id"));
            Account a = accountService.getAccountById(id);
            if(a != null){
                ctx.json(a);
                ctx.status(200);
            }
            else {
                ctx.status(404);
            }

        }, Role.ANYONE);

        app.get("/accounts/username/{username}", ctx -> {
           String username = ctx.pathParam("username");
           Account a = accountService.getAccountByUsername(username);
            if(a != null){
                ctx.json(a);
                ctx.status(200);
            }
            else {
                ctx.status(404);
            }

        }, Role.EMPLOYEE);

        app.post("/accounts/new", ctx -> {
           AccountDTO accountDTO = ctx.bodyAsClass(AccountDTO.class);
            if (accountService.addAccount(accountDTO)) {
                ctx.status(201);
            } else {
                ctx.status(400);
            }
        }, Role.ANYONE);

        app.put("/accounts/update", ctx -> {
            AccountDTO accountDTO = ctx.bodyAsClass(AccountDTO.class);
            if(accountService.updateAccount(accountDTO)){
                ctx.status(202);
            }
            else {
                ctx.status(400);
            }
        }, Role.EMPLOYEE);

        app.delete("accounts/delete/{id}", ctx -> {
           int accountId = Integer.parseInt(ctx.pathParam("id"));
           if(accountService.deleteAccount(accountId)){
               ctx.status(200);
           }
           else {
               ctx.status(400);
           }
        }, Role.EMPLOYEE);




    }
}
