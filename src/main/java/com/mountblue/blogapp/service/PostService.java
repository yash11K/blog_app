package com.mountblue.blogapp.service;

import com.mountblue.blogapp.model.Post;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface PostService{
    void savePost(Post post);
    List<Post> getPostsByPublished(Boolean isPublished);
    Post getPostById(int postId);
}
