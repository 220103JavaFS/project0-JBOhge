package com.revature.repository;

import com.revature.JDBCPostgreSQLConnection;
import com.revature.model.Account;
import com.revature.model.AllAccount;
import com.revature.model.BankAccount;

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
                        statement.setBytes(count++, a.getHash());
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

        public AllAccount getAllAccountById(int accountId){
                try(Connection connection = JDBCPostgreSQLConnection.getConnection()) {
                        AllAccount allAccount = new AllAccount();
                        Account account = this.getAccountById(accountId);
                        if(account == null){
                              return null;
                        }
                        allAccount.setAccount(account);

                        //Get Bank accounts
                        String sql = "SELECT * FROM bankaccount WHERE account_id = ?;";
                        PreparedStatement statement = connection.prepareStatement(sql);
                        statement.setInt(1, accountId);
                        ResultSet resultSet = statement.executeQuery();

                        ArrayList<BankAccount> bankAccountList = new ArrayList<>();

                        while(resultSet.next()){
                                int bankAccountId = resultSet.getInt("bankaccount_id");
                                double balance = resultSet.getDouble("balance");
                                BankAccount bankAccount = new BankAccount(bankAccountId, accountId, balance);
                                bankAccountList.add(bankAccount);
                        }

                        allAccount.setBankAccountList(bankAccountList);

                        return allAccount;

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
        public boolean updateAccount(Account a) {
                try(Connection connection = JDBCPostgreSQLConnection.getConnection()){
                        String sql = "UPDATE account SET first_name = ? , last_name = ? , username = ? , password = ? WHERE username = ?;";
                        PreparedStatement statement = connection.prepareStatement(sql);

                        int count = 1;
                        statement.setString(count++, a.getFirstName());
                        statement.setString(count++, a.getLastName());
                        statement.setString(count++, a.getUsername());
                        statement.setBytes(count++, a.getHash());
                        statement.setString(count++, a.getUsername());

                        statement.execute();

                        return true;

                }
                catch (SQLException e){
                        e.printStackTrace();
                }

                return false;
        }

        @Override
        public boolean deleteAccount(int id) {
                try(Connection connection = JDBCPostgreSQLConnection.getConnection()){
                        //NOTE: Where you try to delete on row with an account_id that doesn't exist still don't throw SQL error
                        String sql = "DELETE FROM account WHERE account_id = ? ;";
                        PreparedStatement statement = connection.prepareStatement(sql);
                        statement.setInt(1, id);
                        statement.execute();

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
                byte[] hash = resultSet.getBytes("password");
                int accessLevel = resultSet.getInt("access_level");

                return new Account(accountId, firstName, lastName, username, hash, accessLevel);
        }
}
