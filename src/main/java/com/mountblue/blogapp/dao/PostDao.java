package com.mountblue.blogapp.dao;

import com.mountblue.blogapp.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostDao extends JpaRepository<Post, Integer> {
    List<Post> findPostByIsPublished(boolean isPublished);

    void deleteById(int postId);

    @Query(value = "SELECT post_id FROM posts WHERE post_is_published = :isPublished", nativeQuery = true)
    List<Integer> findAllIdsByIsPublished(@Param("isPublished")boolean post_is_published);

    List<Post> findPostsByTitleContainingIgnoreCase(String titlePattern);

    List<Post> findPostByIdInAndIsPublishedOrderByPublishedAtAsc(List<Integer> ids,boolean isPublished);

    List<Post> findPostByIdInAndIsPublishedOrderByPublishedAtDesc(List<Integer> ids,boolean isPublished);

    List<Post> findPostByIdInAndIsPublishedOrderByTitleDesc(List<Integer> ids, boolean isPublished);

    List<Post> findPostByIdInAndIsPublishedOrderByTitleAsc(List<Integer> ids, boolean isPublished);
}
