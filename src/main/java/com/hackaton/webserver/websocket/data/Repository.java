package com.hackaton.webserver.websocket.data;

import com.hackaton.webserver.websocket.user.User;

import java.util.HashMap;

/**
 * Repository responsible for all operation related to data saving or retrieval.
 * @author amrishodiq
 */

public class Repository {
    private static Repository instance;
    public synchronized static Repository getInstance() {
        if (instance == null) {
            instance = new Repository();
        }
        return instance;
    }

    private final HashMap<String, User> users = new HashMap<>();
    public final String SESSION_ID = "RS_SCHOOL";

    private Repository() {
        initDummyUser();
    }

    private void initDummyUser() {
        User user = new User("alice", "123456");
        users.put(user.username, user);
        user = new User("bob", "654321");
        users.put(user.username, user);
        user = new User("charlie", "qwerty");
        users.put(user.username, user);
    }

    public boolean isValid(User user) {
//        User item = users.get(user.username);
//        return item != null && item.password.equals(user.password);
        return true;
    }

    public boolean isValid(String session) {
        return SESSION_ID.equals(session);
    }
}
