package com.mountblue.blogapp.service;

import com.mountblue.blogapp.dao.CommentDao;
import com.mountblue.blogapp.model.Comment;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentManager implements CommentService{
    private final CommentDao commentService;

    public CommentManager(CommentDao commentService) {
        this.commentService = commentService;
    }
    @Override
    public void saveComment(Comment comment){
        commentService.save(comment);
    }

    @Override
    public List<Comment> getAllComments() {
        return commentService.findAll();
    }
}
