package com.cs8803mas.groupin.server;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cs8803mas.groupin.datastore.Group;
import com.cs8803mas.groupin.datastore.GroupDAO;
import com.cs8803mas.groupin.datastore.GroupJDODAO;

@SuppressWarnings("serial")
public class RemoveAllGroupsServlet extends HttpServlet {

	private static final GroupDAO GROUP_DAO = new GroupJDODAO();
	

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		GROUP_DAO.removeAllGroups();
		List<Group> groupList = GROUP_DAO.getAllGroups();
		resp.getWriter().write("group num:" + groupList.size());
	}
}
