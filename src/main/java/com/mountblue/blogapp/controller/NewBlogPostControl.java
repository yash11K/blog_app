package com.mountblue.blogapp.controller;

import com.mountblue.blogapp.model.Post;
import com.mountblue.blogapp.model.Tag;
import com.mountblue.blogapp.service.PostService;
import com.mountblue.blogapp.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/home")
public class NewBlogPostControl {
    @Value("${Tag.Topics}")
    private Set<String> TagTopics;

    @Autowired
    public NewBlogPostControl(PostService postService, TagService tagService) {
        this.postService = postService;
        this.tagService = tagService;
    }

    private final PostService postService;
    private final TagService tagService;

    @GetMapping("/blog-new")
    public String showHome(Model model){
        List<String> newPostTagNames = new ArrayList<>();
        boolean isTagMade = false;
        model.addAttribute("tagTopics",TagTopics);
        model.addAttribute("newPost", new Post());
        model.addAttribute("isTagMade", false);
        return "home";
    }

    @PostMapping("/blog-publish")
    public String publishBlog(@ModelAttribute("newPost") Post newPost, @RequestParam(value = "newPostTags")List<String> newPostTagNames){
        Set<Tag> newPostTags = new HashSet<>();
        for(String newPostTagName: newPostTagNames){
            Tag newPostTag = tagService.findByName(newPostTagName);
            System.out.println("<><><><>><><><><<<><><><><><" + newPostTag.toString());
            newPostTags.add(newPostTag);
        }
        System.out.println();
        newPost.setTags(newPostTags);
        newPost.setPublished(true);
        newPost.setExcerpt(newPost.getContent().substring(0,5) + ". . .");
        postService.save(newPost);
        System.out.println("saved ================= " + newPost.toString());
        return "redirect:/home/blog-new";
    }
}
