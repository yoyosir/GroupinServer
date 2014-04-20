package com.cs8803mas.groupin.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cs8803mas.groupin.datastore.Group;
import com.cs8803mas.groupin.datastore.GroupDAO;
import com.cs8803mas.groupin.datastore.GroupJDODAO;
import com.cs8803mas.groupin.datastore.GroupUserPairDAO;
import com.cs8803mas.groupin.datastore.GroupUserPairJDODAO;
import com.cs8803mas.groupin.datastore.User;
import com.cs8803mas.groupin.datastore.UserDAO;
import com.cs8803mas.groupin.datastore.UserJDODAO;
import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;

@SuppressWarnings("serial")
public class JoinGroupServlet extends HttpServlet {

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
			String groupName = jsonObject.getString("groupname");
			String passcode = jsonObject.getString("passcode");
			User user;
			if ((user = USER_JDODAO.verifyUser(username, password)) == null) {
				resp.getWriter().write("denied");
			} else {
				
				Group group;
				if ((group = GROUP_DAO.verifyGroup(groupName, passcode)) != null) {
					if (!PAIR_DAO.pairExists(group.getId(), user.getId())) 
						PAIR_DAO.createPair(group.getId(), user.getId());
					else resp.getWriter().write("exist!");
				} else {
					resp.getWriter().write("invalid passcode");
				}
				
			}
		} catch (JSONException e) {
			resp.getWriter().write("denied");
			e.printStackTrace();
		}
		resp.getWriter().flush();
	}
}
