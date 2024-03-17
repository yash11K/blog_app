package com.mountblue.blogapp.service;

import com.mountblue.blogapp.model.Post;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

@Service
public interface PostService{
    void savePost(Post post);
    List<Post> findPostsByPublished(Boolean isPublished);
    Post findPostById(int postId);
    @Transactional
    void deletePostById(int postId);
    static String createExcerpt(String content){
        int excerptLength = 50;
        return content.substring(0,excerptLength) + "....";
    }

    public List<Post> findOrderedCustomPostsByPublished(List<Integer> postIds,boolean isPublished,boolean order);

    public List<Post> findOrderedCustomPostsByTitle(List<Integer> postIds, boolean isPublished,boolean order);

    List<Integer> findIdByPublished(boolean isPublished);

    List<Post> findPostsBySortType(String sortType, List<Integer> postIds, boolean isPublished);

    public List<Post> findPostByTitlePattern(String titlePattern);

    public List<Post> findPostByContentPattern(String contentPattern);

    Date setDateToday() throws ParseException;
}
