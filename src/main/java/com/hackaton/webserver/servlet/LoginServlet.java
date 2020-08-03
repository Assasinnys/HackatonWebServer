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
import java.nio.charset.StandardCharsets;
import java.util.Map;

import static com.hackaton.webserver.util.Constants.KEY_LOGIN;
import static com.hackaton.webserver.util.Constants.KEY_PASS;

public class LoginServlet extends HttpServlet {

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
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
        if (user.pass.equals(pass)) {
            JSONObject userJson = new JSONObject(user.getJsonMap());
            out.write(userJson.toString().getBytes(StandardCharsets.UTF_8));
            out.flush();
            resp.setStatus(HttpServletResponse.SC_OK);
        } else {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }
}
