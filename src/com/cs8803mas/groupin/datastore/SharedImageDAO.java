package com.cs8803mas.groupin.datastore;

import java.util.List;

public interface SharedImageDAO {
	List<SharedImage> getImagesByGroup(Long gid);
	boolean createImage(SharedImage image);
}
