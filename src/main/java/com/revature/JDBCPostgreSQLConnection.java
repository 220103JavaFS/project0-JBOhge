package com.revature;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCPostgreSQLConnection {

    private static String url = "jdbc:postgresql://localhost:5432/proj0";
    private static String url2 = "jdbc:postgresql://javafs220103ohgejb.ccidemni0rly.us-east-2.rds.amazonaws.com:5432/postgres";

    private static String userName = System.getenv("SQLUsername");
    private static String password = System.getenv("SQLPassword");

    public static Connection getConnection() throws SQLException{

            try {
                Class.forName(("org.postgresql.Driver"));

            }catch (ClassNotFoundException e) {
                e.printStackTrace();
            }


           return DriverManager.getConnection(url2, userName, password);
    }

}
