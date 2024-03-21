package com.mountblue.blogapp.controller;

import com.mountblue.blogapp.model.Post;
import com.mountblue.blogapp.model.User;
import com.mountblue.blogapp.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.Collection;

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
        Collection<Post> allPostsPyAuthor =  postService.findPostsCreatedByAuthor(true, userService.findUserByUserName(principal.getName()).get());
        model.addAttribute("user", userService.findUserByUserName(principal.getName()));
        model.addAttribute("publishedPosts", allPostsPyAuthor);
        return "profilePage";
    }
}