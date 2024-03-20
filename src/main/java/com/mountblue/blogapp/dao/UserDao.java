package com.mountblue.blogapp.dao;

import com.mountblue.blogapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserDao extends JpaRepository<User, Integer> {
    User findByName(String userName);

    User findById(int id);

    List<User> findUserByNameIn(List<String> userNames);

    List<User> findUserByNameLike(String name);

    Optional<User> findUserByUsername(String username);

}
