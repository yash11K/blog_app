package com.mountblue.blogapp.service;

import com.mountblue.blogapp.dao.PostDao;
import com.mountblue.blogapp.model.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostManager implements PostService{
    private final PostDao postService;
    @Autowired
    public PostManager(PostDao postService) {
        this.postService = postService;
    }
    public void savePost(Post post){
        postService.save(post);
    }
}
