package com.revature.controller;

import com.revature.Role;
import com.revature.model.Account;
import com.revature.model.AccountDTO;
import com.revature.service.LoginService;
import io.javalin.Javalin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoginController extends Controller {

    LoginService loginService = new LoginService();
    private static Logger log = LoggerFactory.getLogger(com.revature.Application.class);

    @Override
    public void addRoutes(Javalin app) {


        app.post("/login", ctx -> {
            log.info("received /login request");
            AccountDTO account = ctx.bodyAsClass(AccountDTO.class);
            Account a  = loginService.login(account.getUsername(), account.getPassword());
            if(a != null){
                ctx.req.getSession();
                ctx.req.getSession(false).setAttribute("account", a);
                ctx.req.getSession(false).setAttribute("accessLevel", a.getAccessLevel());
                ctx.req.getSession(false).setAttribute("accountId", a.getAccountId());
                ctx.status(200);
            }
            else {
                ctx.req.getSession().invalidate();
                ctx.status(400);
            }
        }, Role.ANYONE);

        app.get("/logout", ctx -> {
            log.info("received /logout request");
            ctx.req.getSession().invalidate();
            ctx.status(200);
        }, Role.ANYONE);

    }


}
