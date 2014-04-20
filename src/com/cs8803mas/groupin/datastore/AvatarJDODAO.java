package com.cs8803mas.groupin.datastore;

import java.util.List;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;

import com.google.appengine.api.datastore.Blob;

public class AvatarJDODAO implements AvatarDAO {

	private static final PersistenceManagerFactory pmfInstance = JDOHelper
			.getPersistenceManagerFactory("transactions-optional");

	public static PersistenceManagerFactory getPersistenceManagerFactory() {
		return pmfInstance;
	}

	@Override
	public Blob getAvatarByUser(Long uid) {
		PersistenceManager pm = pmfInstance.getPersistenceManager();
		String query = "select from " + Avatar.class.getName()
				+ " where uid == " + uid;
		@SuppressWarnings("unchecked")
		List<Avatar> avatars = (List<Avatar>) pm.newQuery(query).execute();
		if (avatars != null && avatars.size() >= 1)
			return avatars.get(0).getBlob();
		return null;
	}

	@Override
	public boolean saveAvatar(Avatar avatar) {
		PersistenceManager pm = pmfInstance.getPersistenceManager();
		String query = "select from " + Avatar.class.getName()
				+ " where uid == " + avatar.getUid();
		@SuppressWarnings("unchecked")
		List<Avatar> avatars = (List<Avatar>) pm.newQuery(query).execute();
		if (avatars != null) {
			for (Avatar avatarInstance : avatars) {
				pm.deletePersistent(avatarInstance);
			}
		}
		try {
			System.out.println("Before makePersistent");
			pm.makePersistent(avatar);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return true;
	}

}
