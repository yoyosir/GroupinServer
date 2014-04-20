package com.cs8803mas.groupin.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cs8803mas.groupin.datastore.User;
import com.cs8803mas.groupin.datastore.UserJDODAO;
import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;

@SuppressWarnings("serial")
public class CreateUserServlet extends HttpServlet {

	private static final UserJDODAO USER_JDODAO = new UserJDODAO();

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.getWriter().write("response");
		resp.setHeader("Content-Type", "application/json");
		StringBuilder sb = new StringBuilder();
		String s = null;
		BufferedReader br = new BufferedReader(new InputStreamReader(
				req.getInputStream()));
		while ((s = br.readLine()) != null) {
			sb.append(s);
		}

		resp.getWriter().write(
				"[user count:" + USER_JDODAO.listUser().size() + "]");
		//resp.getWriter().write(sb.toString());

		try {
			JSONObject jsonObject = new JSONObject(sb.toString());
			resp.getWriter().write(jsonObject.getString("username") + "\n");
			resp.getWriter().write(jsonObject.getString("password") + "\n");
			resp.getWriter().write(jsonObject.getString("nickname") + "\n");
			User user = new User(jsonObject.getString("username"),
					jsonObject.getString("password"), jsonObject
							.getString("nickname"));
			int success = USER_JDODAO.addUser(user);
			resp.getWriter().write("success" + success);
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		resp.getWriter().write(
				"[user count:" + USER_JDODAO.listUser().size() + "]");
		resp.getWriter().flush();
	}
}
