package com.mountblue.blogapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ManagerFactory implements ServiceFactory{
    final PostService postService;
    final TagService tagService;
    final PostTagService postTagService;
    final CommentService commentService;
    final UserService userService;
    final SearchService searchService;
    final FilterService filterService;

    @Autowired
    public ManagerFactory(PostService postService,
                          TagService tagService,
                          PostTagService postTagService,
                          CommentService commentService,
                          UserService userService,
                          SearchService searchService,
                          FilterService filterService) {
        this.postService = postService;
        this.tagService = tagService;
        this.postTagService = postTagService;
        this.commentService = commentService;
        this.userService = userService;
        this.searchService = searchService;
        this.filterService = filterService;
    }

    @Override
    public PostService getPostService() {
        return postService;
    }

    @Override
    public TagService getTagService() {
        return tagService;
    }

    @Override
    public PostTagService getPostTagService() {
        return postTagService;
    }

    @Override
    public CommentService getCommentService() {
        return commentService;
    }

    @Override
    public UserService getUserService() {
        return userService;
    }

    @Override
    public SearchService getSearchService() {
        return searchService;
    }

    @Override
    public FilterService getFilterService() {
        return filterService;
    }
}
