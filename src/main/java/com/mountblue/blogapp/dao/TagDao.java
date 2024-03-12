package com.mountblue.blogapp.dao;

import com.mountblue.blogapp.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagDao extends JpaRepository<Tag, Integer> {
    Tag findByName(String name);
}
