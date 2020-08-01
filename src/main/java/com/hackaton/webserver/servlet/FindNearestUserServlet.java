package com.hackaton.webserver.servlet;

import com.hackaton.webserver.ServiceLocator;
import com.hackaton.webserver.model.UserLocation;
import org.json.JSONArray;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import static com.hackaton.webserver.util.Constants.*;

public class FindNearestUserServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, String[]> paramMap = req.getParameterMap();
        if (!paramMap.containsKey(KEY_ID) || !paramMap.containsKey(KEY_LAT) || !paramMap.containsKey(KEY_LON)) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        ServletOutputStream out = resp.getOutputStream();
        int userId = Integer.parseInt(paramMap.get(KEY_ID)[0]);
        float lat = Float.parseFloat(paramMap.get(KEY_LAT)[0]);
        float lon = Float.parseFloat(paramMap.get(KEY_LON)[0]);

        UserLocation location = new UserLocation(userId, lat, lon);
        JSONArray array = new JSONArray();

        for (UserLocation userLocation: ServiceLocator.dbHelper.getNearestUsers(location)) {
            array.put(userLocation.getJsonMap());
        }

        out.write(array.toString().getBytes(StandardCharsets.UTF_8));
        out.flush();
        resp.setStatus(HttpServletResponse.SC_OK);
    }
}
