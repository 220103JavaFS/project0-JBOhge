package com.revature;

import io.javalin.Javalin;

public class Application {
    public static void main(String[] args) {
        JDBCPostgreSQLConnection connection = new JDBCPostgreSQLConnection();
        connection.connect();

        Javalin app = Javalin.create().start(7000);
        app.get("/", ctx -> ctx.result("Hello from Springboot-lite"));
    }
}
