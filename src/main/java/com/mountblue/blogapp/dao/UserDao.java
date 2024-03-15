package com.mountblue.blogapp.dao;

import com.mountblue.blogapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends JpaRepository<User, Integer> {
    User findByName(String userName);
    User findById(int id);
}
