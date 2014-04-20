package com.cs8803mas.groupin.datastore;

import java.util.List;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;

public class UserJDODAO implements UserDAO {

	private static final PersistenceManagerFactory pmfInstance = JDOHelper
			.getPersistenceManagerFactory("transactions-optional");

	public static PersistenceManagerFactory getPersistenceManagerFactory() {
		return pmfInstance;
	}

	@Override
	public int addUser(User user) {
		PersistenceManager pm = getPersistenceManagerFactory()
				.getPersistenceManager();
		String userName = user.getUsername();
		if (!isExistUser(userName)) {
			pm.makePersistent(user);
			return 0;
		} else
			return 1;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> listUser() {
		PersistenceManager pm = getPersistenceManagerFactory()
				.getPersistenceManager();
		String query = "select from " + User.class.getName();
		return (List<User>) pm.newQuery(query).execute();
	}

	@Override
	public User verifyUser(String username, String password) {
		PersistenceManager pm = getPersistenceManagerFactory()
				.getPersistenceManager();
		String query = "select from " + User.class.getName()
				+ " where username == '" + username + "'";
		@SuppressWarnings("unchecked")
		List<User> users = (List<User>) pm.newQuery(query).execute();
		if (users.isEmpty())
			return null;
		if (users.get(0).getPassword().equals(password))
			return users.get(0);
		return null;
	}

	@Override
	public boolean isExistUser(String username) {
		PersistenceManager pm = getPersistenceManagerFactory()
				.getPersistenceManager();
		String query = "select from " + User.class.getName()
				+ " where username == '" + username + "'";
		@SuppressWarnings("unchecked")
		List<User> users = (List<User>) pm.newQuery(query).execute();
		if (users.isEmpty()) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	public User getUserByName(String username) {
		PersistenceManager pm = getPersistenceManagerFactory()
				.getPersistenceManager();
		String query = "select from " + User.class.getName()
				+ " where username == '" + username + "'";
		@SuppressWarnings("unchecked")
		List<User> users = (List<User>) pm.newQuery(query).execute();
		if (users.isEmpty())
			return null;
		return users.get(0);
	}

	@Override
	public User getUserById(Long id) {
		PersistenceManager pm = getPersistenceManagerFactory()
				.getPersistenceManager();
		String query = "select from " + User.class.getName() + " where id == "
				+ id;
		@SuppressWarnings("unchecked")
		List<User> users = (List<User>) pm.newQuery(query).execute();
		if (users.isEmpty())
			return null;
		return users.get(0);
	}

}
