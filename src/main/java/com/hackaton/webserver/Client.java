package com.hackaton.webserver;

import java.net.URI;
import java.util.Scanner;
import java.util.concurrent.Future;

import com.google.gson.Gson;
import com.hackaton.webserver.websocket.ChatWebSocket;
import com.hackaton.webserver.websocket.data.Data;
import com.hackaton.webserver.websocket.user.Message;
import com.hackaton.webserver.websocket.user.User;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.client.WebSocketClient;

public class Client {
    public static void main(String[] args) {
        URI uri = URI.create("wss://localhost:8080/chat");
        Gson gson = new Gson();

        WebSocketClient client = new WebSocketClient();
        try {
            try {
                client.start();
                // The socket that receives events
                ChatWebSocket socket = new ChatWebSocket();
                // Attempt Connect
                Future<Session> fut = client.connect(socket, uri);
                // Wait for Connect
                Session session = fut.get();

                Scanner scanner = new Scanner(System.in);
                String message;

                Data data = new Data();
                data.operation = Data.AUTHENTICATION_LOGIN;
                data.user = new User("assasinnys", "123");
                session.getRemote().sendString(gson.toJson(data));

                while (!(message = scanner.nextLine()).equals("stop")) {
                    // Send a message
                    Data dataMessage = new Data();
                    dataMessage.operation = Data.MESSAGING_SEND;
                    dataMessage.message = new Message(data.user.username, message, System.currentTimeMillis(), Message.USER);

                    session.getRemote().sendString(gson.toJson(dataMessage));
                }

                // Close session
                session.close();

            } finally {
                client.stop();
            }
        } catch (Throwable t) {
            t.printStackTrace(System.err);
        }
    }
}
