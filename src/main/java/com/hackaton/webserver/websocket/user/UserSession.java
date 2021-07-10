package com.hackaton.webserver.websocket.user;

/**
 * UserSession is an interface to track every client connection.
 * @author amrishodiq
 */

public interface UserSession {
    void receiveText(String text) throws Exception;
    void setCurrentUser(User user);
    User getUser();
    void disconnect(int status, String reason);
}
