	package com.rsvier.workshop2.useraccounts;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class PasswordHasher {

		    private SecureRandom random;
		    private String password;
		    private String encryptedPass;
		    private String encryptedTotal;

		    public String generateSalt() {
		        this.random = new SecureRandom();
		        byte bytes[] = new byte[20];
		        this.random.nextBytes(bytes);
		        return new String (bytes);
		    }

		    public String makeSaltedPasswordHash(String password) {
		        String saltString = generateSalt();
		        this.password = password + saltString;
		        try {
		            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
		            messageDigest.update(this.password.getBytes());
		            this.encryptedPass = new String(messageDigest.digest());
		            this.encryptedTotal = this.encryptedPass + saltString;
		        } catch (NoSuchAlgorithmException e) {
		            System.out.println(e);
		        }
		        return this.encryptedTotal;
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

		    public String hasher(String password) {
		        this.password = password;
		        try {
		            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
		            messageDigest.update(password.getBytes());
		            this.encryptedPass = new String(messageDigest.digest());
		        } catch (NoSuchAlgorithmException e) {
		            System.out.println(e);
		        }
		        return this.encryptedPass;
		    }
			
}