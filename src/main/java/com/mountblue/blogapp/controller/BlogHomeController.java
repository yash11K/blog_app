package com.mountblue.blogapp.controller;

import com.mountblue.blogapp.model.Post;
import com.mountblue.blogapp.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
public class BlogHomeController extends AbstractBlogControl{
    @Autowired
    public BlogHomeController(PostService postService,
                              TagService tagService,
                              PostTagService postTagService,
                              CommentService commentService,
                              UserService userService,
                              SearchService searchService) {
        super(postService, tagService, postTagService, commentService, userService, searchService);
    }

    @GetMapping("/home")
    public String showHomePage(Model model,
                               @RequestParam(value = "orderBy", required = false)String orderBy,
                               @RequestParam(value = "rawQuery", required = false)String rawQuery,
                               RedirectAttributes redirectAttributes){
        List<Post> publishedPosts;
        List<Integer> postIdsQueryPosts;
        if (orderBy==null){
            redirectAttributes.addAttribute("orderBy", "dateDesc");
            return "redirect:/home";
        }

        if (rawQuery != null){
            postIdsQueryPosts = searchService.processSearchQuery(rawQuery);
            model.addAttribute("query", rawQuery);
        }
        else {
            postIdsQueryPosts = postService.findIdByPublished(true);
        }
        publishedPosts = postService.findPostsBySortType(orderBy, postIdsQueryPosts, true);
        model.addAttribute("sortType", orderBy);
        model.addAttribute("publishedPosts", publishedPosts);
        return "home";
    }

    @GetMapping("/orderBy")
    public String showHomePageWithOrderedPost(@RequestParam("orderBy")String orderBy, @RequestParam(value = "rawQuery", required = false)String rawQuery, RedirectAttributes redirectAttributes){
        if(rawQuery!=null) {
            redirectAttributes.addAttribute("rawQuery",rawQuery);
        }
        redirectAttributes.addAttribute("orderBy",orderBy);
        return "redirect:/home";
    }
}
