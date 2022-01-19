package com.revature.repository;

import com.revature.JDBCPostgreSQLConnection;
import com.revature.model.Application;
import com.revature.model.BankAccount;
import com.revature.model.Transaction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class BankAccountDAOImpl implements BankAccountDAO {

    /*
      gets every bankaccount records in the database
     */
    @Override
    public ArrayList<BankAccount> getBankAccounts() {
        try(Connection connection = JDBCPostgreSQLConnection.getConnection()){
            ArrayList<BankAccount> bankAccounts = new ArrayList<>();
            ResultSet resultSet = connection.createStatement().executeQuery("SELECT * FROM bankaccount");
            while(resultSet.next()){
                bankAccounts.add(createAccountObj(resultSet));
            }
            return bankAccounts;
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    /*
        gets the bankaccount with the bankaccount_id equal to parameter bankAccountId
     */
    @Override
    public BankAccount getBankAccountById(int bankAccountId) {
        try(Connection connection = JDBCPostgreSQLConnection.getConnection()) {
            String sql = "SELECT * FROM bankaccount WHERE bankaccount_id = ?;";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, bankAccountId);

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

    /*
        Creates a new bankaccount entry in the database
     */
    @Override
    public boolean createBankAccount(int applicationId) {
        ApplicationDAO applicationDAO = new ApplicationDAOImpl();
        Application application = applicationDAO.getApplicationById(applicationId);
        if(application == null){
            //Application with given applicationId was not found
            return false;
        }
        //the accountId to associate with the new bankaccount
        int accountId = application.getAccountId();

        try(Connection connection = JDBCPostgreSQLConnection.getConnection()){
            String sql = "INSERT INTO bankaccount (account_id, balance) VALUES (?,?);";
            PreparedStatement statement = connection.prepareStatement(sql);

            int count = 1;
            statement.setInt(count++, accountId);
            statement.setDouble(count++, 0.0);
            statement.execute();


            return true;

        }
        catch (SQLException e){
            e.printStackTrace();
        }

        return false;



    }

    /*
        deletes a bankaccount from the database
     */
    @Override
    public boolean deleteBankAccount(int bankAccountId) {
        try(Connection connection = JDBCPostgreSQLConnection.getConnection()){
            //NOTE: Where you try to delete on row with an bankaccount_id that doesn't exist still don't throw SQL error
            String sql = "DELETE FROM bankaccount WHERE bankaccount_id = ? ;";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, bankAccountId);
            statement.execute();

            return true;

        }
        catch (SQLException e){
            e.printStackTrace();
        }

        return false;
    }

    /*
        Withdraws an bankamount from an account
     */
    @Override
    public boolean withdrawFromBankAccount(Transaction transaction) {
        if(transaction.getTransferAmount() <= 0){
            return false;
        }
        BankAccount bankAccount = this.getBankAccountById(transaction.getOriginatorBankAccountId());

        //if bank account doesn't exist or the balance is less than the amount to be withdrawn or the transaction originator's accountId is different from this bankaccounts accountId
        if(bankAccount == null || bankAccount.getBalance() < transaction.getTransferAmount() || transaction.getOriginatorAccountId() != bankAccount.getAccountId()){
            return false;
        }
        bankAccount.setBalance(bankAccount.getBalance()-transaction.getTransferAmount());

        return this.updateBankAccount(bankAccount);
    }

    /*
        Deposits an bankamount from into an bankaccount
     */
    @Override
    public boolean depositToBankAccount(Transaction transaction) {
        if(transaction.getTransferAmount() <= 0){
            return false;
        }
        BankAccount bankAccount = this.getBankAccountById(transaction.getOriginatorBankAccountId());

        //if bank account doesn't exist or the transaction originator's accountId is different from this bankaccounts accountId
        if(bankAccount == null || transaction.getOriginatorAccountId() != bankAccount.getAccountId()){
            return false;
        }

        bankAccount.setBalance(bankAccount.getBalance()+transaction.getTransferAmount());

        return this.updateBankAccount(bankAccount);
    }

    /*
        Transfers an amount from one bankaccount to another bankaccount
     */
    @Override
    public boolean transferToBankAccount(Transaction transaction) {
        if(transaction.getTransferAmount() <= 0){
            return false;
        }
        BankAccount bankAccount = this.getBankAccountById(transaction.getOriginatorBankAccountId());
        BankAccount bankAccount2 = this.getBankAccountById(transaction.getResponderBankAccountId());

        //if bank account doesn't exist or the balance is less than the amount to be withdrawn or the transaction originator's accountId is different from this bankaccounts accountId
        if(bankAccount == null || bankAccount2 == null || bankAccount.getBalance() < transaction.getTransferAmount() || transaction.getOriginatorAccountId() != bankAccount.getAccountId()){
            return false;
        }


        try(Connection connection = JDBCPostgreSQLConnection.getConnection()){
            String sql = "CALL transfer(?, ?, ?);";
            PreparedStatement statement = connection.prepareStatement(sql);

            int count = 1;
            statement.setInt(count++, bankAccount.getBankAccountId());
            statement.setInt(count++, bankAccount2.getBankAccountId());
            statement.setDouble(count++, transaction.getTransferAmount());

            statement.execute();

            return true;

        }
        catch (SQLException e){
            e.printStackTrace();
        }


        return false;

    }
    /*
        Updates the bankaccount
     */
    public boolean updateBankAccount(BankAccount bankAccount){
        try(Connection connection = JDBCPostgreSQLConnection.getConnection()){
            String sql = "UPDATE bankaccount SET balance = ? WHERE bankaccount_id = ?;";
            PreparedStatement statement = connection.prepareStatement(sql);

            int count = 1;
            statement.setDouble(count++, bankAccount.getBalance());
            statement.setInt(count++, bankAccount.getBankAccountId());

            statement.execute();

            return true;

        }
        catch (SQLException e){
            e.printStackTrace();
        }

        return false;
    }


    private static BankAccount createAccountObj(ResultSet resultSet) throws SQLException {
        int bankAccountId = resultSet.getInt("bankaccount_id");
        int accountId = resultSet.getInt("account_id");
        double balance = resultSet.getDouble("balance");

        return new BankAccount(bankAccountId, accountId, balance);
    }
}
