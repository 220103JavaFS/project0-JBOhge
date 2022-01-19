package com.revature;

import com.revature.controller.*;
import com.revature.model.Account;
import io.javalin.Javalin;
import io.javalin.http.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

public class Application {

    private static Logger log = LoggerFactory.getLogger(Application.class);
    private static Javalin app;

    public static void main(String[] args) {

        log.info("Application Starting");


        app = Javalin.create(config -> {
            config.accessManager((handler, ctx, routeRoles) -> {

                //GENERAL ACCESS MANAGER

                //if the user does not have a session deny request unless its for the login or logout or open endpoints
                ArrayList<String> openList = new ArrayList<>();
                openList.add("/login");
                openList.add("/logout");
                openList.add("/accounts/new");


                if(ctx.req.getSession(false)==null && !openList.contains(ctx.path())){
                    ctx.status(401);
                    return;
                }

                //CHECKING USERS ACCESS LEVEL
                int accessLevel = 1;
                try{
                    accessLevel = (Integer) ctx.req.getSession(false).getAttribute("accessLevel");
                }
                catch (Exception e){
                    //attribute "accessLevel" didn't exist or is not an Account object
                }

                Role userRole = Role.ANYONE;
                switch (accessLevel){
                    case 2:
                        userRole = Role.EMPLOYEE;
                        break;
                    case 3:
                        userRole = Role.ADMIN;
                        break;
                    default:
                        userRole = Role.ANYONE;
                        break;
                }


                //if user is an ADMIN or route is for ANYONE
                if (userRole.equals(Role.ADMIN) || routeRoles.contains(Role.ANYONE)) {
                    handler.handle(ctx);
                }
                //if user is EMPLOYEE and route is for EMPLOYEE
                else if(userRole.equals(Role.EMPLOYEE) && routeRoles.contains(Role.EMPLOYEE)){
                    handler.handle(ctx);
                }
                else {
                    ctx.status(403).result("Forbidden");
                }
            });

        });

        //Test route
        app.get("/", ctx -> ctx.result("Hello from Springboot-lite"), Role.ANYONE);

        //Add all Controller routes
        configure(new AccountController(), new LoginController(), new ApplicationController(), new BankAccountController());

        //Start Javalin on Port 7000
        app.start(7000);

    }


    public static void configure(Controller... controllers){
        for(Controller c : controllers){
            c.addRoutes(app);
        }
    }
}
