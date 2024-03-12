package com.mountblue.blogapp.service;

import com.mountblue.blogapp.model.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostManager{
    private final PostService postService;
    @Autowired
    public PostManager(PostService postService) {
        this.postService = postService;
    }
    public void savePost(Post post){
        postService.save(post);
    }
}
