package com.hackaton.webserver.websocket;

import com.hackaton.webserver.websocket.user.User;
import com.hackaton.webserver.websocket.user.UserSession;
import org.eclipse.jetty.websocket.api.CloseStatus;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.WebSocketAdapter;

import java.io.IOException;

/**
 * MessagingAdapter responsible to handle connection, receiving data, forward
 * the data to @see id.amrishodiq.jettywebsocket.MessagingLogic and receive
 * data from @see id.amrishodiq.jettywebsocket.MessagingLogic to be
 * delivered to recipient.
 *
 * @author amrishodiq
 */

public class UserSocket extends WebSocketAdapter implements UserSession {

    private volatile Session session;
    private User currentUser;

    @Override
    public void onWebSocketConnect(Session session) {
        super.onWebSocketConnect(session);
        System.out.println("Client connected");
        this.session = session;
    }

    @Override
    public void onWebSocketText(String message) {
        System.out.println("message received: " + message);
        super.onWebSocketText(message);
        MessagingController.getInstance().receiveText(this, message);
    }

    @Override
    public void receiveText(String text) throws Exception {
        if (session != null && session.isOpen()) {
            session.getRemote().sendString(text);
        }
    }

    @Override
    public void setCurrentUser(User user) {
        this.currentUser = user;
    }

    @Override
    public User getUser() {
        return currentUser;
    }

    @Override
    public void onWebSocketClose(int statusCode, String reason) {
        super.onWebSocketClose(statusCode, reason);
        MessagingController.getInstance().setOffline(currentUser, this);
        this.session = null;

        System.out.println("Close connection " + statusCode + ", " + reason);
    }

    @Override
    public void onWebSocketError(Throwable cause) {
        System.out.println("web socket error");
        cause.printStackTrace();
        super.onWebSocketError(cause);
    }

    @Override
    public void disconnect(int status, String reason) {
        session.close(status, reason);
        System.out.println("isOpen: " + session.isOpen());
        System.out.println("user was disconnected!");
    }
}
