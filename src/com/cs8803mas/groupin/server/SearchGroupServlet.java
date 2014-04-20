package com.cs8803mas.groupin.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cs8803mas.groupin.datastore.Group;
import com.cs8803mas.groupin.datastore.GroupDAO;
import com.cs8803mas.groupin.datastore.GroupJDODAO;
import com.cs8803mas.groupin.datastore.User;
import com.cs8803mas.groupin.datastore.UserDAO;
import com.cs8803mas.groupin.datastore.UserJDODAO;
import com.google.appengine.labs.repackaged.org.json.JSONArray;
import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;

@SuppressWarnings("serial")
public class SearchGroupServlet extends HttpServlet {

	private static final UserDAO USER_JDODAO = new UserJDODAO();
	private static final GroupDAO GROUP_DAO = new GroupJDODAO();
	
	double rad(double degree) {
		return degree * Math.PI / 180;
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// resp.getWriter().write("response");
		resp.setHeader("Content-Type", "application/json");
		StringBuilder sb = new StringBuilder();
		String s = null;
		BufferedReader br = new BufferedReader(new InputStreamReader(
				req.getInputStream()));
		while ((s = br.readLine()) != null) {
			sb.append(s);
		}

		try {
			JSONObject jsonObject = new JSONObject(sb.toString());
			String username = jsonObject.getString("username");
			String password = jsonObject.getString("password");
			double coordinateX = Double.parseDouble(jsonObject.getString("coordinatex"));
			double coordinateY = Double.parseDouble(jsonObject.getString("coordinatey"));
			if (USER_JDODAO.verifyUser(username, password) == null) {
				resp.getWriter().write("denied");
			} else {
				List<Group> groupList = GROUP_DAO.getAllGroups();
				JSONArray jsonArray = new JSONArray();
				//resp.getWriter().write("" + groupList.size());
				for (Group group : groupList) {
					double R = 6371;
					double dLat = rad(coordinateX - group.getCoordinateX());
					double dLon = rad(coordinateY - group.getCoordinateY());
					double lat2 = rad(coordinateX);
					double lat1 = rad(group.getCoordinateX());
					double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
					        Math.sin(dLon/2) * Math.sin(dLon/2) * Math.cos(lat1) * Math.cos(lat2);
					double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
					double d = R * c;
					if (d > 0.5) continue;
					User user = USER_JDODAO.getUserById(group.getGuuid());
					JSONObject object = new JSONObject();
					object.put("groupname", group.getName());
					object.put("founder", user.getUsername());
					object.put("dis", "" + d);
					jsonArray.put(object);
				}
				resp.getWriter().write(jsonArray.toString());
			}
		} catch (JSONException e) {
			resp.getWriter().write("denied");
		}
	}
}
