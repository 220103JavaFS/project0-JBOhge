package com.revature;

import com.revature.controller.AccountController;
import com.revature.controller.Controller;
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
                if (userRole.equals(Role.ADMIN)) {
                    handler.handle(ctx);
                }
                else if(userRole.equals(Role.EMPLOYEE) && routeRoles.contains(Role.EMPLOYEE) || routeRoles.contains(Role.ANYONE)){
                    handler.handle(ctx);
                }
                else if(userRole.equals(Role.ANYONE) && routeRoles.contains(Role.ANYONE)){
                    handler.handle(ctx);
                }
                else {
                    ctx.status(401).result("Unauthorized");
                }
            });

        });

        //Test route
        app.get("/", ctx -> ctx.result("Hello from Springboot-lite"));

        //Add all Controller routes
        configure(new AccountController());

        //Start Javalin on Port 7000
        app.start(7000);

    }

    private static Role getUserRole(Context ctx) {
        int roleNum = Integer.parseInt(ctx.header("Role"));
        switch (roleNum){
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
