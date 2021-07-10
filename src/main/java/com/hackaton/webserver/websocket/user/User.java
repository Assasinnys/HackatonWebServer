package com.hackaton.webserver.websocket.user;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

/**
 * User representation of the messaging platform.
 * @author amrishodiq
 */
public class User {

    public String username;
    public String password;
    public String token;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        try {
            token = generateToken();
        } catch (NoSuchAlgorithmException ignored) {}
    }

    private String generateToken() throws NoSuchAlgorithmException {
        Random random = new Random(System.currentTimeMillis());
        String input = username + password + random.nextInt();
//        byte[] hash = MessageDigest.getInstance("MD5").digest(input.getBytes());
        return String.valueOf(input.hashCode());
    }
}
