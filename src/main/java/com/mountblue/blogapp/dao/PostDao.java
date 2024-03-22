package com.mountblue.blogapp.dao;

import com.mountblue.blogapp.model.Post;
import com.mountblue.blogapp.model.Tag;
import com.mountblue.blogapp.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Date;
import java.util.List;

@Repository
public interface PostDao extends JpaRepository<Post, Integer> {
    List<Post> findPostByIsPublished(boolean isPublished);

    void deleteById(int postId);

    @Query(value = "SELECT post_id FROM posts WHERE post_is_published = :isPublished", nativeQuery = true)
    List<Integer> findAllIdsByIsPublished(@Param("isPublished")boolean isPublished);

    List<Post> findPostsByTitleContainingIgnoreCase(String titlePattern);

    List<Post> findPostsByContentContaining(String contentPattern);

    Page<Post> findPostByIdInAndIsPublishedOrderByPublishedAtAsc(Collection<Integer> ids, boolean isPublished, Pageable pageable);

    Page<Post> findPostByIdInAndIsPublishedOrderByPublishedAtDesc(Collection<Integer> ids, boolean isPublished, Pageable pageable);

    Page<Post> findPostByIdInAndIsPublishedOrderByTitleDesc(Collection<Integer> ids, boolean isPublished, Pageable pageable);

    Page<Post> findPostByIdInAndIsPublishedOrderByTitleAsc(Collection<Integer> ids, boolean isPublished, Pageable pageable);

    List<Post> findPostsByTagsIn(Collection<Tag> tags);

    List<Post> findPostsByAuthorIn(Collection<User> users);

    @Query(value = "SELECT posts.post_id FROM posts WHERE post_published_at BETWEEN :startDate AND :endDate", nativeQuery = true)
    List<Integer> findPostIdsByPublishedAtBetween(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

    List<Post> findPostsByAuthor(User user);
}
