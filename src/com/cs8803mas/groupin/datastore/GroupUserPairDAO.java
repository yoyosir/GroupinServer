package com.cs8803mas.groupin.datastore;

import java.util.List;

public interface GroupUserPairDAO {
	boolean createPair(Long gid, Long uid);
	boolean removePair(Long gid, Long uid);
	boolean pairExists(Long gid, Long uid);
	List<GroupUserPair> getUsersInGroup(Long gid);
	List<GroupUserPair> getGroupsFromUser(Long uid);
	List<GroupUserPair> getAllPairs();
}
