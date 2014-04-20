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
import com.cs8803mas.groupin.datastore.Message;
import com.cs8803mas.groupin.datastore.MessageDAO;
import com.cs8803mas.groupin.datastore.MessageJDODAO;
import com.cs8803mas.groupin.datastore.User;
import com.cs8803mas.groupin.datastore.UserDAO;
import com.cs8803mas.groupin.datastore.UserJDODAO;
import com.google.appengine.labs.repackaged.org.json.JSONArray;
import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;

@SuppressWarnings("serial")
public class RetrieveMessageServlet extends HttpServlet {

	private static final UserDAO USER_JDODAO = new UserJDODAO();
	private static final GroupDAO GROUP_DAO = new GroupJDODAO();
	private static final MessageDAO MESSAGE_DAO = new MessageJDODAO();

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
			if (USER_JDODAO.verifyUser(username, password) == null) {
				resp.getWriter().write("denied");
			} else {
				Group group;
				if ((group = GROUP_DAO.verifyGroup(groupName, passcode)) != null) {
					List<Message> messages = MESSAGE_DAO.getMessagesForGroup(group.getId());
					JSONArray jsonArray = new JSONArray();
					for (Message message : messages) {
						User user = USER_JDODAO.getUserById(message.getUid());
						JSONObject object = new JSONObject();
						object.put("username", user.getUsername());
						object.put("content", message.getContent());
						object.put("time", message.getTime());
						jsonArray.put(object);
					}
					resp.getWriter().write(jsonArray.toString());
					resp.getWriter().flush();
				} else {
					resp.getWriter().write("invalid passcode");
				}
			}
		} catch (JSONException e) {
			resp.getWriter().write("denied");
			e.printStackTrace();
		}
	}
}
