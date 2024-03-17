package com.mountblue.blogapp.service;

import com.mountblue.blogapp.dao.PostDao;
import com.mountblue.blogapp.model.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
        int excerptLength = 80;
        return content.substring(0,excerptLength) + "...";
    }

    @Override
    public List<Post> findOrderedCustomPostsByPublished(List<Integer> postIds,boolean isPublished,boolean order) {
        if(order){
            return postService.findPostByIdInAndIsPublishedOrderByPublishedAtAsc(postIds ,isPublished);
        }
        else return postService.findPostByIdInAndIsPublishedOrderByPublishedAtDesc(postIds ,isPublished);
    }

    @Override
    public List<Post> findOrderedCustomPostsByTitle(List<Integer> postIds, boolean isPublished,boolean order) {
        if(order){
            return postService.findPostByIdInAndIsPublishedOrderByTitleAsc(postIds, isPublished);
        }
        else return postService.findPostByIdInAndIsPublishedOrderByTitleDesc(postIds, isPublished);
    }

    @Override
    public List<Integer> findIdByPublished(boolean isPublished) {
        return postService.findAllIdsByIsPublished(true);
    }

    @Override
    public List<Post> findPostByTitlePattern(String titlePattern) {
        return postService.findPostsByTitleContainingIgnoreCase(titlePattern);
    }

    @Override
    public List<Post> findPostByContentPattern(String contentPattern) {
        return postService.findPostsByContentContaining(contentPattern);
    }

    @Override
    public List<Post> findPostsBySortType(String sortType, List<Integer> postIds, boolean isPublished){
        return switch (sortType) {
            default -> findOrderedCustomPostsByPublished(postIds, isPublished, false) ;
            case "dateAsc" -> {
                yield findOrderedCustomPostsByPublished(postIds, isPublished, true);
            }
            case "dateDesc" -> {
                yield findOrderedCustomPostsByPublished(postIds, isPublished, false);
            }
            case "nameAsc" -> {
                yield findOrderedCustomPostsByTitle(postIds, isPublished, true);
            }
            case "nameDesc" -> {
                yield findOrderedCustomPostsByTitle(postIds, isPublished, false);
            }
        };
    }

    @Override
    public Date setDateToday() throws ParseException {
        DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        Date today = new Date();
        return  formatter.parse(formatter.format(today));
    }
}
