package com.revature;

import io.javalin.core.security.RouteRole;

public enum Role implements RouteRole {
    ANYONE, EMPLOYEE, ADMIN;
}
