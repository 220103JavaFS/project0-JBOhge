package com.revature;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCPostgreSQLConnection {
    private String url = "jdbc:postgresql://localhost:5432/postgres";
    private String userName = "postgres";
    private String password = "password1";

    public void connect(){
        try {
            Connection connection = DriverManager.getConnection(url, userName, password);
            System.out.println(connection.createStatement().executeQuery("SELECT VERSION()"));


        }
        catch (SQLException e){
            System.out.println("yeah idk what happen bro try again");
        }
    }

}
