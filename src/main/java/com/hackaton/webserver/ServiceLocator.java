package com.hackaton.webserver;

import com.hackaton.webserver.database.DBHelper;

public abstract class ServiceLocator {
    public static DBHelper dbHelper = new DBHelper();
}
