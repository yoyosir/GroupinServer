package com.cs8803mas.groupin.datastore;

import java.util.List;

public interface UserDAO {
	User verifyUser(String username, String password);
	User getUserByName(String username);
	User getUserById(Long id);
	int addUser(User user);
    List<User> listUser();
    boolean isExistUser(String username);
}
