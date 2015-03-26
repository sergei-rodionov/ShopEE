package ru.sergeirodionov.shopee.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.sergeirodionov.shopee.dao.UserDao;
import ru.sergeirodionov.shopee.model.User;
import ru.sergeirodionov.shopee.model.UserRole;
import ru.sergeirodionov.shopee.service.UserService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service("UserService")
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao dao;

    @Override
    public void saveUser(User user) {
        dao.saveUser(user);
    }

    @Override
    public void saveUserRoles(Set<UserRole> roles) {
        dao.saveUserRoles(roles);
    }

    @Override
    public User findByUserName(String username) {
        return dao.findByUserName(username);
    }

    @Override
    public String listAllUsers_JSON() {
        List<User> list = dao.findAllUsers();
        StringBuilder sb = new StringBuilder();
        boolean isNextUser = false;
        for(User user : list) {

            StringBuilder roles = new StringBuilder();
            boolean isNext = false;
            for(UserRole role : user.getUserRole()) {
                if (isNext) {
                    roles.append(",");
                }
                isNext=true;
                roles.append("{\"role\":\""+role.getRole()+"\"}");
            }
            if (isNextUser) {
                sb.append(",");
            }
            isNextUser=true;
            sb.append("{\"username\":\""+user.getUsername()+"\",\"isenable\": \""+user.isEnabled()+"\", \"roles\":["+roles.toString()+"]}");
        }
        return "["+sb.toString()+"]";
    }

    @Override
    public void addNewUser_JSON(String username, String password, String roles) {
        User user = new User(username, password , true);
        Set<UserRole> userRoles = new HashSet<UserRole>();
        dao.saveUser(user);
        String[] uRoles = roles.split(" ");
        for (int i = 0; i < uRoles.length; i++) {
            UserRole userRole = new UserRole(user, uRoles[i]);
            userRoles.add(userRole);
        }
        user.setUserRole(userRoles);
        dao.saveUser(user);
        dao.saveUserRoles(userRoles);
    }

    @Override
    public void deleteUser_JSON(String username) {
        dao.deleteUserRoles(dao.findByUserName(username).getUserRole());
        dao.deleteUser(dao.findByUserName(username));
    }

    @Override
    public void changeUserPassword_JSON(String username, String newPassword) {
        User user = dao.findByUserName(username);
        user.setPassword(newPassword);
        dao.saveUser(user);
    }

    @Override
    public void switchUserStatus_JSON(String username) {
        User user = dao.findByUserName(username);
        user.setEnabled(!user.isEnabled());
        dao.updateUser(user.getUsername(), user);

    }
}
