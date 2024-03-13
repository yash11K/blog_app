package com.mountblue.blogapp.controller;

import com.mountblue.blogapp.service.PostService;
import com.mountblue.blogapp.service.PostTagService;
import com.mountblue.blogapp.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public abstract class AbstractBlogControl {
    final PostService postService;
    final TagService tagService;
    final PostTagService postTagService;
    @Autowired
    public AbstractBlogControl(PostService postService, TagService tagService, PostTagService postTagService) {
        this.postService = postService;
        this.tagService = tagService;
        this.postTagService = postTagService;
    }

}
