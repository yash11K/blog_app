package com.mountblue.blogapp.controller;

import com.mountblue.blogapp.model.Comment;
import com.mountblue.blogapp.model.User;
import com.mountblue.blogapp.service.*;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

public abstract class AbstractBlogControl {
    final PostService postService;
    final TagService tagService;
    final PostTagService postTagService;
    final CommentService commentService;
    final UserService userService;

    @Autowired
    public AbstractBlogControl(PostService postService, TagService tagService, PostTagService postTagService, CommentService commentService, UserService userService) {
        this.postService = postService;
        this.tagService = tagService;
        this.postTagService = postTagService;
        this.commentService = commentService;
        this.userService = userService;
    }
    User testUser = new User();
}