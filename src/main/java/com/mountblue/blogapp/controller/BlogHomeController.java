package com.mountblue.blogapp.controller;

import com.mountblue.blogapp.model.Post;
import com.mountblue.blogapp.service.PostService;
import com.mountblue.blogapp.service.PostTagService;
import com.mountblue.blogapp.service.TagService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class BlogHomeController extends AbstractBlogControl{
    public BlogHomeController(PostService postService, TagService tagService, PostTagService postTagService) {
        super(postService, tagService, postTagService);
    }

    @GetMapping("/home")
    public String showHomePage(Model model){
        List<Post> publishedPosts  = postService.getPostsByPublished(true);
        model.addAttribute("publishedPosts", publishedPosts);
        return "home";
    }
}
