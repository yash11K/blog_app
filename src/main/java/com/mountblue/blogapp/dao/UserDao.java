package com.mountblue.blogapp.dao;

import com.mountblue.blogapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDao extends JpaRepository<User, Integer> {
    User findByName(String userName);
}
