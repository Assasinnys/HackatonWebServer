package com.hackaton.webserver;

import com.hackaton.webserver.servlet.Hi;
import com.hackaton.webserver.servlet.LoginServlet;
import com.hackaton.webserver.servlet.RegistrationServlet;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

public class WebServerMainThread extends Thread {

    @Override
    public void run() {
        int port = Integer.parseInt(System.getenv("PORT"));
        Server server = new Server(port);
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        server.setHandler(context);
        context.addServlet(new ServletHolder(new Hi()), "/*");
        context.addServlet(new ServletHolder(new LoginServlet()), "/login");
        context.addServlet(new ServletHolder(new RegistrationServlet()), "/register");
//        context.addServlet(new ServletHolder(new TakeActiveServlet()), "/takeactive");
        try {
            server.start();
            server.join();
        } catch (Exception io) {
            io.printStackTrace();
        }
    }
}
