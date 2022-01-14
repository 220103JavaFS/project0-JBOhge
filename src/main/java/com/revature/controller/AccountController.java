package com.revature.controller;

import com.revature.Role;
import com.revature.model.Account;
import com.revature.service.AccountService;
import io.javalin.Javalin;
import io.javalin.core.security.RouteRole;


import java.util.ArrayList;
import java.util.List;

public class AccountController extends Controller{

    private AccountService accountService = new AccountService();

    @Override
    public void addRoutes(Javalin app) {

        List<RouteRole> roles = new ArrayList<>();
        roles.add(Role.EMPLOYEE);

        //Get All accounts
        app.get("/accounts", ctx -> {

            if(ctx.req.getSession(false)!=null){
                ArrayList<Account> accounts = accountService.getAccounts();
                ctx.json(accounts);
                ctx.status(200);
            }
            else {
                ctx.status(401);
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

        }, Role.EMPLOYEE);

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
