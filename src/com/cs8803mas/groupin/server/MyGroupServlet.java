package com.cs8803mas.groupin.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cs8803mas.groupin.datastore.Group;
import com.cs8803mas.groupin.datastore.GroupDAO;
import com.cs8803mas.groupin.datastore.GroupJDODAO;
import com.cs8803mas.groupin.datastore.GroupUserPair;
import com.cs8803mas.groupin.datastore.GroupUserPairDAO;
import com.cs8803mas.groupin.datastore.GroupUserPairJDODAO;
import com.cs8803mas.groupin.datastore.User;
import com.cs8803mas.groupin.datastore.UserDAO;
import com.cs8803mas.groupin.datastore.UserJDODAO;
import com.google.appengine.labs.repackaged.org.json.JSONArray;
import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;

@SuppressWarnings("serial")
public class MyGroupServlet extends HttpServlet {

	private static final UserDAO USER_JDODAO = new UserJDODAO();
	private static final GroupDAO GROUP_DAO = new GroupJDODAO();
	private static final GroupUserPairDAO PAIR_DAO = new GroupUserPairJDODAO();

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
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
			User user = null;
			if ((user = USER_JDODAO.verifyUser(username, password)) == null) {
				resp.getWriter().write("denied");
			} else {
				user = USER_JDODAO.getUserByName(username);
				List<GroupUserPair> pairList = PAIR_DAO.getGroupsFromUser(user
						.getId());

				List<Group> groupList = new ArrayList<Group>();

				for (GroupUserPair pair : pairList) {
					groupList.add(GROUP_DAO.getGroupById(pair.getGid()));
				}
				JSONArray jsonArray = new JSONArray();
				for (Group group : groupList) {
					user = USER_JDODAO.getUserById(group.getGuuid());
					JSONObject object = new JSONObject();
					object.put("groupname", group.getName());
					object.put("passcode", group.getPasscode());
					object.put("founder", user.getUsername());
					jsonArray.put(object);
				}
				resp.getWriter().write(jsonArray.toString());

			}
		} catch (JSONException e) {
			resp.getWriter().write("denied");
			resp.getWriter().flush();
		}
		resp.getWriter().flush();
	}
}
