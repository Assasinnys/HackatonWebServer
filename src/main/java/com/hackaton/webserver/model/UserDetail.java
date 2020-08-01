package com.hackaton.webserver.model;

import java.util.HashMap;
import java.util.Map;

import static com.hackaton.webserver.util.Constants.*;

public class UserDetail {

    public int userId, age;
    public String alcoholType, gender, userName;

    public UserDetail(int userId, int age, String alcoholType, String gender, String userName) {
        this.userId = userId;
        this.age = age;
        this.alcoholType = alcoholType;
        this.gender = gender;
        this.userName = userName;
    }

    public Map<String, String> getJsonMap() {
        Map<String, String> map = new HashMap<>();
        map.put(KEY_AGE, String.valueOf(age));
        map.put(KEY_ID, String.valueOf(userId));
        map.put(KEY_ALCOHOL, alcoholType);
        map.put(KEY_GENDER, gender);
        map.put(KEY_USERNAME, userName);
        return map;
    }
}
