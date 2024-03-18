package com.mountblue.blogapp.dao;

import com.mountblue.blogapp.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentDao extends JpaRepository<Comment, Integer> {
}
