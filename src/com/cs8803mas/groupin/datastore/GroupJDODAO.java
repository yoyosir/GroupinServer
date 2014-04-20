package com.cs8803mas.groupin.datastore;

import java.util.List;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;


public class GroupJDODAO implements GroupDAO {

	private static final PersistenceManagerFactory pmfInstance = JDOHelper
			.getPersistenceManagerFactory("transactions-optional");

	public static PersistenceManagerFactory getPersistenceManagerFactory() {
		return pmfInstance;
	}

	@Override
	public Group verifyGroup(String groupName, String passcode) {
		PersistenceManager pm = getPersistenceManagerFactory()
				.getPersistenceManager();
		String query = "select from " + Group.class.getName()
				+ " where name == '" + groupName + "'";
		@SuppressWarnings("unchecked")
		List<Group> groups = (List<Group>) pm.newQuery(query).execute();
		if (groups.isEmpty())
			return null;
		if (groups.get(0).getPasscode().equals(passcode))
			return groups.get(0);
		return null;
	}

	@Override
	public Group getGroupById(Long id) {
		PersistenceManager pm = getPersistenceManagerFactory()
				.getPersistenceManager();
		String query = "select from " + Group.class.getName() + " where id == "
				+ id;
		@SuppressWarnings("unchecked")
		List<Group> groups = (List<Group>) pm.newQuery(query).execute();
		if (groups.isEmpty())
			return null;
		return groups.get(0);
	}

	@Override
	public boolean createGroup(Group group) {
		PersistenceManager pm = getPersistenceManagerFactory()
				.getPersistenceManager();
		boolean success = false;
		try {
			String groupName = group.getName();
			if (!groupExists(groupName)) {
				pm.makePersistent(group);
				GroupUserPairDAO groupUserPairDAO = new GroupUserPairJDODAO();
				if (groupUserPairDAO
						.createPair(group.getId(), group.getGuuid()))
					success = true;
			}
		} finally {
			pm.close();
		}
		if (success) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean groupExists(String groupName) {
		PersistenceManager pm = getPersistenceManagerFactory()
				.getPersistenceManager();
		String query = "select from " + Group.class.getName()
				+ " where name == '" + groupName + "'";
		@SuppressWarnings("unchecked")
		List<Group> groups = (List<Group>) pm.newQuery(query).execute();
		return !groups.isEmpty();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Group> getAllGroups() {
		PersistenceManager pm = getPersistenceManagerFactory()
				.getPersistenceManager();
		String query = "select from " + Group.class.getName();
		return (List<Group>) pm.newQuery(query).execute();
	}

	@Override
	public Group getGroupByName(String groupName) {
		PersistenceManager pm = getPersistenceManagerFactory()
				.getPersistenceManager();
		String query = "select from " + Group.class.getName()
				+ " where name == '" + groupName + "'";
		@SuppressWarnings("unchecked")
		List<Group> groups = (List<Group>) pm.newQuery(query).execute();
		if (groups.size() == 0) return null;
		return groups.get(0);
	}

	@Override
	public void removeAllGroups() {
		PersistenceManager pm = getPersistenceManagerFactory()
				.getPersistenceManager();
		List<Group> groupList = getAllGroups();
		try {
			pm.currentTransaction().begin();
			for (Group group : groupList) {
				pm.deletePersistent(group);
			}

			pm.currentTransaction().commit();
		} catch (Exception ex) {
			pm.currentTransaction().rollback();
			throw new RuntimeException(ex);
		} finally {
			pm.close();
		}
	}

}
