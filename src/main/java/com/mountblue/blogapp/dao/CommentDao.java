package com.mountblue.blogapp.dao;

import com.mountblue.blogapp.model.Comment;
import com.mountblue.blogapp.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentDao extends JpaRepository<Comment, Integer> {
    List<Comment> findAllByPost(Post post);
}
