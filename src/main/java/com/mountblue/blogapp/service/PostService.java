package com.mountblue.blogapp.service;

import com.mountblue.blogapp.model.Comment;
import com.mountblue.blogapp.model.Post;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PostService{
    void savePost(Post post);
    List<Post> findPostsByPublished(Boolean isPublished);
    Post findPostById(int postId);
    @Transactional
    void deletePostById(int postId);
    static String createExcerpt(String content){
        int excerptLength = 30;
        return content.substring(0,excerptLength) + "....";
    }
    void deleteCommentRelation(Comment comment, Post post);
}
