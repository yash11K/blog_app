package com.mountblue.blogapp.service;

import com.mountblue.blogapp.model.User;
import com.mountblue.blogapp.security.WebUser;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface UserService extends UserDetailsService {
    void saveUser(WebUser webUser);
    User findUserByName(String userName);
    User findUserById(int id);

    int findUserIdByName(String name);

    List<User> findUsersLike(String userPattern);

    List<User> findUsersByNames(List<String> userNames);

    List<User> findAllUsers();

    Optional<User> findUserByUserName(String username);
}
