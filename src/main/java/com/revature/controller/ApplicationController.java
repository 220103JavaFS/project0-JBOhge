package com.revature.controller;

import com.revature.Role;
import com.revature.model.Application;
import com.revature.service.ApplicationService;
import com.revature.service.BankAccountService;
import io.javalin.Javalin;

import java.util.ArrayList;

public class ApplicationController extends Controller {

    ApplicationService applicationService = new ApplicationService();
    BankAccountService bankAccountService = new BankAccountService();

    @Override
    public void addRoutes(Javalin app) {
        app.get("/Applications", ctx -> {
            ArrayList<Application> appList = applicationService.getApplications();
            if(appList == null){
                ctx.status(401);
            }
            else{
                ctx.json(appList);
                ctx.status(200);
            }
        }, Role.EMPLOYEE);


        app.get("/Applications/myapplication", ctx -> {
            int accountId = 0;
            try{
                accountId = (Integer)ctx.req.getSession(false).getAttribute("accountId");
            } catch (ClassCastException e){
                e.printStackTrace();
                ctx.status(400);
                return;
            }

            ArrayList<Application> appList = applicationService.getApplicationsByAccountId(accountId);
            if(appList == null){
                ctx.json(400);
            }
            else {
                ctx.json(appList);
                ctx.status(200);
            }

        },Role.ANYONE);

        app.get("/Applications/{id}", ctx -> {
            int applicationId = Integer.parseInt(ctx.pathParam("id"));
            Application application = applicationService.getApplicationById(applicationId);

            if(application != null){
                ctx.json(application);
                ctx.status(200);
            }
            else {
                ctx.status(404);
            }
        }, Role.EMPLOYEE);

        app.post("/Applications/approve/{id}", ctx -> {
            int applicationId = Integer.parseInt(ctx.pathParam("id"));
            //create new bank account
            if(bankAccountService.createBankAccount(applicationId)){
                ctx.status(201);
            }
            else {
                ctx.status(400);
            }
        }, Role.EMPLOYEE);

        app.post("/Applications/new", ctx -> {
            Application application = ctx.bodyAsClass(Application.class);
            try{
                application.setAccountId((Integer)ctx.req.getSession(false).getAttribute("accountId"));
            } catch (ClassCastException e){
                e.printStackTrace();
            }

            if(applicationService.addApplication(application)){
                ctx.status(201);
            }
            else {
                ctx.status(400);
            }

        }, Role.ANYONE);

        app.delete("/Applications/delete/{id}", ctx -> {
            int applicationId = Integer.parseInt(ctx.pathParam("id"));
            if(applicationService.deleteApplication(applicationId)){
                ctx.status(200);
            }
            else {
                ctx.status(400);
            }
        }, Role.ADMIN);
    }
}
