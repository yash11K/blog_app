package com.mountblue.blogapp.controller;

import com.mountblue.blogapp.model.Post;
import com.mountblue.blogapp.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public String showHomePage(Model model,
                               @RequestParam(value = "orderBy", required = false)String orderBy,
                               RedirectAttributes redirectAttributes){
        if (orderBy == null) {
            redirectAttributes.addAttribute("orderBy", "dateDsc");
            return "redirect:/home";
        }
        List<Post> publishedPosts;
        publishedPosts = postService.getPostsBySortType(orderBy, true);
        model.addAttribute("sortType", orderBy);
        model.addAttribute("publishedPosts", publishedPosts);
        return "home";
    }

    @GetMapping("/orderBy")
    public String showHomePageWithOrderedPost(@RequestParam("orderBy")String orderBy, Model model){
        return "redirect:/home?orderBy=" + orderBy;
    }
}
