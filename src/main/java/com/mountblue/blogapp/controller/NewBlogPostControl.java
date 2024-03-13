package com.mountblue.blogapp.controller;

import com.mountblue.blogapp.model.Post;
import com.mountblue.blogapp.model.Tag;
import com.mountblue.blogapp.service.PostService;
import com.mountblue.blogapp.service.PostTagService;
import com.mountblue.blogapp.service.TagService;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RequestMapping("/home")
public class NewBlogPostControl extends AbstractBlogControl{
    @InitBinder
    public void initBinder(WebDataBinder webDataBinder){
        StringTrimmerEditor stringTrimmer = new StringTrimmerEditor(true);
        webDataBinder.registerCustomEditor(String.class, stringTrimmer);
    }

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
        Set<Tag> newPostTags = new HashSet<>();
        List<String> newPostTagNames = Arrays.stream(newPostTagNamesStr.split(",")).toList();
        for(String newPostTagName: newPostTagNames){
            newPostTagName = newPostTagName.trim();
            Boolean tagExists = tagService.tagExistsByName(newPostTagName);
            if(!tagExists){
                Tag newPostTag = new Tag();
                newPostTag.setName(newPostTagName);
                tagService.saveTag(newPostTag);
            }
            Tag newPostTag = tagService.findTagByName(newPostTagName);
            newPostTag.getPosts().add(newPost);
            newPostTags.add(newPostTag);
        }
        newPost.setTags(newPostTags);
        newPost.setPublished(true);
        newPost.setExcerpt(createExcerpt(newPost.getContent()));
        postService.savePost(newPost);
        return "redirect:/home/blog-new";
    }

    String createExcerpt(String content){
        int excerptLength = 30;
        return content.substring(0,excerptLength) + "....";
    }
}
