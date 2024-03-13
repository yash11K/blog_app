package com.mountblue.blogapp.service;

import com.mountblue.blogapp.model.Post;
import org.springframework.stereotype.Service;

@Service
public interface PostService{
    void savePost(Post post);
}
