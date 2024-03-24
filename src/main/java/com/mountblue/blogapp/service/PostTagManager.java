package com.mountblue.blogapp.service;

import com.mountblue.blogapp.dao.PostTagDao;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class PostTagManager implements PostTagService{
    private final PostTagDao postTagDao;

    @Autowired
    public PostTagManager(PostTagDao postTagDao) {
        this.postTagDao = postTagDao;
    }

    @Override
    @Transactional
    public void deletePostTagRelationByPostId(Integer postId) {
        postTagDao.deleteByPostId(postId);
    }

    @Override
    public Set<Integer> findTagIdsByPostIds(List<Integer> postIds) {
        return postTagDao.findTagIdsByPostIds(postIds);
    }
}
