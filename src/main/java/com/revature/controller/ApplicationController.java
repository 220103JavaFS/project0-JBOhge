package com.revature.controller;

import com.revature.Role;
import com.revature.model.Application;
import com.revature.service.ApplicationService;
import com.revature.service.BankAccountService;
import io.javalin.Javalin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

public class ApplicationController extends Controller {

    ApplicationService applicationService = new ApplicationService();
    BankAccountService bankAccountService = new BankAccountService();
    private static Logger log = LoggerFactory.getLogger(com.revature.Application.class);

    @Override
    public void addRoutes(Javalin app) {

        //get all applications
        app.get("/applications", ctx -> {
            log.info("received /applications request");
            ArrayList<Application> appList = applicationService.getApplications();
            if(appList == null){
                ctx.status(401);
            }
            else{
                ctx.json(appList);
                ctx.status(200);
            }
        }, Role.EMPLOYEE);

        //get my applications
        app.get("/applications/myapplications", ctx -> {
            log.info("received /application/myapplications request");
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


        //get the application with this id
        app.get("/applications/id/{id}", ctx -> {
            log.info("received /applications/{id} request");
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


        //approve the application with this id
        app.post("/applications/approve/{id}", ctx -> {
            log.info("received /applications/approve/{id} request");
            int applicationId = Integer.parseInt(ctx.pathParam("id"));
            //create new bank account
            if(bankAccountService.createBankAccount(applicationId)){
                ctx.status(201);
            }
            else {
                ctx.status(400);
            }
        }, Role.EMPLOYEE);


        //create a new application
        app.post("/applications/new", ctx -> {
            log.info("received /applications/new request");
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


        //delete the application with the provided id
        app.delete("/applications/delete/{id}", ctx -> {
            log.info("received /applications/delete{id} request");
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
