package com.mountblue.blogapp.service;

import com.mountblue.blogapp.model.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    void saveUser(User user);
    User findUserByName(String userName);
    User findUserById(int id);
}
