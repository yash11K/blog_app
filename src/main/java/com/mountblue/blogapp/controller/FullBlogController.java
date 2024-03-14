package com.mountblue.blogapp.controller;

import com.mountblue.blogapp.model.Comment;
import com.mountblue.blogapp.model.Post;
import com.mountblue.blogapp.model.Tag;
import com.mountblue.blogapp.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Set;

@Controller
public class FullBlogController extends AbstractBlogControl{
    public FullBlogController(PostService postService,
                              TagService tagService,
                              PostTagService postTagService,
                              CommentService commentService,
                              UserService userService) {
        super(postService, tagService, postTagService, commentService, userService);
    }

    @GetMapping("/blog")
    public String showFullBlog(@RequestParam("postId") Integer postId, Model model){
        Post post = postService.getPostById(postId);
        List<Comment> postedComments = commentService.getAllComments();
        Set<Tag> tags = post.getTags();
        Comment comment = new Comment();
        model.addAttribute("post", post);
        model.addAttribute("postedComments", postedComments);
        model.addAttribute("postTags", tags);
        model.addAttribute("comments", comment);
        return "fullBlog";
    }
}
