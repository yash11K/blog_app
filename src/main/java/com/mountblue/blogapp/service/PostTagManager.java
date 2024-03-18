package com.mountblue.blogapp.service;

import com.mountblue.blogapp.dao.PostTagDao;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostTagManager implements PostTagService{
    private final PostTagDao postTagService;

    @Autowired
    public PostTagManager(PostTagDao postTagService) {
        this.postTagService = postTagService;
    }

    @Override
    @Transactional
    public void deletePostTagRelationByPostId(Integer postId) {
        postTagService.deleteByPostId(postId);
    }

    @Override
    public List<Integer> findTagIdsByPostIds(List<Integer> postIds) {
        return postTagService.findTagIdsByPostIds(postIds);
    }
}
