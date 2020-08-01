package com.hackaton.webserver.model;

import com.hackaton.webserver.servlet.LoginServlet;

import java.util.HashMap;
import java.util.Map;

import static com.hackaton.webserver.util.Constants.*;

@SuppressWarnings("ALL")
public class User {

    public String login;
    public String pass;
    public int id;

    public User(String login, String pass, int id) {
        this.login = login;
        this.pass = pass;
        this.id = id;
    }

    public Map<String, String> getJsonMap() {
        Map<String, String> map = new HashMap<>();
        map.put(KEY_LOGIN, login);
        map.put(KEY_PASS, pass);
        map.put(KEY_ID, String.valueOf(id));

        return map;
    }
}
