package com.mountblue.blogapp.dao;

import com.mountblue.blogapp.model.Post;
import com.mountblue.blogapp.model.Tag;
import com.mountblue.blogapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface PostDao extends JpaRepository<Post, Integer> {
    List<Post> findPostByIsPublished(boolean isPublished);

    void deleteById(int postId);

    @Query(value = "SELECT post_id FROM posts WHERE post_is_published = :isPublished", nativeQuery = true)
    List<Integer> findAllIdsByIsPublished(@Param("isPublished")boolean isPublished);

    @Query(value = "SELECT author_id FROM  posts WHERE post_is_published = :isPublished", nativeQuery = true)
    List<String> findAllPostsByPublished(@Param("isPublished")boolean isPublished);

    List<Post> findPostsByTitleContainingIgnoreCase(String titlePattern);

    List<Post> findPostsByContentContaining(String contentPattern);

    List<Post> findPostByIdInAndIsPublishedOrderByPublishedAtAsc(List<Integer> ids,boolean isPublished);

    List<Post> findPostByIdInAndIsPublishedOrderByPublishedAtDesc(List<Integer> ids,boolean isPublished);

    List<Post> findPostByIdInAndIsPublishedOrderByTitleDesc(List<Integer> ids, boolean isPublished);

    List<Post> findPostByIdInAndIsPublishedOrderByTitleAsc(List<Integer> ids, boolean isPublished);

    List<Post> findPostsByTagsIn(List<Tag> tags);

    List<Post> findPostsByAuthorIn(List<User> users);

    @Query(value = "SELECT posts.post_id FROM posts WHERE post_published_at BETWEEN :startDate AND :endDate", nativeQuery = true)
    List<Integer> findPostIdsByPublishedAtBetween(Date startDate, Date endDate);

}
