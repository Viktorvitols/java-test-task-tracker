package com.app.services;

import com.app.dao.UserDao;
import com.app.model.Login;
import com.app.model.Registration;
import com.app.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    public boolean registerUser(Registration newUser) {
        if (userDao.checkEmail(newUser.getEmail())) {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            newUser.setPassword(encoder.encode(newUser.getPassword()));
            userDao.registerUser(newUser);
            return true;
        } else {
            System.out.println("FALSE");
            return false;
        }
    }

    public List<User> getUserList() {
        return userDao.getUserList();
    }

    public List<String> getUserNames() {
        List<String> usernames = new ArrayList<>();
        for (User user : getUserList()) {
            String username = user.getName();
            usernames.add(username);
        }
        return usernames;
    }

    public User loginUser(Login login) {
        return userDao.loginUser(login).get(0);
    }

}
