package com.revature;

import com.revature.controller.AccountController;
import com.revature.controller.Controller;
import com.revature.controller.LoginController;
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

                //if the user does not have a session deny request unless its for the login or logout or open endpoints
                ArrayList<String> openList = new ArrayList<>();
                openList.add("/login");
                openList.add("/logout");
                openList.add("/accounts/new");


                if(ctx.req.getSession(false)==null && !openList.contains(ctx.path())){
                    ctx.status(401);
                    return;
                }


                Role userRole = getUserRole(ctx);
                //if user is an ADMIN or route is for ANYONE
                if (userRole.equals(Role.ADMIN) || routeRoles.contains(Role.ANYONE)) {
                    handler.handle(ctx);
                }
                //if user is EMPLOYEE and route is for EMPLOYEE
                else if(userRole.equals(Role.EMPLOYEE) && routeRoles.contains(Role.EMPLOYEE)){
                    handler.handle(ctx);
                }
                else {
                    ctx.status(401).result("Unauthorized");
                }
            });

        });

        //Test route
        app.get("/", ctx -> ctx.result("Hello from Springboot-lite"), Role.ANYONE);

        //Add all Controller routes
        configure(new AccountController(), new LoginController());

        //Start Javalin on Port 7000
        app.start(7000);

    }


    //static helper method to get the user's role by looking at the
    private static Role getUserRole(Context ctx) {
        int accessLevel = 1;
        try{
            accessLevel = (Integer) ctx.req.getSession(false).getAttribute("accessLevel");
        }
        catch (Exception e){
            //attribute "accessLevel" didn't exist or is not an Account object
        }

        switch (accessLevel){
            case 2:
                return Role.EMPLOYEE;
            case 3:
                return Role.ADMIN;
            default:
                return Role.ANYONE;
        }
    }

    public static void configure(Controller... controllers){
        for(Controller c : controllers){
            c.addRoutes(app);
        }
    }
}
