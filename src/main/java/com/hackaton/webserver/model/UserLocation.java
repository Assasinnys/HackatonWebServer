package com.hackaton.webserver.model;

import java.util.HashMap;
import java.util.Map;

import static com.hackaton.webserver.util.Constants.*;

public class UserLocation {
    public int id;
    public float lat, lon;

    public UserLocation(int id, float lat, float lon) {
        this.id = id;
        this.lat = lat;
        this.lon = lon;
    }

    public Map<String, String> getJsonMap() {
        Map<String, String> map = new HashMap<>();
        map.put(KEY_ID, String.valueOf(id));
        map.put(KEY_LAT, String.valueOf(lat));
        map.put(KEY_LON, String.valueOf(lon));
        return map;
    }
}
