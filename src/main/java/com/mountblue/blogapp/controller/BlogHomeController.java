package com.mountblue.blogapp.controller;

import com.mountblue.blogapp.model.Post;
import com.mountblue.blogapp.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class BlogHomeController extends AbstractBlogControl{
    public BlogHomeController(PostService postService,
                              TagService tagService,
                              PostTagService postTagService,
                              CommentService commentService,
                              UserService userService) {
        super(postService, tagService, postTagService, commentService, userService);
    }

    @GetMapping("/home")
    public String showHomePage(Model model){
        List<Post> publishedPosts  = postService.findPostsByPublished(true);
        model.addAttribute("publishedPosts", publishedPosts);
        return "home";
    }
}
