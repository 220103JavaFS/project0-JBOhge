package com.revature.controller;

import com.revature.Role;
import com.revature.model.Application;
import com.revature.model.BankAccount;
import com.revature.model.Transaction;
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
                ctx.status(500);
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
            int accountId = 0;
            try{
                accountId = (Integer)ctx.req.getSession(false).getAttribute("accountId");

            } catch (ClassCastException e){
                e.printStackTrace();
                ctx.status(400);
                return;
            }
            Transaction transaction = ctx.bodyAsClass(Transaction.class);
            transaction.setOriginatorAccountId(accountId);



            if(bankAccountService.withdrawFromBankAccount(transaction)){
                ctx.status(200);
            }
            else{
                ctx.status(400);
            }
        }, Role.ANYONE);

        app.put("/Bankaccounts/deposit", ctx -> {
            int accountId = 0;
            try{
                accountId = (Integer)ctx.req.getSession(false).getAttribute("accountId");
            } catch (ClassCastException e){
                e.printStackTrace();
                ctx.status(400);
                return;
            }
            Transaction transaction = ctx.bodyAsClass(Transaction.class);
            transaction.setOriginatorAccountId(accountId);


            if(bankAccountService.depositToBankAccount(transaction)){
                ctx.status(200);
            }
            else{
                ctx.status(400);
            }
        }, Role.ANYONE);

        app.put("/Bankaccounts/transfer", ctx -> {
            int accountId = 0;
            try{
                accountId = (Integer)ctx.req.getSession(false).getAttribute("accountId");
            } catch (ClassCastException e){
                e.printStackTrace();
                ctx.status(400);
                return;
            }
            Transaction transaction = ctx.bodyAsClass(Transaction.class);
            transaction.setOriginatorAccountId(accountId);


            if(bankAccountService.transferToBankAccount(transaction)){
                ctx.status(200);
            }
            else{
                ctx.status(400);
            }

        }, Role.ANYONE);

        app.post("/Bankaccounts/new", ctx -> {
            //Unused new bank accounts are made by approving a bank account application at "/Applications/approve/{id}
        });

        app.delete("Bankaccounts/delete/{id}", ctx -> {
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
