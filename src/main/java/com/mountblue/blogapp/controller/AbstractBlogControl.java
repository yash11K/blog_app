package com.mountblue.blogapp.controller;

import com.mountblue.blogapp.model.Comment;
import com.mountblue.blogapp.model.Post;
import com.mountblue.blogapp.model.Tag;
import com.mountblue.blogapp.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import java.util.List;
import java.util.Set;

@Controller
public abstract class AbstractBlogControl {
    final PostService postService;
    final TagService tagService;
    final PostTagService postTagService;
    final CommentService commentService;
    final UserService userService;
    final SearchService searchService;
    final FilterService filterService;

    @Autowired
    public AbstractBlogControl(PostService postService,
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

    static void addModelAttributeOfFullBlog(Model model, Post post, List<Comment> allCommentsOfPost) {
        Set<Tag> tags = post.getTags();
        Comment comment = new Comment();
        model.addAttribute("post", post);
        model.addAttribute("postedComments", allCommentsOfPost);
        model.addAttribute("postTags", tags);
        model.addAttribute("comments", comment);
    }
}