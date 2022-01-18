package com.revature;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class test {

    public static void main(String[] args) {
        SecureRandom random = new SecureRandom();
        MessageDigest messageDigest = null;


            try {
                messageDigest = MessageDigest.getInstance("SHA-512");
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }


            byte[] salt = new byte[16];
            random.nextBytes(salt);


        byte[] hash = messageDigest.digest("password".getBytes(StandardCharsets.UTF_8));

        byte[] hash2 = messageDigest.digest("password".getBytes(StandardCharsets.UTF_8));

        System.out.println(hash.equals(hash2));

        int count = 0;
        for(int i=0; i<hash.length; i+=1){
            System.out.println(hash[i] + " | " + hash2[i]);
            System.out.println(hash[i] == hash2[i]);
            ++count;
        }
        System.out.println(count);
    }








}
