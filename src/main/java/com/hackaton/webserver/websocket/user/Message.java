package com.hackaton.webserver.websocket.user;

/**
 * Message representation.
 * @author amrishodiq
 */

public class Message {
    public String from;
    public String body;
    public long sent;
    public int messageType = USER;

    public Message() {}

    public Message(String from, String body, long sent, int messageType) {
        this.from = from;
        this.body = body;
        this.sent = sent;
        this.messageType = messageType;
    }

    public static final int SYSTEM = 0;
    public static final int USER = 1;
    public static final String SERVER_NAME = "SERVER";
}
