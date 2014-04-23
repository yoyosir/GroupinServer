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
			User user = new User(jsonObject.getString("username"),
					jsonObject.getString("password"), jsonObject
							.getString("nickname"));
			int success = USER_JDODAO.addUser(user);
			if (success == 0) resp.getWriter().write("success");
			else resp.getWriter().write("username exists");
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		resp.getWriter().flush();
	}
}
