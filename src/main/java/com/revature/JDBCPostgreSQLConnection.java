package com.revature;

import org.postgresql.jdbc.PgResultSet;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JDBCPostgreSQLConnection {

    private static Connection connection;
    private static String url = "jdbc:postgresql://localhost:5432/proj0";
    private static String userName = "postgres";
    private static String password = "password1";

    public static Connection getConnection(){

            if(connection == null){
                try {
                    connection = DriverManager.getConnection(url, userName, password);
                }
                catch (SQLException e){
                    e.printStackTrace();
                }
            }
            return connection;

    }

}
