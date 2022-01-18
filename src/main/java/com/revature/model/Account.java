package com.revature.model;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class Account {
    private int accountId;
    private String firstName;
    private String lastName;
    private String username;
    private int accessLevel;
    private byte[] hash;

    public Account() {
    }


    public Account(int accountId, String firstName, String lastName, String username, byte[] hash, int accessLevel) {
        this.accountId = accountId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.hash = hash;
        this.accessLevel = accessLevel;

    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getAccessLevel() {
        return accessLevel;
    }

    public void setAccessLevel(int accessLevel) {
        this.accessLevel = accessLevel;
    }

    public byte[] getHash() {
        return hash;
    }

    public void setHash(byte[] hash) {
        this.hash = hash;
    }

    @Override
    public String toString() {
        return "Account{" +
                "accountId=" + accountId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", username='" + username + '\'' +
                ", accessLevel=" + accessLevel +
                ", hash=" + Arrays.toString(hash) +
                '}';
    }


    public static Account getAccountObject(AccountDTO accountDTO){
        String password = accountDTO.getPassword();
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("SHA-512");
        } catch (NoSuchAlgorithmException e){
            e.printStackTrace();
        }
        byte[] hash = messageDigest.digest(password.getBytes(StandardCharsets.UTF_8));
        return new Account(1, accountDTO.getFirstName(),accountDTO.getLastName(), accountDTO.getUsername(), hash, 1);
    }
}
