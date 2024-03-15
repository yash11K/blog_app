package com.mountblue.blogapp.service;

import com.mountblue.blogapp.dao.PostDao;
import com.mountblue.blogapp.model.Post;
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
    public List<Post> findOrderedPostByPublished(boolean isPublished, boolean order) {
        if(order){
            return postService.findPostByIsPublishedOrderByPublishedAtAsc(isPublished);
        }
        else return postService.findPostByIsPublishedOrderByPublishedAtDesc(isPublished);
    }

    @Override
    public List<Post> findOrderedPostByName(boolean order) {
        if(order){
            return postService.findPostByIsPublishedOrderByTitleAsc(true);
        }
        else return postService.findPostByIsPublishedOrderByTitleDesc(true);
    }

    @Override
    public List<Post> getPostsBySortType(String sortType, boolean isPublished){
        return switch (sortType) {
            default -> findOrderedPostByPublished(isPublished,false);
            case "dateAsc" -> {
                yield findOrderedPostByPublished(isPublished,true);
            }
            case "dateDesc" -> {
                yield findOrderedPostByPublished(isPublished,false);
            }
            case "nameAsc" -> {
                yield findOrderedPostByName(true);
            }
            case "nameDesc" -> {
                yield findOrderedPostByName(false);
            }
        };
    }
}
