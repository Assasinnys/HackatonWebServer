package com.hackaton.webserver.servlet;

import com.hackaton.webserver.ServiceLocator;
import com.hackaton.webserver.model.UserLocation;
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

public class LocationServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, String[]> paramMap = req.getParameterMap();
        if (!paramMap.containsKey(KEY_ID)) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        ServletOutputStream out = resp.getOutputStream();
        int userId = Integer.parseInt(paramMap.get(KEY_ID)[0]);
        UserLocation location = ServiceLocator.dbHelper.getUserLocation(userId);

        if (location == null) {
            out.print("{\"error\":\"Location not found\"}");
            out.flush();
            resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
        } else {
            JSONObject userJson = new JSONObject(location.getJsonMap());
            out.write(userJson.toString().getBytes(StandardCharsets.UTF_8));
            out.flush();
            resp.setStatus(HttpServletResponse.SC_OK);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, String[]> paramMap = req.getParameterMap();
        if (!paramMap.containsKey(KEY_ID) || !paramMap.containsKey(KEY_LAT) || !paramMap.containsKey(KEY_LON)) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        int userId = Integer.parseInt(paramMap.get(KEY_ID)[0]);
        float lat = Float.parseFloat(paramMap.get(KEY_LAT)[0]);
        float lon = Float.parseFloat(paramMap.get(KEY_LON)[0]);

        UserLocation location = new UserLocation(userId, lat, lon);

        if (ServiceLocator.dbHelper.setUserLocation(location)) {
            resp.setStatus(HttpServletResponse.SC_OK);
        } else {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
