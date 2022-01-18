package com.revature.controller;

import com.revature.model.BankAccount;
import io.javalin.Javalin;

public class BankAccountController extends Controller {

    @Override
    public void addRoutes(Javalin app) {

        app.get("/Bankaccounts", ctx -> {

        });

        app.get("/Bankaccounts/{id}", ctx -> {

        });

        app.put("/Bankaccounts/withdraw", ctx -> {

        });

        app.put("/Bankaccounts/deposit", ctx -> {

        });

        app.put("/Bankaccount/transfer", ctx -> {

        });

        app.post("/Bankaccounts/new", ctx -> {

        });

        app.delete("Bankaccount/delete/{id}", ctx -> {

        });
    }
}
