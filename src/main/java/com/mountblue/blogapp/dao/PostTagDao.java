package com.mountblue.blogapp.dao;

import com.mountblue.blogapp.model.PostTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Set;

@Repository
public interface PostTagDao extends JpaRepository<PostTag, PostTag.PostTagId> {
    @Modifying
    @Query(value = "DELETE FROM post_tags WHERE post_id = :postId", nativeQuery = true)
    void deleteByPostId(@Param("postId")int postId);

    @Query(value = "SELECT post_tags.tag_id FROM post_tags WHERE post_id IN :postIds", nativeQuery = true)
    Set<Integer> findTagIdsByPostIds(Collection<Integer> postIds);
}
