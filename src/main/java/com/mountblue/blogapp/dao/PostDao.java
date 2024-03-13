package com.mountblue.blogapp.dao;

import com.mountblue.blogapp.model.Post;
import com.mountblue.blogapp.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostDao extends JpaRepository<Post, Integer> {
    List<Post> getPostByIsPublished(boolean isPublished);
    void deleteById(Integer postId);
}
