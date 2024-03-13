package com.mountblue.blogapp.service;

import com.mountblue.blogapp.model.Post;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface PostService{
    void savePost(Post post);
    List<Post> getPostsByPublished(Boolean isPublished);
    Post getPostById(int postId);
    @Transactional
    void deletePostById(int postId);
    static String createExcerpt(String content){
        int excerptLength = 30;
        return content.substring(0,excerptLength) + "....";
    }
}
