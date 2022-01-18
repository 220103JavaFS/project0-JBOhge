package com.revature.controller;

import com.revature.Role;
import com.revature.model.Application;
import com.revature.service.ApplicationService;
import io.javalin.Javalin;

import java.util.ArrayList;

public class ApplicationController extends Controller {

    ApplicationService applicationService = new ApplicationService();

    @Override
    public void addRoutes(Javalin app) {
        app.get("/Applications", ctx -> {
            ArrayList<Application> appList = applicationService.getApplications();
            if(appList.isEmpty()){
                ctx.status(401);
            }
            else{
                ctx.json(appList);
                ctx.status(200);
            }
        }, Role.EMPLOYEE);

        app.get("/Applications/{id}", ctx -> {
            int applicationId = Integer.parseInt(ctx.pathParam("id"));
            Application application = applicationService.getApplicationById(applicationId);

            if(application != null){
                ctx.json(application);
                ctx.status(200);
            }
            else {
                ctx.status(400);
            }
        }, Role.ANYONE);

        app.put("/Applications/update/{id}", ctx -> {
            int applicationId = Integer.parseInt(ctx.pathParam("id"));
        });

        app.post("/Application/approve/{id}", ctx -> {
            int applicationId = Integer.parseInt(ctx.pathParam("id"));
        });

        app.post("/Applications/new", ctx -> {

        });

        app.delete("/Applications/delete/{id}", ctx -> {
            int applicationId = Integer.parseInt(ctx.pathParam("id"));
        });
    }
}
