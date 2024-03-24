package com.mountblue.blogapp.service;

import com.mountblue.blogapp.model.Post;
import com.mountblue.blogapp.model.User;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public interface PostService{
    void savePost(Post post);
    List<Post> findPostsByPublished(Boolean isPublished);
    Optional<Post> findPostById(int postId);
    @Transactional
    void deletePostById(int postId);
    static String createExcerpt(String content){
        int excerptLength = 50;
        return content.substring(0,excerptLength) + "....";
    }

    Page<Post> findOrderedCustomPostsByPublishedAt(Collection<Integer> postIds, boolean isPublished, boolean order, Pageable pageable);

    List<Post> findOrderedCustomPostsByPublishedAt(Collection<Integer> postIds, boolean isPublished, boolean order);

    List<Post> findOrderedCustomPostsByPublishedAt(Collection<Integer> postIds, boolean order);

    Page<Post> findOrderedCustomPostsByTitle(Collection<Integer> postIds, boolean isPublished, boolean order, Pageable pageable);

    List<Post> findOrderedCustomPostsByTitle(Collection<Integer> postIds, boolean isPublished, boolean order);

    Page<Post> findPostsBySortType(String sortType, Collection<Integer> postIds, boolean isPublished, Pageable pageable);

    List<Post> findOrderedCustomPostsByTitle(Collection<Integer> postIds, boolean order);

    List<Integer> findIdByPublished(boolean isPublished);

    List<Post> findPostByTitlePattern(String titlePattern);

    List<Post> findPostByContentPattern(String contentPattern);

    List<Post> findPostsBySortType(String sortType, Collection<Integer> postIds, boolean isPublished);

    List<Post> findPostsBySortType(String sortType, Collection<Integer> postIds);

    List<Post> findPostsCreatedByAuthorByPublished(Boolean isPublished, User author);

    Date setDateToday() throws ParseException;

    List<Post> findPostsOrderBy(String orderBy);

    Collection<Integer> findAllIds();

    List<Post> findAllPosts();
}
