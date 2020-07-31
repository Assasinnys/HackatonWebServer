package com.hackaton.webserver.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Hi extends HttpServlet {
        private static final String INDEX = "<!DOCTYPE html>\n" +
            "<html>\n" +
            "  <head>\n" +
            "    <meta charset=\"UTF-8\">" +
            "    <title>Заголовок</title>\n" +
            "  </head>\n" +
            "  <body>\n" +
            "    <h1> WINNERS OF HACKATON USE THIS SERVER! :D</h1>\n" +
            "  </body>\n" +
            "</html>";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("GET request accepted! ☺");
        resp.getOutputStream().flush();
        resp.getOutputStream().print(INDEX);
        resp.getOutputStream().flush();
        resp.setStatus(HttpServletResponse.SC_OK);



//        resp.sendRedirect("https://id.twitch.tv/oauth2/authorize" +
//                "?client_id=" + Main.CLIENT_ID + "&redirect_uri=https://herbott.herokuapp.com/oauth" +
//                "&response_type=code&scope=user:edit:broadcast");
    }
}
