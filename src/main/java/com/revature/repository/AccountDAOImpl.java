package com.revature.repository;

import com.revature.JDBCPostgreSQLConnection;
import com.revature.model.Account;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class AccountDAOImpl implements AccountDAO{


        public AccountDAOImpl(){
        }

        @Override
        public boolean addAccount(Account a) {
                try(Connection connection = JDBCPostgreSQLConnection.getConnection()){
                        String sql = "INSERT INTO account (first_name, last_name, username, password, access_level) VALUES (?,?,?,?,?);";
                        PreparedStatement statement = connection.prepareStatement(sql);

                        int count = 1;
                        statement.setString(count++, a.getFirstName());
                        statement.setString(count++, a.getLastName());
                        statement.setString(count++, a.getUsername());
                        statement.setString(count++, a.getPassword());
                        statement.setInt(count++, a.getAccessLevel());

                        statement.execute();

                        return true;

                }
                catch (SQLException e){
                        e.printStackTrace();
                }

                return false;
        }

        @Override
        public ArrayList<Account> getAccounts(){
                try(Connection connection = JDBCPostgreSQLConnection.getConnection()){
                        ArrayList<Account> accounts = new ArrayList<>();
                        ResultSet resultSet = connection.createStatement().executeQuery("SELECT * FROM account");
                        while(resultSet.next()){
                                accounts.add(createAccountObj(resultSet));
                        }
                        return accounts;
                }
                catch (SQLException e){
                        e.printStackTrace();
                }
                return new ArrayList<Account>();
        }

        @Override
        public Account getAccountById(int id) {
                try(Connection connection = JDBCPostgreSQLConnection.getConnection()) {
                        String sql = "SELECT * FROM account WHERE account_id = ?;";
                        PreparedStatement statement = connection.prepareStatement(sql);
                        statement.setInt(1, id);

                        ResultSet resultSet = statement.executeQuery();
                        if(resultSet.next()){
                                return createAccountObj(resultSet);
                        }
                }
                catch (SQLException e){
                        e.printStackTrace();
                }
                return null;
        }

        @Override
        public Account getAccountByUsername(String username){
                try(Connection connection = JDBCPostgreSQLConnection.getConnection()) {
                        String sql = "SELECT * FROM account WHERE username = ?;";
                        PreparedStatement statement = connection.prepareStatement(sql);
                        statement.setString(1, username);

                        ResultSet resultSet = statement.executeQuery();
                        if(resultSet.next()){
                                return createAccountObj(resultSet);
                        }
                }
                catch (SQLException e){
                        e.printStackTrace();
                }
                return null;
        }

        @Override
        public Account getAccountByUsernamePassword(String username, String password) {
                try(Connection connection = JDBCPostgreSQLConnection.getConnection()) {
                        String sql = "SELECT * FROM account WHERE username = ? AND password = ?;";
                        PreparedStatement statement = connection.prepareStatement(sql);
                        statement.setString(1, username);
                        statement.setString(2, password);

                        ResultSet resultSet = statement.executeQuery();
                        if(resultSet.next()){
                                return createAccountObj(resultSet);
                        }
                }
                catch (SQLException e){
                        e.printStackTrace();
                }
                return null;
        }

        @Override
        public boolean updateAccount(Account a) {
                //TODO:
                return false;
        }

        @Override
        public boolean deleteAccount(int id) {
                //TODO:
                return false;
        }

        @Override
        public boolean deleteAccount(String username) {
                //TODO:
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
}
