package com.hackaton.webserver.model;

import com.hackaton.webserver.servlet.LoginServlet;

import java.util.HashMap;
import java.util.Map;

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
        map.put(LoginServlet.KEY_LOGIN, login);
        map.put(LoginServlet.KEY_PASS, pass);
        map.put(LoginServlet.KEY_ID, String.valueOf(id));

        return map;
    }
}
