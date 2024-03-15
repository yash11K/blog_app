package com.mountblue.blogapp.dao;

import com.mountblue.blogapp.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostDao extends JpaRepository<Post, Integer> {
    List<Post> findPostByIsPublished(boolean isPublished);
    void deleteById(Integer postId);
}
