package com.mountblue.blogapp.controller;

import com.mountblue.blogapp.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/user")
public class ProfilePageController extends AbstractBlogControl{
    private ServiceFactory serviceFactory;
    private PostService postService;
    private UserService userService;
    @Autowired
    public ProfilePageController(ServiceFactory serviceFactory) {
        this.serviceFactory = serviceFactory;
    }
    @ModelAttribute
    public void initService(){
        this.postService = serviceFactory.getPostService();
        this.userService = serviceFactory.getUserService();
    }

    @GetMapping("/profile")
    public String showUserProfile(Model model, Principal principal){
        model.addAttribute("user", userService.findUserByUserName(principal.getName()));
        model.addAttribute("publishedPosts", postService.findPostsCreatedByAuthorByPublished(true, userService.findUserByUserName(principal.getName()).get()));
        model.addAttribute("archivedPosts", postService.findPostsCreatedByAuthorByPublished(false, userService.findUserByUserName(principal.getName()).get()));
        return "profilePage";
    }
}