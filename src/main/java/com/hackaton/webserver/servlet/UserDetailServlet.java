package com.hackaton.webserver.servlet;

import com.hackaton.webserver.ServiceLocator;
import com.hackaton.webserver.model.User;
import com.hackaton.webserver.model.UserDetail;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import static com.hackaton.webserver.util.Constants.*;

public class UserDetailServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, String[]> paramMap = req.getParameterMap();
        if (!paramMap.containsKey(KEY_ID)) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        ServletOutputStream out = resp.getOutputStream();
        int userId = Integer.parseInt(paramMap.get(KEY_ID)[0]);
        UserDetail detail = ServiceLocator.dbHelper.getUserDetail(userId);

        if (detail == null) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        } else {
            JSONObject json = new JSONObject(detail.getJsonMap());
            out.write(json.toString().getBytes(StandardCharsets.UTF_8));
            out.flush();
            resp.setStatus(HttpServletResponse.SC_OK);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, String[]> paramMap = req.getParameterMap();
        if (!paramMap.containsKey(KEY_LOGIN) || !paramMap.containsKey(KEY_PASS)) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        String login = paramMap.get(KEY_LOGIN)[0];
        String pass = paramMap.get(KEY_PASS)[0];

        User user = ServiceLocator.dbHelper.getUser(login);
        if (user == null || !user.pass.equals(pass)) {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        } else {
            int age = Integer.parseInt(paramMap.get(KEY_AGE)[0]);
            String gender = paramMap.get(KEY_GENDER)[0];
            String alcoholType = paramMap.get(KEY_ALCOHOL)[0];
            String userName = paramMap.get(KEY_USERNAME)[0];
            UserDetail detail = new UserDetail(user.id, age, alcoholType, gender, userName);
            if (ServiceLocator.dbHelper.setUserDetails(detail)) {
                resp.setStatus(HttpServletResponse.SC_OK);
            } else {
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        }
    }
}
