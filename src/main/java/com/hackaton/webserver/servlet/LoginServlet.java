package com.hackaton.webserver.servlet;

import com.hackaton.webserver.ServiceLocator;
import com.hackaton.webserver.model.User;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class LoginServlet extends HttpServlet {

    public static final String KEY_LOGIN = "login";
    public static final String KEY_PASS = "pass";
    public static final String KEY_ID = "id";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, String[]> paramMap = req.getParameterMap();
        if (!paramMap.containsKey(KEY_LOGIN) || !paramMap.containsKey(KEY_PASS)) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        ServletOutputStream out = resp.getOutputStream();
        String login = paramMap.get(KEY_LOGIN)[0];
        String pass = paramMap.get(KEY_PASS)[0];
        User user = ServiceLocator.dbHelper.getUser(login);

        if (user == null) {
            out.print("{\"error\":\"User does not exist\"}");
            out.flush();
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
        if (user.pass.equals(pass)) {
            JSONObject userJson = new JSONObject(user.getJsonMap());
            out.write(userJson.toString().getBytes(StandardCharsets.UTF_8));
            out.flush();
            resp.setStatus(HttpServletResponse.SC_OK);
        } else {
            out.print("{\"error\":\"Password is incorrect\"}");
            out.flush();
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
}
