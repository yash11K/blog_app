package com.mountblue.blogapp.service;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public interface PostTagService {
    void deletePostTagRelationByPostId(Integer postId);

    Set<Integer> findTagIdsByPostIds(List<Integer> postIds);
}
