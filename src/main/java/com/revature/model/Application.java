package com.revature.model;

public class Application {

    private int applicationId;
    private int accountId;
    private int creditScore;
    private int debt;

    public Application(int applicationId, int accountId, int creditScore, int debt) {
        this.applicationId = applicationId;
        this.accountId = accountId;
        this.creditScore = creditScore;
        this.debt = debt;
    }

    public int getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(int applicationId) {
        this.applicationId = applicationId;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public int getCreditScore() {
        return creditScore;
    }

    public void setCreditScore(int creditScore) {
        this.creditScore = creditScore;
    }

    public int getDebt() {
        return debt;
    }

    public void setDebt(int debt) {
        this.debt = debt;
    }
}
