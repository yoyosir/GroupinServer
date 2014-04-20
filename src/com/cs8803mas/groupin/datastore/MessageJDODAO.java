package com.cs8803mas.groupin.datastore;

import java.util.List;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;

public class MessageJDODAO implements MessageDAO {

	private static final PersistenceManagerFactory pmfInstance = JDOHelper
			.getPersistenceManagerFactory("transactions-optional");

	public static PersistenceManagerFactory getPersistenceManagerFactory() {
		return pmfInstance;
	}

	@Override
	public boolean createMessage(Long gid, Long uid, String content) {
		Message message = new Message(gid, uid, content);
		PersistenceManager pm = getPersistenceManagerFactory()
				.getPersistenceManager();
		pm.makePersistent(message);
		return true;
	}

	@Override
	public List<Message> getMessagesForGroup(Long gid) {
		PersistenceManager pm = getPersistenceManagerFactory()
				.getPersistenceManager();
		String query = "select from " + Message.class.getName()
				+ " where gid == " + gid;
		@SuppressWarnings("unchecked")
		List<Message> messages = (List<Message>) pm.newQuery(query).execute();
		return messages;
	}

	@Override
	public List<Message> getAllMessages() {
		PersistenceManager pm = getPersistenceManagerFactory()
				.getPersistenceManager();
		String query = "select from " + Message.class.getName();
//				+ " where gid == " + gid;
		@SuppressWarnings("unchecked")
		List<Message> messages = (List<Message>) pm.newQuery(query).execute();
		return messages;
	}

}
