package com.mountblue.blogapp.service;

import com.mountblue.blogapp.model.Comment;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CommentService {

    void saveComment(Comment comment);
    List<Comment> getAllComments();
}
