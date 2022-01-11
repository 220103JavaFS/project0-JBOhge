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

//            connection.createStatement().executeUpdate("INSERT INTO ACCOUNT (accountId, name, balance) VALUES (3, 'Jon', 300);");
//            PgResultSet resultSet = (PgResultSet) connection.createStatement().executeQuery("SELECT * FROM ACCOUNT");
//            resultSet.next();
//            System.out.println(resultSet.getArray("first_name"));


    }

}
