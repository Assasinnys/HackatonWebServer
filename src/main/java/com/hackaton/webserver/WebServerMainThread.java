package com.hackaton.webserver;

import com.hackaton.webserver.servlet.*;
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
        context.addServlet(new ServletHolder(new LocationServlet()), "/location");
        context.addServlet(new ServletHolder(new FindNearestUserServlet()), "/find");
        context.addServlet(new ServletHolder(new UserDetailServlet()), "/detail");
        try {
            server.start();
            server.join();
        } catch (Exception io) {
            io.printStackTrace();
        }
    }
}
