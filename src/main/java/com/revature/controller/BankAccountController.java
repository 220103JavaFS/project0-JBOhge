package com.revature.controller;

import com.revature.Role;
import com.revature.model.Application;
import com.revature.model.BankAccount;
import com.revature.service.BankAccountService;
import io.javalin.Javalin;

import java.util.ArrayList;

public class BankAccountController extends Controller {

    @Override
    public void addRoutes(Javalin app) {

        BankAccountService bankAccountService = new BankAccountService();

        app.get("/Bankaccounts", ctx -> {
            ArrayList<BankAccount> bankList = bankAccountService.getBankAccounts();
            if(bankList == null){
                ctx.status(401);
            }
            else{
                ctx.json(bankList);
                ctx.status(200);
            }
        }, Role.EMPLOYEE);

        app.get("/Bankaccounts/{id}", ctx -> {
            int bankAccountId = Integer.parseInt(ctx.pathParam("id"));
            BankAccount bankAccount = bankAccountService.getBankAccountById(bankAccountId);
            if(bankAccount == null){
                ctx.status(401);
            }
            else{
                ctx.json(bankAccount);
                ctx.status(200);
            }
        }, Role.EMPLOYEE);

        app.put("/Bankaccounts/withdraw", ctx -> {

        });

        app.put("/Bankaccounts/deposit", ctx -> {

        });

        app.put("/Bankaccount/transfer", ctx -> {

        });

        app.post("/Bankaccounts/new", ctx -> {

        });

        app.delete("Bankaccount/delete/{id}", ctx -> {
            int bankAccountId = Integer.parseInt(ctx.pathParam("id"));
            if(bankAccountService.deleteBankAccount(bankAccountId)){
                ctx.status(200);
            }
            else {
                ctx.status(400);
            }

        }, Role.ADMIN);
    }
}
