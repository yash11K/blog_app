package com.mountblue.blogapp.dao;

import com.mountblue.blogapp.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TagDao extends JpaRepository<Tag, Integer> {
    Boolean existsByName(String name);
    Tag findByName(String name);
}
