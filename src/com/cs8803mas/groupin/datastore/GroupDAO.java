package com.cs8803mas.groupin.datastore;

import java.util.List;

public interface GroupDAO {
	void removeAllGroups();
	Group verifyGroup(String groupName, String passcode);
	Group getGroupById(Long id);
	Group getGroupByName(String groupName);
	List<Group> getAllGroups();
	boolean createGroup(Group group);
	boolean groupExists(String groupName);
}
