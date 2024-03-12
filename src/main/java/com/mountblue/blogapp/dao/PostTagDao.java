package com.mountblue.blogapp.dao;

import com.mountblue.blogapp.model.PostTag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostTagDao extends JpaRepository<PostTag, PostTag.PostTagId> {
}
