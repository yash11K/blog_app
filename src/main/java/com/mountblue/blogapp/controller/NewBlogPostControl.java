package com.mountblue.blogapp.controller;

import com.mountblue.blogapp.model.Post;
import com.mountblue.blogapp.model.User;
import com.mountblue.blogapp.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.Date;

import static com.mountblue.blogapp.service.PostService.createExcerpt;

@Controller
@RequestMapping("/home")
public class NewBlogPostControl extends AbstractBlogControl{
    public NewBlogPostControl(PostService postService,
                              TagService tagService,
                              PostTagService postTagService,
                              CommentService commentService,
                              UserService userService,
                              SearchService searchService,
                              FilterService filterService) {
        super(postService,
                tagService,
                postTagService,
                commentService,
                userService,
                searchService,
                filterService);
    }
    private final String blogActionPublish = "Publish";
    private final String blogActionDraft = "SaveAsDraft";

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
                              @RequestParam(value = "newPostTagNames")String newPostTagNamesStr,
                              @RequestParam("blogAction")String blogAction) throws ParseException {
        User testUser = userService.findUserById(103);
        newPost.setAuthor(testUser);
        newPost.setCreatedAt(postService.setDateToday());
        newPost.setPublishedAt(postService.setDateToday());
        tagService.findTagSetFromTagString(newPostTagNamesStr, newPost);
        newPost.setPublished(blogAction.equals(blogActionPublish));
        newPost.setExcerpt(createExcerpt(newPost.getContent()));
        postService.savePost(newPost);
        return "redirect:/home/blog-new";
    }
}
