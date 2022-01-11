package com.revature.repository;

import com.revature.JDBCPostgreSQLConnection;
import com.revature.model.Account;
import org.postgresql.jdbc.PgResultSet;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class AccountDAO {

        private static Connection connection;

        public AccountDAO(){
                connection = JDBCPostgreSQLConnection.getConnection();
        }

        public ArrayList<Account> getAccounts(){
                ArrayList<Account> accounts = new ArrayList<>();

                try{
                        PgResultSet resultSet = (PgResultSet) connection.createStatement().executeQuery("SELECT * FROM ACCOUNT");
                        while(resultSet.next()){
                                int id = resultSet.getInt("account_id");
                                String firstName = resultSet.getString("first_name");
                                String lastName = resultSet.getString("last_name");
                                String username = resultSet.getString("username");
                                String password = resultSet.getString("password");
                                int accessLevel = resultSet.getInt("access_level");

                                Account a = new Account(id, firstName, lastName, username, password, accessLevel);
                                accounts.add(a);
                        }
                }
                catch (SQLException e){
                        e.printStackTrace();
                }
                return accounts;
        }

}
