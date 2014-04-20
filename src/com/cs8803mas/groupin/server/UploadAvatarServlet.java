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

@WebServlet("/upload")
@MultipartConfig
@SuppressWarnings("serial")
public class UploadAvatarServlet extends HttpServlet {

	private static final UserDAO USER_DAO = new UserJDODAO();
	private static final AvatarDAO AVATAR_DAO = new AvatarJDODAO();

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		try {
			ServletFileUpload upload = new ServletFileUpload();
			resp.setContentType("text/plain");

			FileItemIterator iterator = upload.getItemIterator(req);
			String username = null;
			int length = 0;
			while (iterator.hasNext()) {
				FileItemStream item = iterator.next();
				InputStream stream = item.openStream();
				byte[] bytes = new byte[800000];
				if (item.isFormField()) {
					if (item.getFieldName().equals("username")) {
						BufferedReader reader = new BufferedReader(
								new InputStreamReader(stream));
						username = reader.readLine();
						//resp.getWriter().write("|" + username + "|\n");
						reader.close();
					}
				} else {
					int len;
					byte[] buffer = new byte[8192];
					while ((len = stream.read(buffer, 0, buffer.length)) != -1) {
						System.arraycopy(buffer, 0, bytes, length, len);
						length += len;
					}
				}
				
				if (length > 0 && username != null) {
					byte[] store = new byte[length];
					System.arraycopy(bytes, 0, store, 0, length);
					Long id = USER_DAO.getUserByName(username).getId();
					Avatar avatar = new Avatar(id, store);
					if (AVATAR_DAO.saveAvatar(avatar)) resp.getWriter().write("success");
					else resp.getWriter().write("fail writing to DB");
					
					//resp.getWriter().write("ll" + avatar.getBlob().getBytes().length);
				}
			}
			//resp.getWriter().write("end" + length + "," + username + "," + flag);
		} catch (Exception ex) {
			//resp.getWriter().write("fail");
			throw new ServletException(ex);
		}
	}
}
