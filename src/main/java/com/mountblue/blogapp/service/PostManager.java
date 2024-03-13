package com.mountblue.blogapp.service;

import com.mountblue.blogapp.dao.PostDao;
import com.mountblue.blogapp.model.Post;
import com.mountblue.blogapp.model.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    @Override
    public List<Post> getPostsByPublished(Boolean isPublished) {
        return postService.getPostByIsPublished(isPublished);
    }

    @Override
    public Post getPostById(int postId) {
        Optional<Post> isPost= postService.findById(postId);
        return isPost.orElseGet(Post::new);
    }
}
