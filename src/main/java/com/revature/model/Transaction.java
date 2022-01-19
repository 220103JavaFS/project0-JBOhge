package com.revature.model;

public class Transaction {

    private int originatorAccountId;
    private int originatorBankAccountId;
    private int responderBankAccountId;
    private double transferAmount;
    private String type;

    public Transaction() {
    }

    public Transaction(int originatorAccountId, int originatorBankAccountId, int responderBankAccountId, double transferAmount, String type) {
        this.originatorAccountId = originatorAccountId;
        this.originatorBankAccountId = originatorBankAccountId;
        this.responderBankAccountId = responderBankAccountId;
        this.transferAmount = transferAmount;
        this.type = type;
    }

    public int getOriginatorAccountId() {
        return originatorAccountId;
    }

    public void setOriginatorAccountId(int originatorAccountId) {
        this.originatorAccountId = originatorAccountId;
    }

    public int getOriginatorBankAccountId() {
        return originatorBankAccountId;
    }

    public void setOriginatorBankAccountId(int originatorBankAccountId) {
        this.originatorBankAccountId = originatorBankAccountId;
    }

    public int getResponderBankAccountId() {
        return responderBankAccountId;
    }

    public void setResponderBankAccountId(int responderBankAccountId) {
        this.responderBankAccountId = responderBankAccountId;
    }

    public double getTransferAmount() {
        return transferAmount;
    }

    public void setTransferAmount(double transferAmount) {
        this.transferAmount = transferAmount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
