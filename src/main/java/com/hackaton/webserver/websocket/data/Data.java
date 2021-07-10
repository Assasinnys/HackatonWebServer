package com.hackaton.webserver.websocket.data;

import com.hackaton.webserver.websocket.user.Message;
import com.hackaton.webserver.websocket.user.User;

public class Data {

    public static final int AUTHENTICATION_LOGIN = 1;
    public static final int MESSAGING_SEND = 101;

    public int operation;
    public User user;
    public Message message;
    public String session;
}
