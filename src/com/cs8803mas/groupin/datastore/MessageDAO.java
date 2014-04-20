package com.cs8803mas.groupin.datastore;

import java.util.List;

public interface MessageDAO {
	boolean createMessage(Long gid, Long uid, String content);
	List<Message> getMessagesForGroup(Long gid);
	List<Message> getAllMessages();
}
