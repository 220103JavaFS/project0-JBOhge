package com.revature.controller;

import com.revature.Role;
import com.revature.model.Application;
import com.revature.model.BankAccount;
import com.revature.model.Transaction;
import com.revature.service.BankAccountService;
import io.javalin.Javalin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

public class BankAccountController extends Controller {

    private static Logger log = LoggerFactory.getLogger(com.revature.Application.class);
    BankAccountService bankAccountService = new BankAccountService();


    @Override
    public void addRoutes(Javalin app) {


        //get all the bankaccounts
        app.get("/bankaccounts", ctx -> {

            log.info("received /bankaccounts request");
            ArrayList<BankAccount> bankList = bankAccountService.getBankAccounts();
            if (bankList == null) {
                ctx.status(500);
            } else {
                ctx.json(bankList);
                ctx.status(200);
            }
        }, Role.EMPLOYEE);


        //get the bankaccount with the id of the provided id
        app.get("/bankaccounts/id/{id}", ctx -> {

            log.info("received /bankaccounts/{id} request");
            int bankAccountId = Integer.parseInt(ctx.pathParam("id"));
            BankAccount bankAccount = bankAccountService.getBankAccountById(bankAccountId);
            if (bankAccount == null) {
                ctx.status(401);
            } else {
                ctx.json(bankAccount);
                ctx.status(200);
            }
        }, Role.EMPLOYEE);


        //withdraw an amount of money from a bankaccount
        app.put("/bankaccounts/withdraw", ctx -> {

            log.info("received /bankaccounts/withdraw request");
            int accountId = 0;
            try {
                accountId = (Integer) ctx.req.getSession(false).getAttribute("accountId");

            } catch (ClassCastException e) {
                e.printStackTrace();
                ctx.status(400);
                return;
            }
            Transaction transaction = ctx.bodyAsClass(Transaction.class);
            transaction.setOriginatorAccountId(accountId);


            if (bankAccountService.withdrawFromBankAccount(transaction)) {
                ctx.status(200);
            } else {
                ctx.status(400);
            }
        }, Role.ANYONE);


        //deposity an ammount of money from a bankaccount
        app.put("/bankaccounts/deposit", ctx -> {

            log.info("received /bankaccounts/deposit request");
            int accountId = 0;
            try {
                accountId = (Integer) ctx.req.getSession(false).getAttribute("accountId");
            } catch (ClassCastException e) {
                e.printStackTrace();
                ctx.status(400);
                return;
            }
            Transaction transaction = ctx.bodyAsClass(Transaction.class);
            transaction.setOriginatorAccountId(accountId);


            if (bankAccountService.depositToBankAccount(transaction)) {
                ctx.status(200);
            } else {
                ctx.status(400);
            }
        }, Role.ANYONE);


        //transfer an amount of month from one bankaccount to another
        app.put("/bankaccounts/transfer", ctx -> {

            log.info("received /bankaccounts/transfer request");
            int accountId = 0;
            try {
                accountId = (Integer) ctx.req.getSession(false).getAttribute("accountId");
            } catch (ClassCastException e) {
                e.printStackTrace();
                ctx.status(400);
                return;
            }
            Transaction transaction = ctx.bodyAsClass(Transaction.class);
            transaction.setOriginatorAccountId(accountId);


            if (bankAccountService.transferToBankAccount(transaction)) {
                ctx.status(200);
            } else {
                ctx.status(400);
            }

        }, Role.ANYONE);


        //update a bankaccount
        app.put("/bankaccounts/update", ctx -> {

            log.info("received /bankaccounts/update request");
            BankAccount bankAccount = ctx.bodyAsClass(BankAccount.class);

            if (bankAccountService.updateBankAccount(bankAccount)) {
                ctx.status(200);
            } else {
                ctx.status(400);
            }
        }, Role.ADMIN);


        //delete the bankaccount with the given id
        app.delete("bankaccounts/delete/{id}", ctx -> {

            log.info("received /bankaccounts/delete/{id} request");
            int bankAccountId = Integer.parseInt(ctx.pathParam("id"));
            if (bankAccountService.deleteBankAccount(bankAccountId)) {
                ctx.status(200);
            } else {
                ctx.status(400);
            }

        }, Role.ADMIN);
    }
}
