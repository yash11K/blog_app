package com.mountblue.blogapp.controller;

import com.mountblue.blogapp.model.Post;
import com.mountblue.blogapp.model.Tag;
import com.mountblue.blogapp.service.PostService;
import com.mountblue.blogapp.service.PostTagService;
import com.mountblue.blogapp.service.TagService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/home")
public class NewBlogPostControl extends AbstractBlogControl{
    @Value("${Tag.Topics}")
    private Set<String> TagTopics;

    public NewBlogPostControl(PostService postService, TagService tagService, PostTagService postTagService) {
        super(postService, tagService, postTagService);
    }

    @GetMapping("/blog-new")
    public String showHome(Model model){
        Tag tags= tagService.findTagByName("Art");
        System.out.println(tags.getPosts().toString());
        model.addAttribute("tagTopics",TagTopics);
        model.addAttribute("newPost", new Post());
        model.addAttribute("isTagMade", false);
        return "home";
    }

    @PostMapping("/blog-publish")
    public String publishBlog(@ModelAttribute("newPost") Post newPost, @RequestParam(value = "newPostTags")List<String> newPostTagNames){
        Set<Tag> newPostTags = new HashSet<>();
        for(String newPostTagName: newPostTagNames){
            Tag newPostTag = tagService.findTagByName(newPostTagName);
            newPostTag.getPosts().add(newPost);
            newPostTags.add(newPostTag);
        }
        System.out.println();
        newPost.setTags(newPostTags);
        newPost.setPublished(true);
        newPost.setExcerpt(newPost.getContent().substring(0,5) + ". . .");
        postService.savePost(newPost);
        return "redirect:/home/blog-new";
    }
}
