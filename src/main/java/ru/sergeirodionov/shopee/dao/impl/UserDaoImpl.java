package ru.sergeirodionov.shopee.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;
import ru.sergeirodionov.shopee.dao.AbstractDao;
import ru.sergeirodionov.shopee.dao.UserDao;
import ru.sergeirodionov.shopee.model.User;
import ru.sergeirodionov.shopee.model.UserRole;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Repository
public class UserDaoImpl extends AbstractDao implements UserDao {
    @Autowired
    private SessionFactory sessionFactory;


    @SuppressWarnings("unchecked")
    public User findByUserName(String username) {

        List<User> users = new ArrayList<User>();

        users = sessionFactory.getCurrentSession()
                .createQuery("from User where username=?")
                .setParameter(0, username)
                .list();

        if (users.size() > 0) {
            return users.get(0);
        } else {
            return null;
        }
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<User> findAllUsers() {
        Criteria criteria = getSession().createCriteria(User.class);
        return (List<User>) criteria.list();
    }


    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    @Override
    public void saveUser(User user) {
        persist(user);
    }

    @Override
    public void saveUserRoles(Set<UserRole> roles) {
        for(UserRole role: roles) {
            persist(role);
        }
    }

    @Override
    public User updateUser(String username, User newUser) {
        Session session = getSession();
        User user = findByUserName(username);
        user.setEnabled( newUser.isEnabled() );
        user.setPassword( newUser.getPassword() );
        user.setUserRole( newUser.getUserRole() );
        session.flush();
        return user;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Override
    public void deleteUser(User user) {
        delete(user);
    }

    @Override
    public void deleteUserRoles(Set<UserRole> roles) {
        for(UserRole role: roles) {
            delete(role);
        }
    }
}
