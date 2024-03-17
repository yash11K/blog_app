package com.mountblue.blogapp.service;

import com.mountblue.blogapp.dao.UserDao;
import com.mountblue.blogapp.model.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

    @Override
    public int findUserIdByName(String name) {
        User user = findUserByName(name);
        return user.getId();
    }

    public List<User> findUserList(List<Integer> ids){
        List<User> users = new ArrayList<>();
        for(int id : ids){
            users.add(findUserById(id));
        }
        return users;
    }

    public List<User> findUsersLike(String userPattern){
        return userService.findUserByNameLike(userPattern);
    }

    @Override
    public List<User> findUsersByNames(List<String> userNames) {
        List<User> users = new ArrayList<>();
        for(String userName : userNames){
            User user = findUserByName(userName);
            users.add(user);
        }
        return users;
    }

    @Override
    public List<User> findAllUsers(){
        return userService.findAll();
    }
}
