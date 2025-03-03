package com.scm.services;
import com.scm.Entity.User;
import java.util.List;

import java.util.Optional;

public interface Userservice{
User saveUser(User user);
Optional <User> getUserById(String id);
Optional <User> getUserByEmail(String email);
User updateUser(User user);
void deleteUser(User user);
boolean isUserExist(String id);
boolean isUserExistByEmail(User user);


List<User>getAllUser();

}
