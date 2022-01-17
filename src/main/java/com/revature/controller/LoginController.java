package com.revature.controller;

import com.revature.Role;
import com.revature.model.Account;
import com.revature.model.AccountDTO;
import com.revature.service.LoginService;
import io.javalin.Javalin;

public class LoginController extends Controller {

    LoginService loginService = new LoginService();

    @Override
    public void addRoutes(Javalin app) {


        app.post("/login", ctx -> {
            AccountDTO account = ctx.bodyAsClass(AccountDTO.class);
            Account a  = loginService.login(account.username, account.password);
            if(a != null){
                ctx.req.getSession();
                ctx.req.getSession(false).setAttribute("account", a);
                ctx.status(200);
            }
            else {
                ctx.req.getSession().invalidate();
                ctx.status(401);
            }
        }, Role.ANYONE);

        app.get("/logout", ctx -> {
           ctx.req.getSession().invalidate();
           ctx.status(200);
        }, Role.ANYONE);

    }


}
