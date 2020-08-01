package com.hackaton.webserver.database;

import com.hackaton.webserver.model.User;
import com.hackaton.webserver.model.UserLocation;
import org.jetbrains.annotations.Nullable;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBHelper {

    private static final String USERS_TABLE = "users";
    private static final String LOCATION_TABLE = "userlocation";
    private static final String LOGIN_C = "login";
    private static final String PASS_C = "pass";
    private static final String ID_C = "id";
    private static final String USER_ID_C = "userid";
    private static final String LAT_C = "lat";
    private static final String LON_C = "lon";

    public static final String CREATED_CODE = "created";
    public static final String ERROR_CODE = "unknown error";
    public static final String EXIST_CODE = "exist";

    private Connection connection;

    public DBHelper() {
        try {
            connection = getConnection();
        } catch (SQLException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

    private Connection getConnection() throws URISyntaxException, SQLException {
        System.out.println("Starting to create connection!");
//        URI dbUri = new URI(testUri);
        URI dbUri = new URI(System.getenv("DATABASE_URL"));

        String username = dbUri.getUserInfo().split(":")[0];
        String password = dbUri.getUserInfo().split(":")[1];
        String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + dbUri.getPath() + "?sslmode=require";

        System.out.println("Connection ready!");

        return DriverManager.getConnection(dbUrl, username, password);
    }

    @Nullable
    public User getUser(String login) {
        if (connection == null) return null;

        try {
            ResultSet result = connection.createStatement().executeQuery("SELECT * FROM " + USERS_TABLE +
                    " WHERE " + LOGIN_C + " = \'" + login + "\'");

            if (result.next()) {
                return new User(
                        result.getString(LOGIN_C),
                        result.getString(PASS_C),
                        result.getInt(ID_C)
                );
            } else return null;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public String createUser(String login, String pass) {
        try {
            Statement s = connection.createStatement();
            User user = getUser(login);
            if (user == null) {
                s.execute("INSERT INTO " + USERS_TABLE + " (" + LOGIN_C + ", " + PASS_C +
                        ") VALUES (\'" + login + "\', \'" + pass + "\')");
                s.close();
                return CREATED_CODE;
            } else {
                return EXIST_CODE;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ERROR_CODE;
    }

    private boolean checkUserExist(int id) {
        try {
            Statement s = connection.createStatement();
            ResultSet r = s.executeQuery("SELECT id FROM " + USERS_TABLE + " WHERE " + ID_C + " = " + id);
            s.close();
            return r.first();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Nullable
    public UserLocation getUserLocation(int id) {
        try {
            Statement s = connection.createStatement();
            ResultSet r = s.executeQuery("SELECT * FROM " + LOCATION_TABLE + " WHERE = " + USER_ID_C + " = " + id);
            if (r.next()) {
                return new UserLocation(
                        r.getInt(USER_ID_C),
                        r.getFloat(LAT_C),
                        r.getFloat(LON_C)
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean setUserLocation(UserLocation location) {
        try {
            Statement s = connection.createStatement();
            if (checkUserExist(location.id)) {
                UserLocation loc = getUserLocation(location.id);
                if (loc == null) {
                    s.execute("INSERT INTO " + LOCATION_TABLE + " (" + USER_ID_C + ", " + LAT_C + ", " + LON_C +
                            ") VALUES (" + location.id + ", " + location.lat + ", " + location.lon + ")");

                } else {
                    s.execute("UPDATE " + LOCATION_TABLE + " SET " + LAT_C + " = " + location.lat + ", " + LON_C + " = " + location.lon);
                }
                s.close();
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<UserLocation> getNearestUsers(UserLocation location) {
        ArrayList<UserLocation> users = new ArrayList<>();
        try {
            Statement s = connection.createStatement();
            ResultSet resultSet = s.executeQuery("SELECT * FROM " + LOCATION_TABLE);
            while (resultSet.next()) {
                users.add(
                        new UserLocation(
                                resultSet.getInt(USER_ID_C),
                                resultSet.getFloat(LAT_C),
                                resultSet.getFloat(LON_C)
                        )
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }
}
