package com.hackaton.webserver.websocket;

import com.google.gson.Gson;
import com.hackaton.webserver.websocket.data.Data;
import com.hackaton.webserver.websocket.data.Repository;
import com.hackaton.webserver.websocket.user.*;
import org.eclipse.jetty.websocket.api.StatusCode;

import java.util.HashSet;

/**
 * MessagingLogic will be responsible for parsing received data, do
 * authentication and forward the messages between users.
 *
 * @author amrishodiq
 */
public class MessagingController {

    private static MessagingController instance;

    public static MessagingController getInstance() {
        if (instance == null) {
            instance = new MessagingController();
        }
        return instance;
    }

    private final HashSet<UserSession> authorizedUserSessions = new HashSet<>();
    private final Gson gson = new Gson();

    private MessagingController() {
    }

    public void receiveText(UserSession session, String text) {
        try {
            receiveData(session, gson.fromJson(text, Data.class));
        } catch (Throwable t) {
            session.disconnect(StatusCode.BAD_DATA, "Wrong message type. Use json type.");
            t.printStackTrace();
        }
    }

    private void receiveData(UserSession session, Data data) {
        if (data == null) return;

        // for all operation except login, do session checking
        if (data.operation != Data.AUTHENTICATION_LOGIN) {
//            if (!isValidSession(data.session)) {
//                session.disconnect(401, "Invalid session");
//                return;
//            }
            if (!authorizedUserSessions.contains(session)) {
                session.disconnect(UNAUTHORIZED_CODE, "You need to authorize before sending messages");
            }
        }

        switch (data.operation) {
            case Data.AUTHENTICATION_LOGIN:
                login(session, data.user);
                break;
            case Data.MESSAGING_SEND:
                if (data.message.messageType == Message.USER) send(data.message);
                else session.disconnect(StatusCode.POLICY_VIOLATION, "Trying to use system message type");
                break;
            default:
                session.disconnect(StatusCode.BAD_PAYLOAD, "Wrong data operation type");
        }
    }

    private void login(UserSession session, User user) {
        if (user == null) {
            session.disconnect(UNAUTHORIZED_CODE, "Give username and password!");
            return;
        }

        if (Repository.getInstance().isValid(user)) {

            session.setCurrentUser(user);

            Data data = new Data();
            data.operation = Data.AUTHENTICATION_LOGIN;
            data.session = Repository.getInstance().SESSION_ID;
            data.message = new Message(Message.SERVER_NAME, "You've been authorized! Welcome to chat.", System.currentTimeMillis(), Message.SYSTEM);

            send(new Message(Message.SERVER_NAME, user.username + " has connected to the chat!", System.currentTimeMillis(), Message.SYSTEM));
            authorizedUserSessions.add(session);

            try {
                session.receiveText(gson.toJson(data));
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        } else {
            session.disconnect(UNAUTHORIZED_CODE, "Wrong username or password!");
        }
    }

    private boolean isValidSession(String session) {
        return Repository.getInstance().isValid(session);
    }

    private void send(Message message) {
        System.out.println("Try to send message...");
        try {
            for (UserSession userSession : authorizedUserSessions) {
                userSession.receiveText(gson.toJson(message));
            }
            System.out.println("Message sent to others");
        } catch (Exception ex) {
            // put to offline message
            System.out.println("User is offline");
            ex.printStackTrace();
        }
    }

    public void setOffline(User user, UserSession userSession) {
        authorizedUserSessions.remove(userSession);
        Message message = new Message(
                Message.SERVER_NAME,
                "User " + user.username + " has gone offline!",
                System.currentTimeMillis(),
                Message.SYSTEM
        );
        send(message);
        System.out.println("Set " + user.username + " offline.");
    }

    /**
     * User violates authorisation
     */

    public static final int UNAUTHORIZED_CODE = 3001;
}
