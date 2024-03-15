package com.mountblue.blogapp.service;

import com.mountblue.blogapp.dao.PostDao;
import com.mountblue.blogapp.model.Comment;
import com.mountblue.blogapp.model.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

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
    public List<Post> findPostsByPublished(Boolean isPublished) {
        return postService.findPostByIsPublished(isPublished);
    }

    @Override
    public Post findPostById(int postId) {
        Optional<Post> isPost= postService.findById(postId);
        return isPost.orElseGet(Post::new);
    }

    @Override
    public void deletePostById(int postId) {
        postService.deleteById(postId);
    }
    static String createExcerpt(String content){
        int excerptLength = 30;
        return content.substring(0,excerptLength) + "....";
    }

    @Override
    public void deleteCommentRelation(Comment comment, Post post){
        Set<Comment> comments = post.getComments();
        comments.remove(comment);
    }
}
