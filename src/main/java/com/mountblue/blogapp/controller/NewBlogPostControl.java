package com.mountblue.blogapp.controller;

import com.mountblue.blogapp.model.Post;
import com.mountblue.blogapp.model.Tag;
import com.mountblue.blogapp.service.PostService;
import com.mountblue.blogapp.service.PostTagService;
import com.mountblue.blogapp.service.TagService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import static com.mountblue.blogapp.service.PostService.createExcerpt;

@Controller
@RequestMapping("/home")
public class NewBlogPostControl extends AbstractBlogControl{
    public NewBlogPostControl(PostService postService, TagService tagService, PostTagService postTagService) {
        super(postService, tagService, postTagService);
    }

    @GetMapping("/blog-new")
    public String showHome(Model model){
        String tags = "";
        model.addAttribute("tags", tags);
        model.addAttribute("newPost", new Post());
        model.addAttribute("isTagMade", false);
        return "newBlogPost";
    }

    @PostMapping("/blog-publish")
    public String publishBlog(@ModelAttribute("newPost") Post newPost, @RequestParam(value = "newPostTagNames")String newPostTagNamesStr){
        tagService.saveTagSetFromTagString(newPostTagNamesStr, newPost);
        newPost.setPublished(true);
        newPost.setExcerpt(createExcerpt(newPost.getContent()));
        postService.savePost(newPost);
        return "redirect:/home/blog-new";
    }

}
