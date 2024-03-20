package com.mountblue.blogapp.controller;

import com.mountblue.blogapp.model.Post;
import com.mountblue.blogapp.model.User;
import com.mountblue.blogapp.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.Collection;

@Controller
@RequestMapping("/user")
public class ProfilePageController extends AbstractBlogControl{
    public ProfilePageController(PostService postService,
                                 TagService tagService,
                                 PostTagService postTagService,
                                 CommentService commentService,
                                 UserService userService,
                                 SearchService searchService,
                                 FilterService filterService) {
        super(postService, tagService, postTagService, commentService, userService, searchService, filterService);
    }

    @GetMapping("/profile")
    public String showUserProfile(Model model, Principal principal){
        Collection<Post> allPostsPyAuthor =  postService.findPostsCreatedByAuthor(true, userService.findUserByUserName(principal.getName()).get());
        model.addAttribute("publishedPosts", allPostsPyAuthor);
        return "profilePage";
    }
}