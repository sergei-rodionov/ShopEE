package ru.sergeirodionov.shopee.dao;

import ru.sergeirodionov.shopee.model.User;
import ru.sergeirodionov.shopee.model.UserRole;

import java.util.List;
import java.util.Set;

public interface UserDao {
    User findByUserName(String username);

    public List<User> findAllUsers();

    public User updateUser(String username, User newUser);
    public void saveUser(User user);
    public void saveUserRoles(Set<UserRole> roles);
    public void deleteUser(User user);
    public void deleteUserRoles(Set<UserRole> roles);
}
