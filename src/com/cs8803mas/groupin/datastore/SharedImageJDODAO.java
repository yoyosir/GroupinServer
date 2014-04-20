package com.cs8803mas.groupin.datastore;

import java.util.List;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;

public class SharedImageJDODAO implements SharedImageDAO {

	private static final PersistenceManagerFactory pmfInstance = JDOHelper
			.getPersistenceManagerFactory("transactions-optional");

	public static PersistenceManagerFactory getPersistenceManagerFactory() {
		return pmfInstance;
	}

	@Override
	public List<SharedImage> getImagesByGroup(Long gid) {
		PersistenceManager pm = getPersistenceManagerFactory()
				.getPersistenceManager();
		String query = "select from " + SharedImage.class.getName()
				+ " where gid == " + gid;
		@SuppressWarnings("unchecked")
		List<SharedImage> images = (List<SharedImage>) pm.newQuery(query).execute();
		return images;
	}

	@Override
	public boolean createImage(SharedImage image) {
		PersistenceManager pm = getPersistenceManagerFactory()
				.getPersistenceManager();
		try {
			pm.makePersistent(image);
			return true;
		} catch (Exception ex) {
			return false;
		}
	}


}
