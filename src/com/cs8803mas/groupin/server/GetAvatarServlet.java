package com.cs8803mas.groupin.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.cs8803mas.groupin.datastore.Avatar;
import com.cs8803mas.groupin.datastore.AvatarDAO;
import com.cs8803mas.groupin.datastore.AvatarJDODAO;
import com.cs8803mas.groupin.datastore.UserDAO;
import com.cs8803mas.groupin.datastore.UserJDODAO;
import com.google.appengine.api.datastore.Blob;
import com.google.appengine.labs.repackaged.org.json.JSONObject;

@WebServlet("/upload")
@MultipartConfig
@SuppressWarnings("serial")
public class GetAvatarServlet extends HttpServlet {

	private static final UserDAO USER_DAO = new UserJDODAO();
	private static final AvatarDAO AVATAR_DAO = new AvatarJDODAO();

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
			Long id = USER_DAO.getUserByName(username).getId();
			Blob avatar = AVATAR_DAO.getAvatarByUser(id);
			byte[] bytes = avatar.getBytes();
			int len = bytes.length;
			resp.getOutputStream().write(bytes, 0, len);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
