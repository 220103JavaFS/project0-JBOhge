package com.revature.repository;

import com.revature.JDBCPostgreSQLConnection;
import com.revature.model.Account;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

//TODO: Make AccountDAO and interface and this class AccountDAOimpl
public class AccountDAO {


        //TODO: replace all uses of this with try w/ Resources catch block
        private static Connection connection;

        public AccountDAO(){
                connection = JDBCPostgreSQLConnection.getConnection();
        }

        public ArrayList<Account> getAccounts(){
                ArrayList<Account> accounts = new ArrayList<>();

                try{
                        ResultSet resultSet = connection.createStatement().executeQuery("SELECT * FROM account");
                        while(resultSet.next()){
                                accounts.add(createAccountObj(resultSet));
                        }
                }
                catch (SQLException e){
                        e.printStackTrace();
                }
                return accounts;
        }

        public Account getAccountById(int id) {
                try {
                        //TODO: Use preparedStatement to sanitize and prevent SQL commands from user
                        ResultSet resultSet = connection.createStatement().executeQuery("SELECT * FROM account WHERE account_id =" + id);
                        if(resultSet.next()){
                                return createAccountObj(resultSet);
                        }
                }
                catch (SQLException e){
                        e.printStackTrace();
                }
                return null;
        }


        public boolean addAccount(Account a) {
                StringBuilder sb = new StringBuilder("INSERT INTO account (first_name, last_name, username, password, access_level) VALUES ");
                sb.append("'" + a.getFirstName() + "',");
                sb.append("'" + a.getLastName() + "',");
                sb.append("'" + a.getUsername() + "',");
                sb.append("'" + a.getPassword() + "',");
                sb.append("'" + a.getAccessLevel() + "')");
                try {
                        connection.createStatement().executeUpdate(sb.toString());
                        return true;
                }
                catch (SQLException e){
                        e.printStackTrace();
                }

                return false;
        }

        //helper method to create an account object from the ResultSet object representing the SQL query
        private static Account createAccountObj(ResultSet resultSet) throws SQLException {
                int accountId = resultSet.getInt("account_id");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                String username = resultSet.getString("username");
                String password = resultSet.getString("password");
                int accessLevel = resultSet.getInt("access_level");
                return new Account(accountId, firstName, lastName, username, password, accessLevel);
        }

        public static Account getAccountByUsernamePassword(String username, String password) {
                try {
                        ResultSet resultSet = connection.createStatement().executeQuery("SELECT * FROM account WHERE username ='" + (username) + "' AND password ='" + (password) +"'");
                        if(resultSet.next()){
                                return createAccountObj(resultSet);
                        }
                }
                catch (SQLException e){
                        e.printStackTrace();
                }
                return null;
        }
}
