package com.cs8803mas.groupin.datastore;

import java.util.List;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;

public class GroupUserPairJDODAO implements GroupUserPairDAO {

	private static final PersistenceManagerFactory pmfInstance = JDOHelper
			.getPersistenceManagerFactory("transactions-optional");

	public static PersistenceManagerFactory getPersistenceManagerFactory() {
		return pmfInstance;
	}

	@Override
	public boolean createPair(Long gid, Long uid) {
		if (pairExists(gid, uid))
			return true;
		GroupUserPair pair = new GroupUserPair(gid, uid);
		PersistenceManager pm = getPersistenceManagerFactory()
				.getPersistenceManager();
		pm.makePersistent(pair);
		return true;
	}

	@Override
	public boolean removePair(Long gid, Long uid) {
		PersistenceManager pm = getPersistenceManagerFactory()
				.getPersistenceManager();
		String query = "select from " + GroupUserPair.class.getName()
				+ " where gid == " + gid + " && uid == " + uid;
		@SuppressWarnings("unchecked")
		List<GroupUserPair> pairs = (List<GroupUserPair>) pm.newQuery(query)
				.execute();
		if (pairs.isEmpty())
			return false;
		pm.deletePersistent(pairs.get(0));
		return true;
	}

	@Override
	public boolean pairExists(Long gid, Long uid) {
		PersistenceManager pm = getPersistenceManagerFactory()
				.getPersistenceManager();
		String query = "select from " + GroupUserPair.class.getName()
				+ " where gid == " + gid + " && uid == " + uid;
		@SuppressWarnings("unchecked")
		List<GroupUserPair> pairs = (List<GroupUserPair>) pm.newQuery(query)
				.execute();
		return pairs.isEmpty();
	}

	@Override
	public List<GroupUserPair> getUsersInGroup(Long gid) {
		PersistenceManager pm = getPersistenceManagerFactory()
				.getPersistenceManager();
		String query = "select from " + GroupUserPair.class.getName()
				+ " where gid == " + gid;
		@SuppressWarnings("unchecked")
		List<GroupUserPair> pairs = (List<GroupUserPair>) pm.newQuery(query)
				.execute();
		return pairs;
	}

	@Override
	public List<GroupUserPair> getGroupsFromUser(Long uid) {
		PersistenceManager pm = getPersistenceManagerFactory()
				.getPersistenceManager();
		String query = "select from " + GroupUserPair.class.getName()
				+ " where uid == " + uid;
		@SuppressWarnings("unchecked")
		List<GroupUserPair> pairs = (List<GroupUserPair>) pm.newQuery(query)
				.execute();
		return pairs;
	}

	@Override
	public List<GroupUserPair> getAllPairs() {
		PersistenceManager pm = getPersistenceManagerFactory()
				.getPersistenceManager();
		String query = "select from " + GroupUserPair.class.getName();
		@SuppressWarnings("unchecked")
		List<GroupUserPair> pairs = (List<GroupUserPair>) pm.newQuery(query)
				.execute();
		return pairs;
	}

}
