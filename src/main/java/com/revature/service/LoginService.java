package com.revature.service;

import com.revature.model.Account;
import com.revature.repository.AccountDAOImpl;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class LoginService {

    private AccountDAOImpl accountDAO = new AccountDAOImpl();

    public Account login(String username, String password){
        Account account = accountDAO.getAccountByUsername(username);

        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("SHA-512");
        } catch (NoSuchAlgorithmException e){
            e.printStackTrace();
        }
        byte[] hash = messageDigest.digest(password.getBytes(StandardCharsets.UTF_8));

        if(compareHash(account.getHash(), hash)){
            return account;
        }
        else {
            return null;
        }

    }

    private static boolean compareHash(byte[] hash1, byte[] hash2){
        if(hash1.length != hash2.length){
            return false;
        }
        for(int i = 0; i<hash1.length; i+=1){
            if(hash1[i] != hash2[i]){
                return false;
            }
        }
        return true;
    }
}
