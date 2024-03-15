package com.mountblue.blogapp.dao;

import com.mountblue.blogapp.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface PostDao extends JpaRepository<Post, Integer> {
    List<Post> findPostByIsPublished(boolean isPublished);
    void deleteById(int postId);
    List<Post> findPostByIsPublishedOrderByPublishedAtDesc(boolean isPublished);
    List<Post> findPostByIsPublishedOrderByPublishedAtAsc(boolean isPublished);
    List<Post> findPostByIsPublishedOrderByTitleAsc(boolean isPublished);
    List<Post> findPostByIsPublishedOrderByTitleDesc(boolean isPublished);
}
