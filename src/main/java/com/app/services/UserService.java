package com.app.services;

import com.app.dao.UserDao;
import com.app.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    public List<User> getUserList() {
        return userDao.getUserList();
    }

    public List<String> getUserNames() {
        List<String> usernames =  new ArrayList<>();
        for (User user : getUserList()) {
            String username = user.getName();
            usernames.add(username);
        }
        return usernames;
    }
}
