package com.revature;

import com.revature.controller.AccountController;
import com.revature.controller.Controller;
import io.javalin.Javalin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Application {

    private static Logger log = LoggerFactory.getLogger(Application.class);
    private static Javalin app;

    public static void main(String[] args) {

        log.info("Application Starting");


        app = Javalin.create();
        //Test route
        app.get("/", ctx -> ctx.result("Hello from Springboot-lite"));

        //Add all Controller routes
        configure(new AccountController());

        //Start Javalin on Port 7000
        app.start(7000);

    }

    public static void configure(Controller... controllers){
        for(Controller c : controllers){
            c.addRoutes(app);
        }
    }
}
