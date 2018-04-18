	package com.rsvier.workshop2.useraccounts;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class PasswordHasher {

		    private SecureRandom random;

		    public String generateSalt() {
		        this.random = new SecureRandom();
		        byte bytes[] = new byte[20];
		        this.random.nextBytes(bytes);
		        return new String (bytes);
		    }
		    
		    public String makeSaltedPasswordHash(String password, String salt) {
		    	String encryptedTotal = password + salt;
		    	String encryptedPassword = "";
		        try {
		            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
		            messageDigest.update(encryptedTotal.getBytes());
		            encryptedPassword = new String(messageDigest.digest());
		        } catch (NoSuchAlgorithmException e) {
		            System.out.println(e);
		        }
		        return encryptedPassword;
		    }
}