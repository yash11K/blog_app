package com.mountblue.blogapp.controller;

import com.mountblue.blogapp.model.Comment;
import com.mountblue.blogapp.model.Post;
import com.mountblue.blogapp.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
public class FullBlogController extends AbstractBlogControl{
    private final ServiceFactory serviceFactory;
    private CommentService commentService;
    private PostService postService;
    @Autowired
    public FullBlogController(ServiceFactory serviceFactory) {
        this.serviceFactory = serviceFactory;
    }
    @ModelAttribute
    public void initService(){
        this.commentService = serviceFactory.getCommentService();
        this.postService = serviceFactory.getPostService();
    }

    @GetMapping("/blog")
    public String showFullBlog(@RequestParam("postId") Integer postId, Model model, Principal principal){
        Post post = postService.findPostById(postId);
        addModelAttributeOfFullBlog(model, post, commentService.findAllCommentsOfPost(post));
        model.addAttribute("principal", principal);
        return "fullBlog";
    }
}
