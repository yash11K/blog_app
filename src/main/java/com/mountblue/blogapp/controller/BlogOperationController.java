package com.mountblue.blogapp.controller;

import com.mountblue.blogapp.model.Post;
import com.mountblue.blogapp.model.Tag;
import com.mountblue.blogapp.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static com.mountblue.blogapp.service.PostService.createExcerpt;

@Controller
@RequestMapping("/home")
public class BlogOperationController extends AbstractBlogControl{
    public BlogOperationController(PostService postService,
                                   TagService tagService,
                                   PostTagService postTagService,
                                   CommentService commentService,
                                   UserService userService) {
        super(postService, tagService, postTagService, commentService, userService);
    }

    @GetMapping("/update")
    String showUpdatePostPage(@RequestParam("postId")Integer updatePostId, Model model){
        Post updatePost = postService.getPostById(updatePostId);
        Set<Tag> tags = updatePost.getTags();
        StringBuilder tagsStr= new StringBuilder();
        model.addAttribute("updatePost",updatePost);
        for(Tag tag : tags){
            tagsStr.append(tag.getName()).append(',');
        }
        model.addAttribute("tagsStr", tagsStr);
        return "updateBlogPost";
    }

    @PostMapping("/submit-updates")
    String submitPostUpdates(@ModelAttribute(name = "updatePost")Post updatedPost, @RequestParam("tagsStr")String tagsStr){
        updatedPost.setAuthor(userService.findUserByName("yash"));
        postTagService.deletePostTagRelationByPostId(updatedPost.getId());
        tagService.saveTagSetFromTagString(tagsStr, updatedPost);
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
