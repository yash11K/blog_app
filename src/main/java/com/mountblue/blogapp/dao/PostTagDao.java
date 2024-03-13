package com.mountblue.blogapp.dao;

import com.mountblue.blogapp.model.PostTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PostTagDao extends JpaRepository<PostTag, PostTag.PostTagId> {
    @Modifying
    @Query(value = "DELETE FROM post_tags WHERE post_id = :postId", nativeQuery = true)
    public void deleteByPostId(@Param("postId")int postId);
}
