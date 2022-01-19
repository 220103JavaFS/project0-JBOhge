package com.revature.repository;

import com.revature.JDBCPostgreSQLConnection;
import com.revature.model.Application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ApplicationDAOImpl implements ApplicationDAO{
    @Override
    public boolean addApplication(Application application) {
        try(Connection connection = JDBCPostgreSQLConnection.getConnection()){
            String sql = "INSERT INTO application (account_id, credit_score, debt) VALUES (?,?,?);";
            PreparedStatement statement = connection.prepareStatement(sql);

            int count = 1;
            statement.setInt(count++, application.getAccountId());
            statement.setInt(count++, application.getCreditScore());
            statement.setInt(count++, application.getDebt());


            statement.execute();

            return true;

        }
        catch (SQLException e){
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public ArrayList<Application> getApplications() {
        try(Connection connection = JDBCPostgreSQLConnection.getConnection()){
            ArrayList<Application> applications = new ArrayList<>();
            ResultSet resultSet = connection.createStatement().executeQuery("SELECT * FROM application");
            while(resultSet.next()){
                applications.add(createAccountObj(resultSet));
            }
            return applications;
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Application getApplicationById(int applicationId) {
        try(Connection connection = JDBCPostgreSQLConnection.getConnection()) {
            String sql = "SELECT * FROM application WHERE application_id = ?;";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, applicationId);

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
    public boolean deleteApplication(int applicationId) {
        try(Connection connection = JDBCPostgreSQLConnection.getConnection()){
            //NOTE: Where you try to delete on row with an account_id that doesn't exist still don't throw SQL error
            String sql = "DELETE FROM application WHERE application_id = ? ;";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, applicationId);
            statement.execute();

            return true;

        }
        catch (SQLException e){
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public ArrayList<Application> getApplicationByAccountId(int accountId) {
        try(Connection connection = JDBCPostgreSQLConnection.getConnection()){
            ArrayList<Application> applications = new ArrayList<>();
            String sql = "SELECT * FROM application WHERE account_id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, accountId);
            ResultSet resultSet = statement.executeQuery();

            while(resultSet.next()){
                applications.add(createAccountObj(resultSet));
            }
            return applications;
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    private static Application createAccountObj(ResultSet resultSet) throws SQLException {
        int applicationId = resultSet.getInt("application_id");
        int accountId = resultSet.getInt("account_id");
        int creditScore = resultSet.getInt("credit_score");
        int debt = resultSet.getInt("debt");

        return new Application(applicationId, accountId, creditScore, debt);
    }
}
