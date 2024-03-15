package com.mountblue.blogapp.service;

import com.mountblue.blogapp.dao.UserDao;
import com.mountblue.blogapp.model.User;
import org.springframework.stereotype.Service;

@Service
public class UserManager implements UserService{
    private final UserDao userService;
    public UserManager(UserDao userService) {
        this.userService = userService;
    }
    @Override
    public void saveUser(User user){
        userService.save(user);
    }
    @Override
    public User findUserByName(String userName) {
        return userService.findByName(userName);
    }

    @Override
    public User findUserById(int id) {
        return userService.findById(id);
    }
}
