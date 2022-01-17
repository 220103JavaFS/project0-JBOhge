package com.revature;

import com.revature.controller.AccountController;
import com.revature.controller.Controller;
import com.revature.controller.LoginController;
import com.revature.model.Account;
import io.javalin.Javalin;
import io.javalin.http.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Application {

    private static Logger log = LoggerFactory.getLogger(Application.class);
    private static Javalin app;

    public static void main(String[] args) {

        log.info("Application Starting");


        app = Javalin.create(config -> {
            config.accessManager((handler, ctx, routeRoles) -> {


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
        Account account = null;
        try{
            account = (Account) ctx.req.getSession(false).getAttribute("account");
        }
        catch (Exception e){
            //attribute "account" didn't exist or is not an Account object
        }

        //Default Access level ANYONE = 1
        int accessLevel = 1;
        if(account != null){
            accessLevel = account.getAccessLevel();
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
