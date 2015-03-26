package ru.sergeirodionov.shopee.service;

import ru.sergeirodionov.shopee.model.User;
import ru.sergeirodionov.shopee.model.UserRole;

import java.util.Set;

public interface UserService {
    User findByUserName(String username);

    //public User getUserById(Integer id);

    public String listAllUsers_JSON();
    public void addNewUser_JSON(String username, String password, String roles);
    public void deleteUser_JSON(String username);
    public void changeUserPassword_JSON(String username, String newPassword);
    public void switchUserStatus_JSON(String username);

    public void saveUser(User user);
    public void saveUserRoles(Set<UserRole> roles);
    //public void deleteUser(User user);
}
