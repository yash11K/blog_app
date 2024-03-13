package com.mountblue.blogapp.service;

import org.springframework.stereotype.Service;

@Service
public interface PostTagService {
    void deletePostTagRelationByPostId(Integer postId);
}
