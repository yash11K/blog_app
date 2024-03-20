package com.mountblue.blogapp.service;

import com.mountblue.blogapp.model.Post;
import com.mountblue.blogapp.model.User;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    Page<Post> findOrderedCustomPostsByPublished(List<Integer> postIds, boolean isPublished, boolean order, Pageable pageable);

    Page<Post> findOrderedCustomPostsByTitle(List<Integer> postIds, boolean isPublished,boolean order, Pageable pageable);

    Page<Post> findPostsBySortType(String sortType, List<Integer> postIds, boolean isPublished, Pageable pageable);

    List<Integer> findIdByPublished(boolean isPublished);

    List<Post> findPostByTitlePattern(String titlePattern);

    List<Post> findPostByContentPattern(String contentPattern);

    List<Post> findPostsCreatedByAuthor(Boolean isPublished, User author);

    Date setDateToday() throws ParseException;
}
