package com.hackaton.webserver.websocket;


import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.WebSocketAdapter;

public class ChatWebSocket extends WebSocketAdapter {

    @Override
    public void onWebSocketClose(int statusCode, String reason) {
        System.out.println("web socket closed, status: " + statusCode + ", reason: " + reason);
        super.onWebSocketClose(statusCode, reason);
    }

    @Override
    public void onWebSocketConnect(Session sess) {
        super.onWebSocketConnect(sess);
        System.out.println("client connected");
    }

    @Override
    public void onWebSocketError(Throwable cause) {
        super.onWebSocketError(cause);
        System.out.println("web socket exception");
        cause.printStackTrace();
    }

    @Override
    public void onWebSocketText(String message) {
        super.onWebSocketText(message);
        System.out.println("Message received: " + message);
    }
}
