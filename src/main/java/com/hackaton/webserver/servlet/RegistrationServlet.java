package com.hackaton.webserver.servlet;

import com.hackaton.webserver.ServiceLocator;
import com.hackaton.webserver.database.DBHelper;
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

public class RegistrationServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, String[]> paramMap = req.getParameterMap();

        if (!paramMap.containsKey(KEY_LOGIN) || !paramMap.containsKey(KEY_PASS)) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        ServletOutputStream out = resp.getOutputStream();
        String login = paramMap.get(KEY_LOGIN)[0];
        String pass = paramMap.get(KEY_PASS)[0];

        if (login.equalsIgnoreCase("") || pass.equalsIgnoreCase("")) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        String code = ServiceLocator.dbHelper.createUser(login, pass);

        switch (code) {
            case DBHelper.CREATED_CODE: {
                User user = ServiceLocator.dbHelper.getUser(login);
                @SuppressWarnings("ConstantConditions")
                JSONObject userJson = new JSONObject(user.getJsonMap());
                out.write(userJson.toString().getBytes(StandardCharsets.UTF_8));
                out.flush();
                resp.setStatus(HttpServletResponse.SC_CREATED);
                break;
            }
            case DBHelper.EXIST_CODE: {
                out.print("{\"error\":\"" + DBHelper.EXIST_CODE + "\"}");
                out.flush();
                resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
            }
            case DBHelper.ERROR_CODE: {
                out.print("{\"error\":\"" + DBHelper.ERROR_CODE + "\"}");
                out.flush();
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        }

    }
}
