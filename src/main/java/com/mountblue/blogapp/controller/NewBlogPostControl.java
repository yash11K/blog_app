package com.mountblue.blogapp.controller;

import com.mountblue.blogapp.model.Post;
import com.mountblue.blogapp.model.Tag;
import com.mountblue.blogapp.model.User;
import com.mountblue.blogapp.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import static com.mountblue.blogapp.service.PostService.createExcerpt;

@Controller
@RequestMapping("/home")
public class NewBlogPostControl extends AbstractBlogControl{
    public NewBlogPostControl(PostService postService,
                              TagService tagService,
                              PostTagService postTagService,
                              CommentService commentService,
                              UserService userService) {
        super(postService, tagService, postTagService, commentService, userService);
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
    public String publishBlog(@ModelAttribute("newPost") Post newPost,
                              @RequestParam(value = "newPostTagNames")String newPostTagNamesStr){
        User testUser = new User();
        System.out.println(testUser);
        userService.saveUser(testUser);
        newPost.setAuthor(testUser);
        tagService.saveTagSetFromTagString(newPostTagNamesStr, newPost);
        newPost.setPublished(true);
        newPost.setExcerpt(createExcerpt(newPost.getContent()));
        postService.savePost(newPost);
        return "redirect:/home/blog-new";
    }
}
