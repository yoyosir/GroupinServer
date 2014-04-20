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
import com.cs8803mas.groupin.datastore.GroupDAO;
import com.cs8803mas.groupin.datastore.GroupJDODAO;
import com.cs8803mas.groupin.datastore.SharedImage;
import com.cs8803mas.groupin.datastore.SharedImageDAO;
import com.cs8803mas.groupin.datastore.SharedImageJDODAO;
import com.cs8803mas.groupin.datastore.UserDAO;
import com.cs8803mas.groupin.datastore.UserJDODAO;

@WebServlet("/upload")
@MultipartConfig
@SuppressWarnings("serial")
public class UploadImageServlet extends HttpServlet {

	private static final UserDAO USER_DAO = new UserJDODAO();
	private static final GroupDAO GROUP_DAO = new GroupJDODAO();
	private static final SharedImageDAO SHARED_IMAGE_DAO = new SharedImageJDODAO();

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		try {
			ServletFileUpload upload = new ServletFileUpload();
			resp.setContentType("text/plain");

			FileItemIterator iterator = upload.getItemIterator(req);
			String username = null;
			String groupname = null;
			int length = 0;
			while (iterator.hasNext()) {
				FileItemStream item = iterator.next();
				InputStream stream = item.openStream();
				byte[] bytes = new byte[1024 * 1024 * 2];
				if (item.isFormField()) {
					if (item.getFieldName().equals("username")) {
						BufferedReader reader = new BufferedReader(
								new InputStreamReader(stream));
						username = reader.readLine();
						reader.close();
					} else if (item.getFieldName().equals("groupname")) {
						BufferedReader reader = new BufferedReader(
								new InputStreamReader(stream));
						groupname = reader.readLine();
						reader.close();
					}
				} else {
					int len;
					byte[] buffer = new byte[8192];
					while ((len = stream.read(buffer, 0, buffer.length)) != -1) {
						System.arraycopy(buffer, 0, bytes, length, len);
						length += len;
						if (length > 1024 * 1024) {
							resp.getWriter().write("too large");
							break;
						}
					}
				}

				if (length > 0 && length < 1024 * 1024 && username != null
						&& groupname != null) {
					byte[] store = new byte[length];
					System.arraycopy(bytes, 0, store, 0, length);
					Long uid = USER_DAO.getUserByName(username).getId();
					Long gid = GROUP_DAO.getGroupByName(groupname).getId();
					SharedImage image = new SharedImage(gid, uid, store);
					if (SHARED_IMAGE_DAO.createImage(image)) {
						resp.getWriter().write("succeed");
					} else {
						resp.getWriter().write("fail");
					}
				}
			}
		} catch (Exception ex) {
			throw new ServletException(ex);
		}
	}
}
