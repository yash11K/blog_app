package com.mountblue.blogapp.controller;

import com.mountblue.blogapp.model.Post;
import com.mountblue.blogapp.service.PostService;
import com.mountblue.blogapp.service.PostTagService;
import com.mountblue.blogapp.service.TagService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

import static com.mountblue.blogapp.service.PostService.createExcerpt;

@Controller
@RequestMapping("/home")
public class BlogOperationController extends AbstractBlogControl{
    public BlogOperationController(PostService postService, TagService tagService, PostTagService postTagService) {
        super(postService, tagService, postTagService);
    }

    @GetMapping("/update")
    String showUpdatePostPage(@RequestParam("postId")Integer updatePostId, Model model){
        Post updatePost = postService.getPostById(updatePostId);
        model.addAttribute("updatePost",updatePost);
        return "updateBlogPost";
    }

    @PostMapping("/submit-updates")
    String submitPostUpdates(@ModelAttribute(name = "updatePost")Post updatedPost){
        updatedPost.setExcerpt(createExcerpt(updatedPost.getContent()));
        updatedPost.setUpdatedAt(new Date());
        updatedPost.setPublished(true);
        postService.savePost(updatedPost);
        return "redirect:/home";
    }

    @GetMapping("/delete")
    String deletePost(@ModelAttribute(name = "updatePost")Post updatedPost, @RequestParam("postId")Integer postId){
        postTagService.deletePostTagRelationByPostId(postId);
        postService.deletePostById(postId);
        return "redirect:/home";
    }
}
