package com.revature.repository;

import com.revature.model.Account;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public class AccountDAOImplTest {

    static MessageDigest messageDigest = null;
    static byte[] hash = messageDigest.digest("password".getBytes(StandardCharsets.UTF_8));

    private static AccountDAO accountDAO = new AccountDAOImpl();
    private static Account testAccount = new Account(10001,"John","Smith","jsmith1", messageDigest.digest("jsmith1".getBytes(StandardCharsets.UTF_8)),1);




}
