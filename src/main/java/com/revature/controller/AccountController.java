package com.revature.controller;

import com.revature.Role;
import com.revature.model.Account;
import com.revature.service.AccountService;
import io.javalin.Javalin;
import io.javalin.core.security.RouteRole;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AccountController extends Controller{

    private AccountService accountService = new AccountService();

    @Override
    public void addRoutes(Javalin app) {

        List<RouteRole> roles = new ArrayList<>();
        roles.add(Role.EMPLOYEE);

        //Get All accounts
        app.get("/accounts", ctx -> {
            ArrayList<Account> accounts = accountService.getAccounts();
            ctx.json(accounts);
            ctx.status(200);

        }, Role.EMPLOYEE);


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

        }, Role.ADMIN);

        app.post("/accounts/new", ctx -> {
           Account a = ctx.bodyAsClass(Account.class);
            if (accountService.addAccount(a)) {
                ctx.status(201);
            } else {
                ctx.status(400);
            }
        }, Role.ANYONE);




    }
}
