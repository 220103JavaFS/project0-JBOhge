package com.revature.controller;

import com.revature.Application;
import com.revature.Role;
import com.revature.model.Account;
import com.revature.model.AccountDTO;
import com.revature.model.AllAccount;
import com.revature.service.AccountService;
import io.javalin.Javalin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.ArrayList;


public class AccountController extends Controller {

    private AccountService accountService = new AccountService();
    private static Logger log = LoggerFactory.getLogger(Application.class);

    @Override
    public void addRoutes(Javalin app) {

        //Get All accounts
        app.get("/accounts", ctx -> {
            log.info("received /accounts request");
            ArrayList<Account> accounts = accountService.getAccounts();

            if(accounts.isEmpty()){
                ctx.status(401);

            }
            else{
                ctx.json(accounts);
                ctx.status(200);
            }

        }, Role.EMPLOYEE);

        app.get("/accounts/myaccount", ctx -> {
            log.info("received /accounts/myaccount request");
            int accountId = 0;
            try{
                accountId = (Integer)ctx.req.getSession(false).getAttribute("accountId");
            } catch (ClassCastException e){
                e.printStackTrace();
                ctx.status(400);
                return;
            }
            AllAccount a = accountService.getAllAccounts(accountId);
            if(a != null){
                ctx.json(a);
                ctx.status(200);
            }
            else {
                ctx.status(404);
            }
        }, Role.ANYONE);


        app.get("/accounts/{id}", ctx -> {
            log.info("received /accounts{id} request");
            int id = Integer.parseInt(ctx.pathParam("id"));
            Account a = accountService.getAccountById(id);
            if(a != null){
                ctx.json(a);
                ctx.status(200);
            }
            else {
                ctx.status(404);
            }

        }, Role.EMPLOYEE);

        app.get("/accounts/username/{username}", ctx -> {
            log.info("received /accounts/username/{username} request");
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
            log.info("received /accounts/new request");
           AccountDTO accountDTO = ctx.bodyAsClass(AccountDTO.class);
            if (accountService.addAccount(accountDTO)) {
                ctx.status(201);
            } else {
                ctx.status(400);
            }
        }, Role.ANYONE);

        app.put("/accounts/update", ctx -> {
            log.info("received /accounts/update request");
            AccountDTO accountDTO = ctx.bodyAsClass(AccountDTO.class);
            if(accountService.updateAccount(accountDTO)){
                ctx.status(202);
            }
            else {
                ctx.status(400);
            }
        }, Role.EMPLOYEE);

        app.delete("accounts/delete/{id}", ctx -> {
            log.info("received /accounts/delete/{id} request");
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
