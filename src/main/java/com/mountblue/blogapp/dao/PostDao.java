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

    @Query(value = "SELECT posts.post_id FROM posts", nativeQuery = true)
    Collection<Integer> findAllIds();

    List<Post> findPostsByTitleContainingIgnoreCase(String titlePattern);

    List<Post> findPostsByContentContaining(String contentPattern);

    Page<Post> findPostByIdInAndIsPublishedOrderByPublishedAtAsc(Collection<Integer> ids, boolean isPublished, Pageable pageable);

    Page<Post> findPostByIdInOrderByPublishedAtAsc(Collection<Integer> ids, Pageable pageable);

    List<Post> findPostByIdInAndIsPublishedOrderByPublishedAtAsc(Collection<Integer> ids, boolean isPublished);

    List<Post> findPostsByIdInOrderByPublishedAtAsc(Collection<Integer> ids);

    Page<Post> findPostByIdInAndIsPublishedOrderByPublishedAtDesc(Collection<Integer> ids, boolean isPublished, Pageable pageable);

    Page<Post> findPostByIdInOrderByPublishedAtDesc(Collection<Integer> ids, Pageable pageable);

    List<Post> findPostByIdInAndIsPublishedOrderByPublishedAtDesc(Collection<Integer> ids, boolean isPublished);

    List<Post> findPostsByIdInOrderByPublishedAtDesc(Collection <Integer> ids);

    Page<Post> findPostByIdInAndIsPublishedOrderByTitleDesc(Collection<Integer> ids, boolean isPublished, Pageable pageable);

    Page<Post> findPostByIdInOrderByTitleDesc(Collection<Integer> ids, Pageable pageable);

    List<Post> findPostByIdInAndIsPublishedOrderByTitleDesc(Collection<Integer> ids, boolean isPublished);

    List<Post> findPostsByIdInOrderByTitleDesc(Collection <Integer> ids);

    Page<Post> findPostByIdInAndIsPublishedOrderByTitleAsc(Collection<Integer> ids, boolean isPublished, Pageable pageable);

    Page<Post> findPostByIdInOrderByTitleAsc(Collection<Integer> ids, Pageable pageable);

    List<Post> findPostByIdInAndIsPublishedOrderByTitleAsc(Collection<Integer> ids, boolean isPublished);

    List<Post> findPostsByIdInOrderByTitleAsc(Collection <Integer> ids);

    List<Post> findPostsByTagsIn(Collection<Tag> tags);

    List<Post> findPostsByAuthorIn(Collection<User> users);

    @Query(value = "SELECT posts.post_id FROM posts WHERE post_published_at BETWEEN :startDate AND :endDate", nativeQuery = true)
    List<Integer> findPostIdsByPublishedAtBetween(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

    List<Post> findPostsByAuthorAndIsPublished(User user, boolean isPublished);

    @Query(value = "FROM Post ORDER BY :orderBy", nativeQuery = false)
    List<Post> findPostsOrderBy(String orderBy);
}
