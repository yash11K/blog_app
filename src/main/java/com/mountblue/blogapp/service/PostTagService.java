package com.mountblue.blogapp.service;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PostTagService {
    void deletePostTagRelationByPostId(Integer postId);

    List<Integer> findTagIdsByPostIds(List<Integer> postIds);
}
