package com.mountblue.blogapp.service;

import com.mountblue.blogapp.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    void saveUser(User user);
    User findUserByName(String userName);
    User findUserById(int id);

    int findUserIdByName(String name);

    List<User> findUsersLike(String userPattern);

    List<User> findUsersByNames(List<String> userNames);

    List<User> findAllUsers();
}
