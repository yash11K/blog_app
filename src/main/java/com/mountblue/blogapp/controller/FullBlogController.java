package com.mountblue.blogapp.controller;

import com.mountblue.blogapp.model.Post;
import com.mountblue.blogapp.model.Tag;
import com.mountblue.blogapp.service.PostService;
import com.mountblue.blogapp.service.PostTagService;
import com.mountblue.blogapp.service.TagService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/home")
public class FullBlogController extends AbstractBlogControl{
    public FullBlogController(PostService postService, TagService tagService, PostTagService postTagService) {
        super(postService, tagService, postTagService);
    }

    @GetMapping("/blog")
    public String showFullBlog(@RequestParam("postId") Integer postId, Model model){
        Post post = postService.getPostById(postId);
        Set<Tag> tags = post.getTags();
        model.addAttribute("post", post);
        model.addAttribute("postTags", tags);
        return "fullBlog";
    }
}
