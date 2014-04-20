package com.cs8803mas.groupin.datastore;

import com.google.appengine.api.datastore.Blob;

public interface AvatarDAO {
	Blob getAvatarByUser(Long uid);
	boolean saveAvatar(Avatar avatar);
}
