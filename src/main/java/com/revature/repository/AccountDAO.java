package com.revature.repository;

import com.revature.JDBCPostgreSQLConnection;
import com.revature.model.Account;
import org.postgresql.util.PSQLException;

import java.sql.Connection;
import java.sql.ResultSet;
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
                        ResultSet resultSet = connection.createStatement().executeQuery("SELECT * FROM ACCOUNT");
                        while(resultSet.next()){
                                accounts.add(createAccount(resultSet));
                        }
                }
                catch (SQLException e){
                        e.printStackTrace();
                }
                return accounts;
        }

        public Account getAccountById(int id) {
                try {
                        ResultSet resultSet = connection.createStatement().executeQuery("SELECT * FROM ACCOUNT WHERE ACCOUNT_ID =" + id);
                        if(resultSet.next()){
                                return createAccount(resultSet);
                        }
                }
                catch (SQLException e){
                        e.printStackTrace();
                }
                return null;
        }

        private static Account createAccount(ResultSet resultSet) throws SQLException {
                int accountId = resultSet.getInt("account_id");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                String username = resultSet.getString("username");
                String password = resultSet.getString("password");
                int accessLevel = resultSet.getInt("access_level");
                return new Account(accountId, firstName, lastName, username, password, accessLevel);
        }

        public boolean addAccount(Account a) {
                StringBuilder sb = new StringBuilder("INSERT INTO ACCOUNT VALUES ");
                sb.append("('" + a.getAccountId() + "',");
                sb.append("'" + a.getFirstName() + "',");
                sb.append("'" + a.getLastName() + "',");
                sb.append("'" + a.getUsername() + "',");
                sb.append("'" + a.getPassword() + "',");
                sb.append("'" + a.getAccessLevel() + "')");
                try {
                        int result = connection.createStatement().executeUpdate(sb.toString());
                        return result > 0;
                }
                catch (SQLException e){
                        e.printStackTrace();
                }

                return false;
        }
}
