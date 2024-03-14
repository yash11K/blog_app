package com.mountblue.blogapp.controller;

import com.mountblue.blogapp.model.Comment;
import com.mountblue.blogapp.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;

@Controller
public class CommentOperationController extends AbstractBlogControl{
    public CommentOperationController(PostService postService,
                                      TagService tagService,
                                      PostTagService postTagService,
                                      CommentService commentService,
                                      UserService userService) {
        super(postService, tagService, postTagService, commentService, userService);
    }

    @PostMapping("/blog/new-comment")
    public String postComment(@RequestParam("postId") Integer postId, @ModelAttribute("comments")Comment comment){
        comment.setAuthor(testUser);
        comment.setCreated_at(new Date());
        comment.setUpdated_at(new Date());
        comment.setPost(postService.getPostById(postId));
        commentService.saveComment(comment);
        String redirectURI = "/blog?postId=" + postId;
        return "redirect:" + redirectURI;
    }

}
